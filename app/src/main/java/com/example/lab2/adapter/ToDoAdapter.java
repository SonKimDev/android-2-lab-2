package com.example.lab2.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.R;
import com.example.lab2.dao.TodoDAO;
import com.example.lab2.model.ToDo;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ToDo> toDos;
    private final TodoDAO dao;

    public ToDoAdapter(Context context, ArrayList<ToDo> toDos, TodoDAO dao) {
        this.context = context;
        this.toDos = toDos;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_todo_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(toDos.get(position).getTitle());
        holder.tvDate.setText(toDos.get(position).getDate());

        if (toDos.get(position).getStatus() == 1) {
            holder.cbCheck.setChecked(true);
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.cbCheck.setChecked(false);
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = toDos.get(position).getId();
                if (dao.deleteToDo(id)){
                    Toast.makeText(context, "Delete todo successfully", Toast.LENGTH_SHORT).show();
                    toDos.clear();
                    toDos = dao.getAllToDo();
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Delete todo failure.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = toDos.get(position).getId();
                boolean check_result = dao.updateStatusToDo(id, holder.cbCheck.isChecked());
                if (check_result) {
                    Toast.makeText(context, "Update status successfully.", Toast.LENGTH_SHORT).show();
                    toDos.clear();
                    toDos = dao.getAllToDo();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Update status failure.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (toDos!=null) {
            return toDos.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbCheck;
        TextView tvTitle, tvDate;
        ImageView btnUpdate, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCheck = itemView.findViewById(R.id.cbCheck);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
