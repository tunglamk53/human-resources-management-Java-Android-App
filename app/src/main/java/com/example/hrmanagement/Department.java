package com.example.hrmanagement;

public class Department {
    private int dep_id;
    private String dep_name, dep_location;

    public Department(int dep_id, String dep_name, String dep_location) {
        this.dep_id = dep_id;
        this.dep_name = dep_name;
        this.dep_location = dep_location;
    }

    public int getDep_id() {
        return dep_id;
    }

//    public void setDep_id(int dep_id) {
//        this.dep_id = dep_id;
//    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getDep_location() {
        return dep_location;
    }

    public void setDep_location(String dep_location) {
        this.dep_location = dep_location;
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
