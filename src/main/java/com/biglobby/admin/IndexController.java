package com.biglobby.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("/biglobby/admin/index")
	public String index() {
		return "redirect:/admin/index.html";
	}
}
