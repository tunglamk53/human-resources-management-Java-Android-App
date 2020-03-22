package com.example.hrmanagement.Entity;

public class Employee {
    private int emp_id;
    private String emp_fname, emp_lname, emp_address, emp_phone;

    private String dep_name;

    //To Add Employee
    public Employee(String emp_fname, String emp_lname, String emp_phone, String emp_address) {
        this.emp_phone = emp_phone;
        this.emp_fname = emp_fname;
        this.emp_lname = emp_lname;
        this.emp_address = emp_address;
    }

    //To Fetch Employees
    public Employee(int emp_id, String emp_fname, String emp_lname, String emp_phone, String emp_address) {
        this.emp_id = emp_id;
        this.emp_phone = emp_phone;
        this.emp_fname = emp_fname;
        this.emp_lname = emp_lname;
        this.emp_address = emp_address;
    }

    public Employee(int emp_id, String emp_fname, String emp_lname, String emp_phone, String emp_address, String dep_name) {
        this.emp_id = emp_id;
        this.emp_phone = emp_phone;
        this.emp_fname = emp_fname;
        this.emp_lname = emp_lname;
        this.emp_address = emp_address;
        this.dep_name = dep_name;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public String getEmp_phone() {
        return emp_phone;
    }

    public String getEmp_fname() {
        return emp_fname;
    }

    public String getEmp_lname() {
        return emp_lname;
    }

    public String getEmp_address() {
        return emp_address;
    }

    public String getEmp_DepName() {
        return dep_name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id=" + emp_id +
                ", emp_phone=" + emp_phone +
                ", emp_fname='" + emp_fname + '\'' +
                ", emp_lname='" + emp_lname + '\'' +
                ", emp_address='" + emp_address + '\'' +
                ", emp_department='" + dep_name + '\'' +
                '}';
    }
}
