package com.example.hrmanagement.Entity;

public class Job {
    int job_id;
    String job_title, job_description;

    public Job(String job_title, String job_description) {
        this.job_title = job_title;
        this.job_description = job_description;
    }

    public Job(int job_id, String job_title, String job_description) {
        this.job_id = job_id;
        this.job_title = job_title;
        this.job_description = job_description;
    }

    public int getJob_id() {
        return job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getJob_description() {
        return job_description;
    }

    @Override
    public String toString() {
        return "Job{" +
                "job_id=" + job_id +
                ", job_title='" + job_title + '\'' +
                ", job_description='" + job_description + '\'' +
                '}';
    }
}
