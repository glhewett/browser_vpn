import java.net.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


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
public class HTMLProxyHandler extends BaseProxyHandler {
	
	protected URL u;
	protected URLConnection conn;
	protected String content_type;
	protected int content_length;
	protected String scheme;
	protected String server_name;
	protected int server_port;
	protected String context_path;
	protected String servlet_path;

	public HTMLProxyHandler (URL u)
	{
		super(u);
	}
	
	public HTMLProxyHandler (String path)
	{
		super(path);
	}
	
	public void prepareResponse()
	{
		BufferedReader rd;
		String str;
		
		try {
			conn = u.openConnection();
			setContentType(conn.getContentType());
			setContentLength(conn.getContentLength());
			rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			content = new StringBuffer();
			
			while (null != ((str = rd.readLine()))) {
				content.append(str);
			}
			
			modifyHTML();	
		}
		catch (IOException e) {
			content = null;
		}
		return;
	}
	
	public void modifyHTML()
	{
		Pattern p;
		Matcher m;
		URL base = getBaseURL();;
	
		p = Pattern.compile("\\s+(href|src|codebase|action|url)=[\'\"]?(.*?)[\'\"]?[\\s>]");
		m = p.matcher(content);
		
		while (m.find()) {
			System.err.println("orig=" + content.substring(m.start(), m.end()));
			System.err.println("orig=" + m.group(2));
			
			if (content.charAt(m.start(2)) == '/' || content.charAt(m.start(2)) == '\\') {
				content.replace(m.start(2), m.start(2), base.toString());
			}
		}
	}
}
