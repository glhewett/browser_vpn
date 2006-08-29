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
