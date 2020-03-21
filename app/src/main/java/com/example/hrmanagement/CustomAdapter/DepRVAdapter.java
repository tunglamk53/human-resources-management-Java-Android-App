package com.example.hrmanagement.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmanagement.Entity.Department;
import com.example.hrmanagement.R;

import java.util.ArrayList;

public class DepRVAdapter extends RecyclerView.Adapter<DepRVAdapter.ViewHolder> {

    private ArrayList<Department> departments;

    public DepRVAdapter(ArrayList<Department> departments) {
        this.departments = departments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_department, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtDepId.setText(departments.get(position).getDep_id()+"");
        holder.txtDepName.setText(departments.get(position).getDep_name());
        holder.txtDepLocation.setText(departments.get(position).getDep_location());
    }

    @Override
    public int getItemCount() {
        try {
            return departments.size();
        } catch (Exception e){
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDepId, txtDepName, txtDepLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDepId = itemView.findViewById(R.id.txtDepId);
            txtDepName = itemView.findViewById(R.id.txtDepName);
            txtDepLocation = itemView.findViewById(R.id.txtDepLocation);
        }
    }


}
