/*
 * Created on Sep 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.strand3.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


/**
 * @author grehewe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProxyHandlerFactory {
    private URLConnection conn;
    private URL u;
    private String content_type;
    private int content_length;
    private static HashMap handlers;
    
    static {
        handlers = new HashMap();
        handlers.put("text/html", new HTMLProxyHandler());
        handlers.put("default", new DefaultProxyHandler());
    }

    public ProxyHandlerFactory (String url)
	{
		
	}
    
	public void setContentType(String value)
	{
		content_type = value;
	}
    
    private static URL getURLFromPathInfo(String pathinfo) 
    	throws MalformedURLException, Exception 
	{
        URL u;
		String paths[];
		paths = pathinfo.split("/", 3);
		
		if (paths.length == 3) {
			u = new URL(paths[1] + "://" + paths[2]);
		}
		else {
			throw new Exception ("Invalid Path Info");
		}
		return u;
    }
    
    public static ProxyHandler getProxyHandler(String pathinfo) throws Exception {
        URL u;
        URLConnection c;
        ProxyHandler ret;
        
        // Parse the URL and Open the connection
        try {
            //System.err.println("PathInfo: " + pathinfo);
            u = getURLFromPathInfo(pathinfo);
            //System.err.println("URL: " + u.toString());
            c = u.openConnection();
            c.connect();
        }
        catch (MalformedURLException e) {
            throw new Exception("Invalid URL Request");
        }
        catch (IOException e) {
            throw new Exception("Unable to open URL Connection");
        }
        String ct;
        String[] cta;
        cta = c.getContentType().split(";");
        ct = cta[0];
        System.err.println("Content-type: " + ct);

        
        // Hand out the proper ProxyHandler
        if (handlers.containsKey(ct)) {
            ret = (ProxyHandler) handlers.get(ct);
        }
        else {
            ret = (ProxyHandler) handlers.get("default");
        }
        
        //attach the connection to the proxy handler
        ret.attachConnection(c);
        return(ret);
    }
}
