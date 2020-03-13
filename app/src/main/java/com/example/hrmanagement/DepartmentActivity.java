package com.example.hrmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {

    private SQLiteDatabase wdb, rdb;
    private DatabaseOperation databaseOperation;
    private RecyclerView recyclerView;
    private ArrayList<Department> departments;
    private DepRVAdapter depRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        databaseOperation = new DatabaseOperation(this);
        wdb = databaseOperation.getWritableDatabase();
        rdb = databaseOperation.getReadableDatabase();

        departments = getDepartments();
        Log.d("depss", departments.toString());

        recyclerView = findViewById(R.id.rViewDepartment);
        depRVAdapter = new DepRVAdapter(departments);
        recyclerView.setAdapter(depRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        Button btnAddDep = findViewById(R.id.btnAddDep);
        btnAddDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDepartment(); //Add a department to Table DEPARTMENT in Database
            }
        });
    }

    private void addDepartment() {
        EditText tv1 = findViewById(R.id.edtDepName);
        EditText tv2 = findViewById(R.id.edtDepLocation);
        Log.d("tv1", tv1.getText().toString());
        Log.d("tv2", tv2.getText().toString());


        ContentValues contentValues = new ContentValues();
        contentValues.put("dep_name", tv1.getText().toString());
        contentValues.put("dep_location", tv2.getText().toString());

        long newRowID = wdb.insert("TAB_DEPARTMENT", null, contentValues);

        departments = getDepartments();

        depRVAdapter = new DepRVAdapter(departments);
        recyclerView.setAdapter(depRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private ArrayList<Department> getDepartments() {
        ArrayList<Department> tem_departments = new ArrayList<>();
        String SQL_SELECT_ALL_DEPARTMENT ="SELECT * FROM TAB_DEPARTMENT";
        Cursor cursor = rdb.rawQuery(SQL_SELECT_ALL_DEPARTMENT, null);
        try {
            if(cursor!=null){
                cursor.moveToFirst();
                do{
                    int tem_id = Integer.parseInt(cursor.getString(0));
                    String tem_name = cursor.getString(1);
                    String tem_location = cursor.getString(2);

                    Department tem_dep = new Department(tem_id, tem_name, tem_location);
                    tem_departments.add(tem_dep);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception ex) { Log.d("errs3", ex.toString()); }
        return tem_departments;
    }
}
