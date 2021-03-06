/*
 * Copyright (c) 2006, Greg Hewett
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name Greg Hewett nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
public class Home extends AppActionAdapter {
	
	public String run (HttpServletRequest req) {
		
		// set page attributes
		req.setAttribute("title", req.getAttribute("title") + " - Login");
		
		// if the method is get, then this is probably the first time on
		// this page.
		if (req.getMethod() == "GET") {
			return "error.jsp";
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
		
		if (req.getParameter("username") == "greg" &&
			req.getParameter("password") == "greg") {
			req.setAttribute("title", req.getAttribute("title") + " - Home");
			return "home.jsp";
		}
		
		return "login.jsp";
	}
}
