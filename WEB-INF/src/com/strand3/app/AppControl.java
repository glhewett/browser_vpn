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
            Class actionclass=null;
            try {
            	System.err.println(getInitParameter("defaultClass") + "." + pathsplit[1]);
                actionclass = Class.forName(getInitParameter("defaultClass") + "." + pathsplit[1]);             
            } catch (ClassNotFoundException cnf) {
                //Try searching for the class directly
                actionclass = Class.forName(pathsplit[1]);
            }
        	AppAction actionhandler = (AppAction)actionclass.newInstance();
        	
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
