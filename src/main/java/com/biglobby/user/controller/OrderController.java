package com.biglobby.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

	@GetMapping("/biglobby/order")
	public String order() {
		return "user/order/order";
	}
}
