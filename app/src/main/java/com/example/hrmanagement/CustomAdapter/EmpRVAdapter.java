package com.example.hrmanagement.CustomAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmanagement.Activity.EditEmployeeActivity;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            holder.txtEmpId.setText(employees.get(position).getEmp_id() + "");
            holder.txtEmpFName.setText(employees.get(position).getEmp_fname());
            holder.txtEmpLName.setText(employees.get(position).getEmp_lname());
            holder.txtEmpPhone.setText(employees.get(position).getEmp_phone());
            holder.txtEmpAddress.setText(employees.get(position).getEmp_address());
            holder.txtEmpDepName.setText(employees.get(position).getEmp_DepName());

            holder.btnEditEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), EditEmployeeActivity.class);

                    i.putExtra("extEmpId", employees.get(position).getEmp_id() + "");
                    i.putExtra("extEmpFName", employees.get(position).getEmp_fname());
                    i.putExtra("extEmpLName", employees.get(position).getEmp_lname());
                    i.putExtra("extEmpPhone", employees.get(position).getEmp_phone());
                    i.putExtra("extEmpAddress", employees.get(position).getEmp_address());
                    i.putExtra("extEmpDepName", employees.get(position).getEmp_DepName());


                    v.getContext().startActivity(i);
                }
            });
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

//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView txtEmpId, txtEmpFName, txtEmpLName, txtEmpPhone, txtEmpAddress, txtEmpDepName;
        private Button btnEditEmployee;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmpId = itemView.findViewById(R.id.txtEmpId);
            txtEmpFName = itemView.findViewById(R.id.txtEmpFName);
            txtEmpLName = itemView.findViewById(R.id.txtEmpLName);
            txtEmpPhone = itemView.findViewById(R.id.txtEmpPhone);
            txtEmpAddress = itemView.findViewById(R.id.txtEmpAddress);
            txtEmpDepName = itemView.findViewById(R.id.txtEmpDepName);

            //set up BUTTON Edit Employee Info
            btnEditEmployee = itemView.findViewById(R.id.btnEditEmp);
//            btnEditEmployee.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            if(v.getId() == btnEditEmployee.getId()){ // get the BUTTON Edit Employee Info
//                Intent i = new Intent(v.getContext(), EditEmployeeActivity.class);
//                v.getContext().startActivity(i);
//            }
//        }
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
