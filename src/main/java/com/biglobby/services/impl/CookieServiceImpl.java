package com.biglobby.services.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biglobby.services.CookieService;

@Service
public class CookieServiceImpl implements CookieService {

	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;

	/**
	 * Get cookie object
	 * 
	 * @param name
	 * @return cookie or null
	 */
	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i];
				}
			}
		}
		return null;
	}

	/**
	 * Get cookie value
	 * 
	 * @param name
	 * @return value or null
	 */
	public String getValue(String name) {
		Cookie cookie = this.get(name);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * Add cookie
	 * 
	 * @param name
	 * @param value
	 * @param hours
	 * @return cookie
	 */

	public Cookie add(String name, String value, int days) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(24 * 60 * 60 * days);
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * Remove cookie
	 * 
	 * @param name
	 */
	public void remove(String name) {
		Cookie cookie = this.get(name);
		if (cookie != null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

}
