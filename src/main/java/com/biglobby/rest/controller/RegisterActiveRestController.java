package com.biglobby.rest.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.biglobby.entity.RegisterActive;
import com.biglobby.services.RegisterActiveService;
import com.biglobby.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/registeractives")
public class RegisterActiveRestController {

	@Autowired
	private RegisterActiveService regService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<RegisterActive>> get() {
		return regService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<RegisterActive> get(@PathVariable("id") Long id) {
		return regService.get(id);
	}

	@GetMapping(params = {"user.id"})
	public ResponseEntity<RegisterActive> getByUserId(@RequestParam("user.id") Long userId) {
		return regService.getByUserId(userId);
	}

	@PostMapping
	public ResponseEntity<RegisterActive> add(@RequestBody Long id) {
		RegisterActive entity = new RegisterActive();
		entity.setUser(userService.get(id).getBody());
		entity.setActived(false);
		entity.setRegisterDate(new Date());
		return regService.add(entity);
	}

	// @PostMapping
	// public RedirectView add(@RequestBody Long id) {
	// RegisterActive entity = new RegisterActive();
	// entity.setUser(userService.get(id).getBody());
	// entity.setActived(false);
	// entity.setRegisterDate(new Date());
	// regService.add(entity);
	// RedirectView redirectView = new RedirectView();
	// redirectView.setUrl("http://localhost:8080/user/index.html#!/biglobby/dangnhap");
	// return redirectView;
	// }

	@PutMapping("/{id}")
	public ResponseEntity<RegisterActive> update(@PathVariable("id") Long id, @RequestBody RegisterActive entity) {
		return regService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return regService.delete(id);
	}
	


	@GetMapping("/active/{id}")
	public RedirectView updateStatus(@PathVariable("id") Long id) {
		regService.updateStatus(id);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:8080/user/index.html#!/biglobby/dangnhap");
		return redirectView;
	}

	// phần của quang
	@GetMapping(params = {"page", "limit", "status", "key"})
	public ResponseEntity<Page<RegisterActive>> getAdminUser(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, 
			@RequestParam("status") int status,
			@RequestParam("key") String textSearch) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		// list page Category
		if (status < 0) {
			if (status == -1) {
				return regService.getListUserAllSearch(textSearch, pageable);
			} else {
				return regService.getListUserAllSearch(textSearch, pageable);
			}
		} else {
			if (status == 1) {
				return regService.getListUserAll(pageable);
			} else {
				return regService.getListUserAll(pageable);
			}
		}
	}
	// end
}
