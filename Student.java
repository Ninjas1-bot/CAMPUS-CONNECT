package com.example.campusconnect.model;

import java.util.*;

public class Student {
    private final int id;
    private final String name;
    private final String email;
    private final String password;
    private final String department;
    private final String registrationNumber;
    private final String phone;
    private int points = 0;
    private int studyStreak = 0;
    private final Map<String, Integer> progress = new LinkedHashMap<>();
    private final Set<Integer> rsvpedEvents = new HashSet<>();

    public Student(int id, String name, String email, String password, String department, String registrationNumber, String phone) {
        this.id = id; this.name = name; this.email = email; this.password = password;
        this.department = department; this.registrationNumber = registrationNumber; this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getDepartment() { return department; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getPhone() { return phone; }
    public int getPoints() { return points; }
    public int getStudyStreak() { return studyStreak; }
    public Map<String,Integer> getProgress() { return progress; }
    public Set<Integer> getRsvpedEvents() { return rsvpedEvents; }

    public void addProgress(String course, int percent) { progress.put(course, percent); }
    public void incrementStreak() { studyStreak++; points += 5; }
    public void addPoints(int p) { points += p; }
}
