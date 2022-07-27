package com.example.cardiacreader.bottomSheetFragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.cardiacreader.R;
import com.example.cardiacreader.activity.MainActivity;
import com.example.cardiacreader.database.DatabaseClient;
import com.example.cardiacreader.model.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateTaskBottomSheetFragment extends BottomSheetDialogFragment {

    Unbinder unbinder;
    @BindView(R.id.date)
    EditText date;
    @BindView(R.id.time)
    EditText time;
    @BindView(R.id.systolic_pressure)
    EditText systolic_pressure;
    @BindView(R.id.diastolic_pressure)
    EditText diastolic_pressure;
    @BindView(R.id.heart_rate)
    EditText heart_rate;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.add)
    Button add;
    int taskId;
    boolean isEdit;
    Task task;
    int mYear, mMonth, mDay;
    int mHour, mMinute;
    setRefreshListener setRefreshListener;
    AlarmManager alarmManager;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    MainActivity activity;
    public static int count = 0;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public void setTaskId(int taskId, boolean isEdit, setRefreshListener setRefreshListener, MainActivity activity) {
        this.taskId = taskId;
        this.isEdit = isEdit;
        this.activity = activity;
        this.setRefreshListener = setRefreshListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override

    /** inside create task bottomsheetfragment there is a layout called fragment_create_task*/

    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_create_task, null);
        unbinder = ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
        /**
         *this is the add button it will vlidate all the fields. if validate fields
         * are not empty it will add the values. if this is empty then  it will cretae a toast that
         * this field is empty
         */
        add.setOnClickListener(view -> {
            if(validateFields())
            createTask();
        });
        if (isEdit) {
            showTaskFromId();
        }

        date.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        time.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(),
                        (view12, hourOfDay, minute) -> {
                            time.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });
    }

    /**
     * validateFields is a boolean funtion, after it returns true it will enter into the create task
     *
     */
    public boolean validateFields() {
        if(date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(activity, "Please enter a date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(time.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(activity, "Please enter a time", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(systolic_pressure.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(activity, "Please enter systolic pressure", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(diastolic_pressure.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(activity, "Please enter diastolic pressure", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(heart_rate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(activity, "Please enter heart rate", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(comment.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(activity, "Please enter comment", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
/**
 * this create task function calls the room database. here we adding all the task into a particular model
 */
    private void createTask() {
        class saveTaskInBackend extends AsyncTask<Void, Void, Void> {
            @SuppressLint("WrongThread")
            @Override
            /*
            here i am setting all the data in the model class
             */
            protected Void doInBackground(Void... voids) {
                Task createTask = new Task();
                createTask.setDate(date.getText().toString());
                createTask.setTime(time.getText().toString());
                createTask.setSystolic_pressure(systolic_pressure.getText().toString());
                createTask.setDiastolic_pressure(diastolic_pressure.getText().toString());
                createTask.setHeart_rate(heart_rate.getText().toString());
                createTask.setComment(comment.getText().toString());

                if (!isEdit)
                    DatabaseClient.getInstance(getActivity()).getAppDatabase()
                            .dataBaseAction()
                            .insertDataIntoTaskList(createTask);
                else
                    DatabaseClient.getInstance(getActivity()).getAppDatabase()
                            .dataBaseAction()
                            .updateAnExistingRow(taskId, date.getText().toString(),
                                    time.getText().toString(),
                                    systolic_pressure.getText().toString(),
                                    diastolic_pressure.getText().toString(),
                                    heart_rate.getText().toString(),
                                    comment.getText().toString());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setRefreshListener.refresh();
                Toast.makeText(getActivity(), "Your Record is added", Toast.LENGTH_SHORT).show();
                dismiss();

            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();
    }


    private void showTaskFromId() {
        class showTaskFromId extends AsyncTask<Void, Void, Void> {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                task = DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .dataBaseAction().selectDataFromAnId(taskId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setDataInUI();
            }
        }
        showTaskFromId st = new showTaskFromId();
        st.execute();
    }

    private void setDataInUI() {
        date.setText(task.getDate());
        time.setText(task.getTime());
        systolic_pressure.setText(task.getSystolic_pressure());
        diastolic_pressure.setText(task.getDiastolic_pressure());
        heart_rate.setText(task.getHeart_rate());
        comment.setText(task.getComment());
    }

    public interface setRefreshListener {
        void refresh();
    }
}
