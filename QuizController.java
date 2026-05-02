package com.example.campusconnect.controller;

import com.example.campusconnect.model.QuizQuestion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class QuizController {
	private static QuizQuestion sampleQuestion() {
		Map<String, String> options = new LinkedHashMap<>();
		options.put("A", "32 bits");
		options.put("B", "64 bits");
		options.put("C", "Depends on JVM");
		options.put("D", "16 bits");
		return new QuizQuestion("q1", "What is the size of the primitive int type in Java?", options, "A");
	}

	@GetMapping("/quiz")
	public String showQuiz(Model model) {
		model.addAttribute("question", sampleQuestion());
		return "quiz";
	}

	@PostMapping("/quiz-submit")
	public String submitQuiz(@RequestParam(name = "answer", required = false) String answer, Model model) {
		QuizQuestion q = sampleQuestion();
		int score = 0;
		if (Objects.equals(answer, q.getCorrectKey())) {
			score = 1;
		}
		model.addAttribute("question", q);
		model.addAttribute("score", score);
		model.addAttribute("total", 1);
		model.addAttribute("correctKey", q.getCorrectKey());
		model.addAttribute("selected", answer);
		return "quiz-result";
	}
}