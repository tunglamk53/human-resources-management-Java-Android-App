package com.example.hrmanagement.Entity;

public class Fact {
    int emp_id, dep_id, doc_id, job_id;
    int employment_status; // 0 = Quited; 1 = Active.
    double salary, hourly_rate;

    public Fact(int emp_id, int dep_id, int job_id) {
        this.emp_id = emp_id;
        this.dep_id = dep_id;
        this.job_id = job_id;
    }

    public Fact(int emp_id, int dep_id, int doc_id, int job_id, double salary, double hourly_rate, int employment_status) {
        this.emp_id = emp_id;
        this.dep_id = dep_id;
        this.doc_id = doc_id;
        this.job_id = job_id;
        this.employment_status = employment_status;
        this.salary = salary;
        this.hourly_rate = hourly_rate;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public int getDep_id() {
        return dep_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public int getEmployment_status() {
        return employment_status;
    }

    public double getSalary() {
        return salary;
    }

    public double getHourly_rate() {
        return hourly_rate;
    }
}
