package com.example.campusconnect.controller;

import com.example.campusconnect.model.Booking;
import com.example.campusconnect.model.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {
	private final List<Event> sampleEvents = Arrays.asList(
		new Event(1, "Campus Orientation", "2025-11-05"),
		new Event(2, "Career Fair", "2025-11-20"),
		new Event(3, "Coding Workshop", "2025-12-01")
	);

	@GetMapping("/bookings")
	public String showBookings(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");
		if (bookings == null) {
			bookings = new ArrayList<>();
			session.setAttribute("bookings", bookings);
		}
		model.addAttribute("events", sampleEvents);
		model.addAttribute("bookings", bookings);
		return "bookings";
	}

	@PostMapping("/book")
	public String bookEvent(@RequestParam("eventId") int eventId, HttpSession session) {
		Optional<Event> ev = sampleEvents.stream().filter(e -> e.getId() == eventId).findFirst();
		if (ev.isPresent()) {
			@SuppressWarnings("unchecked")
			List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");
			if (bookings == null) {
				bookings = new ArrayList<>();
				session.setAttribute("bookings", bookings);
			}
			bookings.add(new Booking(ev.get().getId(), ev.get().getTitle()));
		}
		return "redirect:/bookings";
	}
}