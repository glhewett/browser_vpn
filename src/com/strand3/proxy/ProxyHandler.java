package com.strand3.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * Created on Sep 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author grehewe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ProxyHandler {
	protected URLConnection conn;
	protected String scheme;
	protected String server_name;
	protected int server_port;
	protected String context_path;
	protected String servlet_path;
	
	public ProxyHandler () {
	    // nothing
	}
	
	public void attachConnection(URLConnection value)
	{
		conn = value;
	}
	
	public String getContentType()
	{
		return conn.getContentType();
	}
	
	public int getContentLength()
	{
		return conn.getContentLength();
	}
	
	public void setScheme(String value)
	{
		scheme = value;
	} 
	
	public void setServerName(String value)
	{
		server_name = value;
	}
	
	public void setServerPort(int value)
	{
		server_port = value;
	}
	
	public void setContextPath(String value)
	{
		context_path = value;
	}
	
	public void setServletPath(String value)
	{
		servlet_path = value;
	}
	
	public InputStream getInputStream() throws IOException {
	    return conn.getInputStream();
	}
	
	public abstract void doProxy(OutputStream out) throws IOException;
}
