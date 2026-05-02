# Campus Connect — Proposed System Overview

This document outlines a proposed extension to the demo Campus Connect portal to support:

- Attendance: Tracks student presence (real-time)
- Quiz: Interactive assessments with instant feedback
- Analytics: Performance insights for students and teachers
- Communication: Improved student-teacher messaging

This is a high-level proposal intended as an implementation roadmap and API/data shape reference for the next phases.

## Goals

1. Real-time attendance tracking during classes and events.
2. Interactive quizzes with instant scoring and per-question feedback.
3. Analytical dashboards with trends and per-student metrics.
4. Simple teacher-student communication channel (announcements & messages).
5. Incremental rollout with minimal friction (keep the demo's in-memory mode but design for persistence).

## Architecture

- Spring Boot backend (current project) with REST endpoints and server-rendered pages for the quick demo.
- WebSocket endpoint for real-time attendance and live updates (Spring WebSocket / STOMP).
- Thymeleaf templates for main UI; lightweight client JS for modals/toasts and WebSocket client.
- Short-term persistence: H2 or SQLite. Long-term: PostgreSQL.

## High-level components

- Attendance Service
  - Tracks active sessions, per-event/class check-ins, and produces presence logs.
  - Real-time socket channel: `/ws/attendance` that pushes presence updates and allows teacher to mark students present.

- Quiz Service
  - REST endpoints for quiz definitions, answer submission and immediate scoring.
  - Stores per-question explanations (optional) and quiz attempts (for analytics).

- Analytics Service
  - Aggregates quizzes and attendance to produce student performance metrics (average scores, streaks, completion rates).
  - Exposes endpoints consumed by a dashboard UI.

- Messaging/Announcements
  - Support announcements (one-to-many), direct messages (teacher->student), and in-app notifications.

## Data models (suggested)

- Student
  - id (int), name, email, department, registrationNumber, phone, points, studyStreak

- Subject
  - id (string), name, teacher, assignments: List<String>, results: Map<String,String>

- Quiz
  - id, subjectId, title, questions: [ { id, text, options:{A,B,C,D}, correctKey, explanation } ], maxTime

- QuizAttempt
  - id, quizId, studentId, submittedAt, score, answers: {questionId -> chosenKey}, feedback

- Event/Attendance
  - id, title, date, attendees: Set<Integer>
  - AttendanceRecord: eventId, studentId, status (present/absent), timestamp

- Message
  - id, fromUserId, toUserId (or group), text, createdAt, read

## API endpoints (minimal set)

- Attendance
  - POST /api/attendance/checkin { eventId, studentId } -> 200
  - GET /api/attendance/event/{id} -> event attendance list
  - WebSocket topic /topic/attendance/{eventId} (broadcast updates)

- Quiz
  - GET /api/quizzes -> list
  - GET /api/quizzes/{id} -> quiz details (without correct answers)
  - POST /api/quizzes/{id}/submit { studentId, answers } -> { score, correctMap, feedback }
  - GET /api/quizzes/{id}/attempts?studentId= -> list attempts

- Analytics
  - GET /api/analytics/student/{id} -> summary (avg score, streaks, attendance%)

- Messaging
  - POST /api/messages -> create
  - GET /api/messages?userId= -> list

## UI & UX sketches

- Dashboard: student profile, upcoming events, subjects, quick actions (RSVP, start quiz)
- Attendance panel: teacher view with real-time list and ``Mark present`` buttons; student can click "Check-in".
- Quiz UI: question-by-question, immediate feedback modal showing correct answer + explanation; progress bar for multi-question quizzes.
- Analytics: charts (line chart for scores, bar chart for attendance), list of weak topics.

## MVP roadmap (3 phases)

Phase 1 — Core features (2 weeks)
- Quiz REST endpoints + in-memory storage.
- Simple quiz UI with immediate scoring (already partly implemented).
- Subjects page with assignments/results (done in demo).
- Basic attendance: allow manual RSVP/check-in for events.

Phase 2 — Real-time and persistence (2–3 weeks)
- Add WebSocket channel for attendance and notifications.
- Add H2 persistence and basic repositories for quizzes/attempts/attendance.
- Add quiz attempts history and result pages.

Phase 3 — Analytics & Messaging (2–3 weeks)
- Build analytics endpoints and basic charts on dashboard.
- Add messaging and announcements.
- Polish UI, add auth, and migrate to persistent DB.

## Security & privacy
- Simple demo: no authentication beyond login for demo user.
- Production: integrate Spring Security, password hashing, role-based access (student, teacher, admin), and secure WebSocket.

## Implementation notes
- Keep server-side logic testable: add unit tests for scoring and attendance business rules.
- For real-time, use Spring WebSocket + STOMP and a simple JS STOMP client.
- Use charting library for analytics (Chart.js or ApexCharts) with small datasets.

## Next steps I can help with right now
- Scaffold REST endpoints and small data repositories (H2) for Quizzes and Attendance.
- Add a WebSocket endpoint for attendance test demo and a small front-end demo to mark present.
- Implement quiz attempt storage and result history.

If you want, I can start by scaffolding the Quiz REST endpoints and a small persistence layer (H2 + Spring Data JPA) and migrate the in-memory quiz flow to use it. Which piece should I implement first?"