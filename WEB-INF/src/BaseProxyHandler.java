import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;

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
public abstract class BaseProxyHandler {
	protected URL u;
	protected URLConnection conn;
	protected String content_type;
	protected int content_length;
	protected String scheme;
	protected String server_name;
	protected int server_port;
	protected String context_path;
	protected String servlet_path;
	protected ByteBuffer content;
	
	public BaseProxyHandler (String url)
	{
		try {
			String paths[];
			paths = url.split("/", 3);
			if (paths.length == 3) {
				u = new URL(paths[1] + "://" + paths[2]);
			}
			else {
				u = null;
				return;
			}
		}
		catch (MalformedURLException e) {
			u = null;
			conn = null;
			System.err.println("failed to parse URL from Request Path");
			return;
		}
	}
	
	public BaseProxyHandler (URL url)
	{
		u = url;
	}
	
	public void setContentType(String value)
	{
		content_type = value;
	}
	
	public String getContentType()
	{
		return content_type;
	}
	
	public void setContentLength(int value)
	{
		content_length = value;
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
	
	public URL getBaseURL()
	{
		URL base;
		
		try {
			base = new URL(scheme, server_name, server_port	, 
					       context_path + servlet_path + "/" + u.getProtocol() + "/" + u.getHost());
		}
		catch (MalformedURLException e) {
			System.err.println("failed to create base url");
			return null;
		}
		return base;
	}
	
	public ByteBuffer getURLContent()
	{
		return content;
	}
	
	public int getContentLength()
	{
		return content.capacity() - content.remaining();
	}
	
	public void prepareResponse() {
		BufferedReader rd;
		String str;
		
		try {
			conn = u.openConnection();
			setContentType(conn.getContentType());
			setContentLength(conn.getContentLength());
			rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			content = new ByteBuffer();
			content.
			while (null != ((str = rd.readLine()))) {
				content.append(str);
			}	
		}
		catch (IOException e) {
			System.err.println("failed to read from url");
			content = null;
		}
		return;
	}
}
