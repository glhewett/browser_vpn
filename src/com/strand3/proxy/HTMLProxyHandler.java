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
 
package com.strand3.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created on Aug 24, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author grehewe
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HTMLProxyHandler extends ProxyHandler {

    public void doProxy (OutputStream out) throws IOException {

        // Read the data from URL
        InputStream in = getInputStream();
        BufferedReader rd;

        // Read the data from the InputStream
        StringBuffer content = new StringBuffer();
        String str = new String();
        rd = new BufferedReader(new InputStreamReader(getInputStream()));

        while (null != (str = rd.readLine())) {
            content.append(str);
        }

        // Change the Links in the HTML Document
        modifyContent(content);

        // Write out to Stream
        out.write(content.toString().getBytes());
        out.close();

        return;
    }

    public URL getBaseURL() {
        URL base;

        try {
            base = new URL(scheme, server_name, server_port, context_path
                    + servlet_path + "/" + conn.getURL().getProtocol() + "/"
                    + conn.getURL().getHost());
        } catch (MalformedURLException e) {
            System.err.println("failed to create base url");
            return null;
        }
        return base;
    }
    
    public URL getProxyURL() {
        URL base;

        try {
            base = new URL(scheme, server_name, server_port, context_path + 
                           servlet_path);
        } catch (MalformedURLException e) {
            System.err.println("failed to create base url");
            return null;
        }
        return base;
    }

    public void modifyContent(StringBuffer content) {
        Pattern p;
        Matcher m;

        p = Pattern
                .compile("\\s+(href|src|codebase|action|url)=[\'\"]?(.*?)[\'\"]?[\\s>]",
                        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        m = p.matcher(content);

        while (m.find()) {

            System.err.println("1: " + m.group(2));
            
            // for links like href="//slashdot.org/"
            if (content.substring(m.start(2), m.start(2) + 2).equals("//") ||
                content.substring(m.start(2), m.start(2) + 2).equals("\\\\")) {
	            URL base = getProxyURL();
	            System.err.println("base: " + base);
	            content.replace(m.start(2), m.start(2) + 2, base.toString() +
	                    "/http/");
	        }
            
            // for links from root like href="/images/logo.gif"
            else if (content.charAt(m.start(2)) == '/'
                    || content.charAt(m.start(2)) == '\\') {
                URL base = getBaseURL();
                System.err.println("base: " + base);
                content.replace(m.start(2), m.start(2), base.toString());
            }
            
            // for absolute links
            else if (content.substring(m.start(2), m.start(2) + 7).equals("http://")) {
                URL base = getProxyURL();
                System.err.println("base: " + base);
                content.replace(m.start(2), m.start(2) + 7, base.toString() + 
                                "/http/"); 
            }
            System.err.println("2: " + m.group(2));
        }
    }
}