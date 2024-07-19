package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab2.adapter.ToDoAdapter;
import com.example.lab2.dao.TodoDAO;
import com.example.lab2.model.ToDo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edTitle, edContent, edDate, edType;
    Button btnAdd;
    RecyclerView rvToDo;
    TodoDAO todoDAO;
    ArrayList<ToDo> toDos;
    ToDoAdapter toDoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edTitle = findViewById(R.id.edTitle);
        edContent = findViewById(R.id.edContent);
        edDate = findViewById(R.id.edDate);
        edType = findViewById(R.id.edType);
        btnAdd = findViewById(R.id.btnAdd);
        rvToDo = findViewById(R.id.rvToDo);
        todoDAO = new TodoDAO(MainActivity.this);
        toDos = todoDAO.getAllToDo();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvToDo.setLayoutManager(linearLayoutManager);
        toDoAdapter = new ToDoAdapter(MainActivity.this, toDos, todoDAO);
        rvToDo.setAdapter(toDoAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edTitle.getText().toString().trim();
                String content = edContent.getText().toString().trim();
                String date = edDate.getText().toString().trim();
                String type = edType.getText().toString().trim();
                if (title.isEmpty() || content.isEmpty() || date.isEmpty() || type.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter full infomation!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ToDo toDo = new ToDo();
                toDo.setTitle(title);
                toDo.setContent(content);
                toDo.setDate(date);
                toDo.setType(type);
                toDo.setStatus(0);
                if(todoDAO.addToDo(toDo)){
                    refreshList();
                    Toast.makeText(MainActivity.this, "Add todo successfully!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Add todo failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshList() {
        toDos.clear();
        toDos.addAll(todoDAO.getAllToDo());
        toDoAdapter.notifyDataSetChanged();
    }
}