package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

import static com.example.todolist.MainActivity.TASKS_lIST;
import static com.example.todolist.MainActivity.TASK_EDIT;


public class TaskFormDetails extends AppCompatActivity {

    public static final String REPLY_TASK_OBJECT = "Task Object";

    EditText task_name_p2;
    ImageButton button_active_inactive_p2;
    EditText editTextTextMultiLine;
    Button button_submit_p2;

    TasksTodo task;
    private List<TasksTodo> listTasks;
    String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form_details);

        Intent intent = getIntent();
        task = intent.getParcelableExtra(TASK_EDIT);
        listTasks = intent.getParcelableArrayListExtra(TASKS_lIST);

        task_name_p2 = findViewById(R.id.task_name_p2);
        button_active_inactive_p2 = findViewById(R.id.button_active_inactive_p2);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        button_submit_p2 = findViewById(R.id.button_submit_p2);

        oldName = task.getName();
        task_name_p2.setText(task.getName());
        editTextTextMultiLine.setText(task.getDescription());
        if(task.getStatus()){
            button_active_inactive_p2.setImageResource(R.drawable.button_active_task_image);
            button_active_inactive_p2.setBackgroundColor(getResources().getColor(R.color.checkedGreen));
        }else{
            button_active_inactive_p2.setImageResource(R.drawable.button_inactive_task_image);
            button_active_inactive_p2.setBackgroundColor(getResources().getColor(R.color.dark_grey));
        }
    }

    public void changeStatus(View view) {
        if(task.getStatus()){
            button_active_inactive_p2.setImageResource(R.drawable.button_inactive_task_image);
            button_active_inactive_p2.setBackgroundColor(getResources().getColor(R.color.dark_grey));
            task.setStatus(false);
        }else{
            button_active_inactive_p2.setImageResource(R.drawable.button_active_task_image);
            button_active_inactive_p2.setBackgroundColor(getResources().getColor(R.color.checkedGreen));
            task.setStatus(true);


        }
    }

    public void submit(View view) {

        task.setName(task_name_p2.getText().toString());
        task.setDescription(editTextTextMultiLine.getText().toString());

        for (int index = 0; index < listTasks.size(); index++){
            if(listTasks.get(index).getName().equals(oldName)){
                listTasks.set(index, task);
            }
        }

        Intent replyIntent = new Intent();
        replyIntent.putExtra(REPLY_TASK_OBJECT,(ArrayList<? extends Parcelable>) listTasks);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}