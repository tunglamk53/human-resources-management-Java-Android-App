package com.example.hrmanagement.CustomAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmanagement.Activity.EmployeeActivity;
import com.example.hrmanagement.Activity.UpdateEmployeeActivity;
import com.example.hrmanagement.DatabaseController.DatabaseOperation;
import com.example.hrmanagement.DatabaseHelper.DBHelperEmployee;
import com.example.hrmanagement.DatabaseHelper.DBHelperFact;
import com.example.hrmanagement.Entity.Employee;
import com.example.hrmanagement.R;

import java.util.ArrayList;

public class EmpRVAdapter extends RecyclerView.Adapter<EmpRVAdapter.ViewHolder> {

    private ArrayList<Employee> employees;
    private DBHelperEmployee dbHelperEmployee;
    private DBHelperFact dbHelperFact;

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
            holder.txtEmpId.setText(String.valueOf(employees.get(position).getEmp_id()));
            holder.txtEmpFName.setText(employees.get(position).getEmp_fname());
            holder.txtEmpLName.setText(employees.get(position).getEmp_lname());
            holder.txtEmpPhone.setText(employees.get(position).getEmp_phone());
            holder.txtEmpAddress.setText(employees.get(position).getEmp_address());
            holder.txtEmpDepName.setText(employees.get(position).getEmp_DepName());

            //Button Employee Detail in Employee Activity RecyclerView
            holder.btnEditEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), UpdateEmployeeActivity.class);

                    //Put EmployeeId to Extra
                    i.putExtra("extEmpId", employees.get(position).getEmp_id());

                    v.getContext().startActivity(i);
                }
            });

            //Button Delete Employee
            holder.btnDeleteEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    DatabaseOperation databaseOperation = new DatabaseOperation(v.getContext());
                    SQLiteDatabase mDb = databaseOperation.openDb();
                    dbHelperEmployee = new DBHelperEmployee(mDb);
                    dbHelperFact = new DBHelperFact(mDb);

                    final boolean[] deletedEmployee = {false};




                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    deletedEmployee[0] = dbHelperEmployee.deleteEmployee(employees.get(position).getEmp_id());
                                    deletedEmployee[0] = dbHelperFact.deleteFact(employees.get(position).getEmp_id());
                                    Intent i = new Intent(v.getContext(), EmployeeActivity.class);
                                    v.getContext().startActivity(i);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Do you want to Delete employee below? \n\n" + "\t\t\tName: "
                            + employees.get(position).getEmp_lname() + ", " + employees.get(position).getEmp_fname()
                            + "\n\t\t\tID: " + employees.get(position).getEmp_id()
                            + "\n\t\t\tDepartment: " + employees.get(position).getEmp_DepName())
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

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
    private TextView btnDeleteEmployee;

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

        //Button Delete Employee
        btnDeleteEmployee = itemView.findViewById(R.id.txtBtnDeleteEmp);
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
