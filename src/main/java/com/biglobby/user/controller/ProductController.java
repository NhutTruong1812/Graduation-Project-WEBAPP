package com.biglobby.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

	@GetMapping("/biglobby/product")
	public String product() {
		return "user/product/product";
	}
}
