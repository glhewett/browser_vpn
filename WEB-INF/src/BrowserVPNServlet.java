/*
 * Created on Aug 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
//import ProxyURL;

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
		BaseProxyHandler ph;
		
		ph = new DefaultProxyHandler(req.getPathInfo());
		
		ph.setScheme(req.getScheme());
		ph.setServerName(req.getServerName());
		ph.setServerPort(req.getServerPort());
		ph.setContextPath(req.getContextPath());
		ph.setServletPath(req.getServletPath());
		
		
		// set up response
		ph.prepareResponse();
		//System.err.println("Setting Content-type to " + ph.getContentType());
		res.setContentType(ph.getContentType());
		res.setContentLength(ph.getContentLength());
		PrintWriter out = res.getWriter();
		//System.out.print(ph.getURLContent());
		out.print(ph.getURLContent());
		out.close();
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException
	{
		doGet(req, res);
	}
}
