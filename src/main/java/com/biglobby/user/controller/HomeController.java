package com.biglobby.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; 

@Controller
public class HomeController {

	@GetMapping("/biglobby/index")
	public String index() {
		return "redirect:/user/index.html";
	}
}
