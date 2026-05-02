package com.example.campusconnect.controller;

import com.example.campusconnect.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.annotation.PostConstruct;

import java.util.*;

@Controller
public class MainController {
    private final List<Student> students = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();
    // subjectId -> date -> set of studentIds present
    private final Map<String, Map<String, Set<Integer>>> subjectAttendance = new HashMap<>();
    private Student currentUser = null;
    // teacher flow is UI-only demo; no backend teacher state here
    private int studentIdCounter = 1, eventIdCounter = 1;

    public MainController() {
        Student s = new Student(studentIdCounter++, "Test Student", "test@example.com", "123", "Computer Science", "REG2025-001", "555-1234");
        s.addProgress("Java Basics", 50);
        students.add(s);

    // no server-side teacher seeding (UI-only teacher demo)

        events.add(new Event(eventIdCounter++, "Hackathon", "24-hour coding event", "2025-10-10"));
        events.add(new Event(eventIdCounter++, "AI Workshop", "Intro to AI", "2025-10-15"));
        // sample subject
        subjects.add(new Subject("java","Java","Miss Surya Madhu", java.util.Arrays.asList("Assignment 1: OOP","Assignment 2: Streams"), java.util.Map.of("Test Student","A")));
    }

    @GetMapping("/")
    public String home(Model model) {
        if (currentUser == null) return "login";
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email) && s.getPassword().equals(password)) {
                currentUser = s;
                return "redirect:/dashboard";
            }
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    if (currentUser == null) return "redirect:/";
    Student displayStudent = currentUser != null ? currentUser : (students.isEmpty()? null : students.get(0));
    model.addAttribute("student", displayStudent);
    model.addAttribute("isTeacher", false);
    model.addAttribute("teacher", null);
        model.addAttribute("events", events);
        model.addAttribute("subjects", subjects);
    model.addAttribute("students", students);
    model.addAttribute("subjectAttendance", subjectAttendance);
    // provide streak visualization data: last 14 days boolean array and previous streak for comparison
    int streak = (displayStudent != null) ? displayStudent.getStudyStreak() : 0;
        int prev = Math.max(0, streak - 1); // simple previous-day heuristic
        List<Boolean> streakDays = new ArrayList<>();
        int days = 14;
        for (int i = 0; i < days; i++) {
            // mark last 'streak' days as studied
            int idxFromEnd = days - 1 - i; // 0=oldest, days-1=most recent
            boolean studied = idxFromEnd >= (days - streak);
            streakDays.add(studied);
        }
        model.addAttribute("streakDays", streakDays);
    model.addAttribute("studyStreak", streak);
    model.addAttribute("prevStudyStreak", prev);
        double changePct = prev == 0 ? (streak>0?100.0:0.0) : ((streak - prev) * 100.0 / prev);
        model.addAttribute("streakChangePct", Math.round(changePct));
        return "dashboard";
    }

    @GetMapping("/subjects")
    public String subjects(Model model) {
        if (currentUser == null) return "redirect:/";
        model.addAttribute("subjects", subjects);
        return "subjects";
    }

    @GetMapping("/attendance")
    public String attendance(Model model) {
        if (currentUser == null) return "redirect:/";
        model.addAttribute("students", students);
        model.addAttribute("subjects", subjects);
        model.addAttribute("subjectAttendance", subjectAttendance);
            model.addAttribute("isTeacher", false);
        return "attendance";
    }

    @PostMapping("/api/attendance/checkin")
    @ResponseBody
    public Map<String,Object> apiAttendanceCheckin(@RequestParam String subjectId, @RequestParam int studentId, @RequestParam boolean present) {
        // server-side marking disabled (UI-only teacher demo); return permission denied
        return Map.of("status","error","message","permission_denied");
    }

    // UI-only teacher demo: teacher login and marking are handled client-side in the template

    @GetMapping("/api/attendance/history")
    @ResponseBody
    public Map<String,Object> apiAttendanceHistory(@RequestParam String subjectId) {
        Map<String, Set<Integer>> byDate = subjectAttendance.getOrDefault(subjectId, Collections.emptyMap());
        // return simple date->count map
        Map<String,Integer> counts = new TreeMap<>();
        for (Map.Entry<String, Set<Integer>> e : byDate.entrySet()) counts.put(e.getKey(), e.getValue().size());
        return Map.of("subjectId", subjectId, "history", counts);
    }

    // Demo helper to populate some sample attendance across a few dates
    @PostConstruct
    public void seedSampleAttendance() {
        if (subjects.isEmpty() || students.isEmpty()) return;
        String sid = subjects.get(0).getId();
        subjectAttendance.putIfAbsent(sid, new HashMap<>());
        Map<String, Set<Integer>> byDate = subjectAttendance.get(sid);
        java.time.LocalDate today = java.time.LocalDate.now();
        for (int i=0;i<5;i++) {
            String d = today.minusDays(4-i).toString();
            Set<Integer> set = new HashSet<>();
            // mark first N students present for variety
            for (int j=0;j<students.size();j++) if (j % 2 == i%2) set.add(students.get(j).getId());
            byDate.put(d, set);
        }
        // additional subjects attendance seed
        for (int k=1;k<subjects.size();k++) {
            String id = subjects.get(k).getId();
            subjectAttendance.putIfAbsent(id, new HashMap<>());
            Map<String, Set<Integer>> b = subjectAttendance.get(id);
            String d = today.toString();
            Set<Integer> sset = new HashSet<>();
            sset.add(students.get(0).getId());
            b.put(d, sset);
        }
    // no server-side teacher notifications/queries in UI-only demo
    }

    @GetMapping("/cgpa")
    public String cgpa() {
        return "cgpa";
    }

    @GetMapping("/progress-view")
    public String progressView(Model model) {
        if (currentUser == null) return "redirect:/";
        model.addAttribute("student", currentUser);
        model.addAttribute("subjects", subjects);
        return "progress";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        if (currentUser == null) return "redirect:/";
        model.addAttribute("student", currentUser);
        return "profile";
    }

    @GetMapping("/announcement")
    public String announcement(Model model) {
        // small announcement page - content can be dynamic
        model.addAttribute("title", "Semester Registration Ending Soon");
        model.addAttribute("message", "Semester registration closes on 2025-10-20. Please register before the deadline to avoid late fees.");
        model.addAttribute("ctaText", "Go to Registration");
        model.addAttribute("ctaLink", "#");
        return "announcement";
    }

    @PostMapping("/rsvp")
    public String rsvp(@RequestParam int eventId, RedirectAttributes redirectAttributes) {
        if (currentUser != null) {
            for (Event e : events) {
                if (e.getId() == eventId && !currentUser.getRsvpedEvents().contains(eventId)) {
                    e.getAttendees().add(currentUser.getId());
                    currentUser.getRsvpedEvents().add(eventId);
                    currentUser.addPoints(10);
                    redirectAttributes.addFlashAttribute("rsvpSuccess", "You have RSVPed to '" + e.getTitle() + "'.");
                    redirectAttributes.addFlashAttribute("rsvpEventId", eventId);
                    break;
                }
            }
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/progress")
    public String updateProgress(@RequestParam String course, @RequestParam int percent) {
        if (currentUser != null) {
            currentUser.addProgress(course, percent);
            if (percent == 100) currentUser.addPoints(50);
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/studyday")
    public String addStudyDay() {
        if (currentUser != null) currentUser.incrementStreak();
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout() {
        currentUser = null;
        return "redirect:/";
    }
}
