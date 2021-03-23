package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.todolist.TaskFormDetails.REPLY_TASK_OBJECT;

public class MainActivity extends AppCompatActivity {

    public static final String TASKS_lIST = "List of Tasks";
    public static final String TASK_EDIT = "Task To Be Edited";
    public static final int TEXT_REQUEST = 1;

    private EditText inputTaskName;
    private Spinner dropDown;
    private LinearLayout taskTable;

    private List<TasksTodo> listTasks = new ArrayList<TasksTodo>(); //this list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTaskName = (EditText)findViewById(R.id.input_todo);
        dropDown = (Spinner) findViewById(R.id.dropdown_todo);
        taskTable = (LinearLayout)findViewById(R.id.layoutTaskContainer_p1);

        addDropdownListener(dropDown);


        // load all the data from


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TASKS_lIST, (ArrayList<? extends Parcelable>) listTasks);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        listTasks = savedInstanceState.getParcelableArrayList(TASKS_lIST);
        for (TasksTodo task : listTasks){
            addRow(task);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK){
            listTasks = data.getParcelableArrayListExtra(REPLY_TASK_OBJECT);
            taskTable.removeAllViews();
            for (TasksTodo task : listTasks){
                addRow(task);
            }
        }
    }


    /////////////////Add Listeners and Method///////////////////////

    public void addTask(View view) {
        boolean ifTaskInputNotEmpty = !(inputTaskName.getText().toString().equals(""));
        String name = inputTaskName.getText().toString();

        if(ifTaskInputNotEmpty){
            TasksTodo task = new TasksTodo(name);
            listTasks.add(addRow(task));
        }
        inputTaskName.setText("");
    }

    private void addDropdownListener(Spinner dropdown) {
        ArrayAdapter dropdownItems = new ArrayAdapter<String>(this, R.layout.spinner_style, getResources().getStringArray(R.array.todo_dropdown));

        dropdown.setAdapter(dropdownItems);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString().trim();

                switch (selectedItem){
                    case "All":
                        taskTable.removeAllViews();
                        for (TasksTodo task : listTasks){
                            addRow(task);
                        }
                        break;
                    case "Active":
                        taskTable.removeAllViews();
                        for (TasksTodo task : listTasks){
                            if(task.getStatus()){
                                addRow(task);
                            }
                        }
                        break;
                    case "Cleared":
                        taskTable.removeAllViews();
                        for (TasksTodo task : listTasks){
                            if(!task.getStatus()){
                                addRow(task);
                            }
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public TasksTodo addRow(TasksTodo task){

        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.row_todo_task, null, false);
        TextView taskName = row.findViewById(R.id.task_1p);
        ImageButton buttonTaskStatus = row.findViewById(R.id.set_active_inactive_p1);
        ImageButton buttonTaskEdit = row.findViewById(R.id.button_edit_p1);
        ImageButton buttonDelete = row.findViewById(R.id.delete_p1);

        taskName.setText(task.getName());

        if(task.getStatus()){
            buttonTaskStatus.setImageResource(R.drawable.button_active_task_image);
            buttonTaskStatus.setBackgroundColor(Color.rgb(0,255,0));
        }

        //TASK CHECKED STATUS
        buttonTaskStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(buttonTaskStatus, task);
            }
        });

        //TASK EDIT
        buttonTaskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailsActivity(task);
            }
        });
        //TASK DELETE
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask(row, task);
            }
        });

        taskTable.addView(row);
        return task;
    }

    /***
     * this method changes the status of the task buttonStatuss and also the object status
     * @param buttonTaskStatus
     * @param task
     */
    public void changeStatus(ImageButton buttonTaskStatus, TasksTodo task) {
        if(task.getStatus()){
            buttonTaskStatus.setImageResource(R.drawable.button_inactive_task_image);
            buttonTaskStatus.setBackgroundColor(getResources().getColor(R.color.dark_grey));
            task.setStatus(false);
        }else{
            buttonTaskStatus.setImageResource(R.drawable.button_active_task_image);
            buttonTaskStatus.setBackgroundColor(getResources().getColor(R.color.checkedGreen));
            task.setStatus(true);
        }
    }

    /**
     * this method goes to details activity
     * @param task
     */
    public void goToDetailsActivity(TasksTodo task) {
        Intent intent = new Intent(this, TaskFormDetails.class);
        intent.putExtra(TASKS_lIST, (ArrayList<? extends Parcelable>) listTasks);
        intent.putExtra(TASK_EDIT, task);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    /**
     * this method deletes a task from the object and deletes the entire row of the task
     * @param row it receives a ROW
     * @param task receibes a task object
     */
    public void deleteTask(LinearLayout row, TasksTodo task ) {
        taskTable.removeView(row);
        listTasks.remove(task);
    }
}