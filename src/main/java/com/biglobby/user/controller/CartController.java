package com.biglobby.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

	@GetMapping("/biglobby/cart")
	public String cart() {
		return "user/cart/cart";
	}
}
