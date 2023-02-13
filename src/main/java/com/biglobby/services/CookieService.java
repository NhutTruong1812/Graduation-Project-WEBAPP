package com.biglobby.services;

import javax.servlet.http.Cookie;

public interface CookieService {
	public Cookie get(String name);

	public String getValue(String name);

	public Cookie add(String name, String value, int days);

	public void remove(String name);
}
