package com.example.hrmanagement.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmanagement.Entity.Employee;
import com.example.hrmanagement.R;

import java.util.ArrayList;

public class EmpRVAdapter extends RecyclerView.Adapter<EmpRVAdapter.ViewHolder> {

    private ArrayList<Employee> employees;

    public EmpRVAdapter(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.txtEmpId.setText(employees.get(position).getEmp_id() + "");
            holder.txtEmpFName.setText(employees.get(position).getEmp_fname());
            holder.txtEmpLName.setText(employees.get(position).getEmp_lname());
            holder.txtEmpPhone.setText(employees.get(position).getEmp_phone());
            holder.txtEmpAddress.setText(employees.get(position).getEmp_address());
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        try {
            return employees.size();
        } catch (Exception e){
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtEmpId, txtEmpFName, txtEmpLName, txtEmpPhone, txtEmpAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmpId = itemView.findViewById(R.id.txtEmpId);
            txtEmpFName = itemView.findViewById(R.id.txtEmpFName);
            txtEmpLName = itemView.findViewById(R.id.txtEmpLName);
            txtEmpPhone = itemView.findViewById(R.id.txtEmpPhone);
            txtEmpAddress = itemView.findViewById(R.id.txtEmpAddress);
        }
    }




//    public void updateData(ArrayList<Employee> employees) {
//        this.employees.clear();
//        this.employees.addAll(employees);
//        notifyDataSetChanged();
//    }
//    public void addItem(int position, ViewHolder viewHolder) {
//        this.employees.add(position, viewHolder);
//        notifyItemInserted(position);
//    }
//
//    public void removeItem(int position) {
//        items.remove(position);
//        notifyItemRemoved(position);
//    }
}
