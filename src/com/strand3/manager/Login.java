/*
 * Created on Dec 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.strand3.manager;

import javax.servlet.http.HttpServletRequest;
import com.strand3.app.AppActionAdapter;

/**
 * @author greg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Login extends AppActionAdapter {
	
	public String run (HttpServletRequest req) {
		
		// set page attributes
		req.setAttribute("title", "Login");
		
		// if the method is get, then this is probably the first time on
		// this page.
		if (req.getMethod() == "GET") {
			return "login.jsp";
		}

        // It is required for the user to provide and username and a
        // pasword, and if they do not, then there is not reason to go
        // forward.
		if (0 == req.getParameter("username").length()) {
			handleException(req, new Exception("Username is required"));
		}
		
		if (0 == req.getParameter("password").length()) {
			handleException(req, new Exception("Username is required"));
		}
		return "login.jsp";
	}
}
