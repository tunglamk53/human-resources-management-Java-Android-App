package com.example.hrmanagement.Entity;

public class Department {
    int dep_id;
    String dep_name, dep_location;

    public Department(String dep_name, String dep_location) {
        this.dep_name = dep_name;
        this.dep_location = dep_location;
    }

    public Department(int dep_id, String dep_name, String dep_location) {
        this.dep_id = dep_id;
        this.dep_name = dep_name;
        this.dep_location = dep_location;
    }

    public int getDep_id() {
        return dep_id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public String getDep_location() {
        return dep_location;
    }

    @Override
    public String toString() {
        return "Department{" +
                "dep_id=" + dep_id +
                ", dep_name='" + dep_name + '\'' +
                ", dep_location='" + dep_location + '\'' +
                '}';
    }

}
