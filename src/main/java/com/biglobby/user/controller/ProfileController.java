package com.biglobby.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

	@GetMapping("/biglobby/profile")
	public String profile() {
		return "user/profile/profile";
	}
}
