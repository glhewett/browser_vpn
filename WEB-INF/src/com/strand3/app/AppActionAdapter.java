/*
 * Created on Dec 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.strand3.app;

import javax.servlet.http.HttpServletRequest;

/**
 * @author greg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AppActionAdapter implements AppAction {

	public abstract String run(HttpServletRequest request);
	
    /**
     * Attempts to retrieve the given parameter and parse it as an integer
     * Returns the default if unsuccessful.
     * 
     * @param request
     * @param paramName
     * @param def
     * @return
     */
    public int getIntParameter(HttpServletRequest request, String paramName, int def) {
        try {
            return Integer.parseInt(request.getParameter(paramName));
        } catch (NumberFormatException nf) {
            return def;
        }
    }
    
    public static String handleException(Object host, HttpServletRequest request, Exception e) {
        request.setAttribute("exception", e);
        request.setAttribute("message", "Error in action handler "+host.getClass().getName());
        return "common/error.jsp";
        
    }

    public String handleException(HttpServletRequest request, Exception e) {
        return handleException(this, request, e);
    }
}
