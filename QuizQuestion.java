package com.example.campusconnect.model;

import java.util.Map;

public class QuizQuestion {
	// ...existing code...
	private String id;
	private String text;
	private Map<String, String> options; // key -> option text (e.g. "A" -> "32 bits")
	private String correctKey; // e.g. "A"

	public QuizQuestion() {}

	public QuizQuestion(String id, String text, Map<String, String> options, String correctKey) {
		this.id = id;
		this.text = text;
		this.options = options;
		this.correctKey = correctKey;
	}

	// getters / setters
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getText() { return text; }
	public void setText(String text) { this.text = text; }
	public Map<String, String> getOptions() { return options; }
	public void setOptions(Map<String, String> options) { this.options = options; }
	public String getCorrectKey() { return correctKey; }
	public void setCorrectKey(String correctKey) { this.correctKey = correctKey; }
}