package com.biglobby.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.biglobby.services.SessionService;


 

@Service
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	SessionService session;

//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		User user = session.get("usersession");
//		
//		String error = "";
//		if (user == null) {
//			error = "Please Login!";
//			session.set("secureUri", request.getRequestURI());
//			response.sendRedirect("/user/signin?error=" + error);
//			return false;
//		} else {
//			//&& user.getAuthorities()
//			if (request.getRequestURI().startsWith("/admin")) {
//				System.err.println(request.getRequestURI());
//				error = "You are not Admin!";
//				session.set("secureUri", request.getRequestURI());
//				response.sendRedirect("/user/index");
//				return false;
//			}
//		}
//		return true;
//	}

}
