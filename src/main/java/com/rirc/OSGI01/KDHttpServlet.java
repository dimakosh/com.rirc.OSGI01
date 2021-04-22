package com.rirc.OSGI01;

import javax.servlet.http.HttpServlet;

public class KDHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void log(Object obj) {
		log(String.valueOf(obj));
	}
	
	public void log(Object obj, Throwable t) {
		log(String.valueOf(obj), t);
	}
}
