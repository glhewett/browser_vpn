/*
 * Created on Aug 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

import com.strand3.proxy.ProxyHandler;
import com.strand3.proxy.ProxyHandlerFactory;

/**
 * @author grehewe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BrowserVPNServlet extends HttpServlet {
	
	public void doGet (HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException
	{
	    ProxyHandler ph = null;
	    
	    try {
	        ph = ProxyHandlerFactory.getProxyHandler(req.getPathInfo());
		    //System.err.println("Using Class: " + ph.getClass().getName());
	    }
	    catch (Exception e) {
	        System.err.println("Error: " + e);
	        return;
	    }

	    ph.setScheme(req.getScheme());
		ph.setServerName(req.getServerName());
		ph.setServerPort(req.getServerPort());
		ph.setContextPath(req.getContextPath());
		ph.setServletPath(req.getServletPath());
		ph.doProxy(res.getOutputStream());
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException
	{
		doGet(req, res);
	}
}
