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