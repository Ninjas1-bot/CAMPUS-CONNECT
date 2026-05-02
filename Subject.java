package com.example.campusconnect.model;

import java.util.List;
import java.util.Map;

public class Subject {
    private String id;
    private String name;
    private String teacher;
    private List<String> assignments;
    private Map<String, String> results; // student -> grade or score

    public Subject() {}

    public Subject(String id, String name, String teacher, List<String> assignments, Map<String,String> results) {
        this.id = id; this.name = name; this.teacher = teacher; this.assignments = assignments; this.results = results;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public List<String> getAssignments() { return assignments; }
    public void setAssignments(List<String> assignments) { this.assignments = assignments; }
    public Map<String,String> getResults() { return results; }
    public void setResults(Map<String,String> results) { this.results = results; }
}
