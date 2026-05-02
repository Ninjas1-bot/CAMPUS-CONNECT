package com.example.campusconnect.model;

public class Booking {
	private int eventId;
	private String eventTitle;

	public Booking() {}
	public Booking(int eventId, String eventTitle) {
		this.eventId = eventId;
		this.eventTitle = eventTitle;
	}
	public int getEventId() { return eventId; }
	public void setEventId(int eventId) { this.eventId = eventId; }
	public String getEventTitle() { return eventTitle; }
	public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
}