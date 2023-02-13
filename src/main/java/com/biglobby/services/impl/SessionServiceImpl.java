package com.biglobby.services.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl {

	@Autowired
	HttpSession session;

	/**
	 * Get attribute from session
	 * 
	 * @param <T>
	 * @param name
	 * @return
	 */
	public <T> T get(String name) {
		T param = (T) session.getAttribute(name);
		return (T) param;
	}

	/**
	 * Set attribute to session
	 * 
	 * @param name
	 * @param value
	 */
	public void set(String name, Object value) {
		session.setAttribute(name, value);
	}

	/**
	 * Remove attribute from session
	 * 
	 * @param name
	 */
	public void remove(String name) {
		session.removeAttribute(name);
	}
}
