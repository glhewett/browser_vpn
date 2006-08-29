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
 *     * Neither the name of the University of California, Berkeley nor the
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
 
/*
 * Created on Aug 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.strand3.app;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

import com.strand3.app.AppAction;

import java.util.regex.Pattern;

/**
 * @author grehewe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AppControl extends HttpServlet {
	
	private static final Pattern splitPattern=Pattern.compile("/");
	
	public void doGet (HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException
	{
		// Get the class name for model.
		String[] pathsplit = splitPattern.split(req.getPathInfo(),2);
		
		try {
        	// create action handler class by name
            Class actionclass;
            
            try {
                actionclass = Class.forName(getInitParameter("defaultClass") + 
                		                    "." + pathsplit[1]);             
            } 
            catch (ClassNotFoundException cnf) {
                //Try searching for the class directly
                //actionclass = Class.forName(pathsplit[1]);
           
            	//We should return a 404 here, but I am not sure how to do that.
            	res.addHeader("status", "404");
                //RequestDispatcher dispatcher = req.getRequestDispatcher(getInitParameter("viewsDir") + "error.jsp");
                //dispatcher.forward(req, res);
                return;
            }
        	AppAction actionhandler = (AppAction)actionclass.newInstance();
        	
        	// Create default properties.
            req.setAttribute("title", "BrowserVPN");
        	
            // polymorphic call to abstract run() method
            String view=actionhandler.run(req);

            // Add view directory to the beginning of the view
            view = getInitParameter("viewsDir") + view;
            
            
            
            // forward to view returned from actionhandler
            RequestDispatcher dispatcher = req.getRequestDispatcher(view);
            dispatcher.forward(req, res);
        }
        catch (Exception e) {
            throw new ServletException("Error in dispatch", e);
        }
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException
	{
		doGet(req, res);
	}
}
