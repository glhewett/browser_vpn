import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class DefaultProxyHandler extends BaseProxyHandler {

	public DefaultProxyHandler (URL u)
	{
		super(u);
	}
	
	public DefaultProxyHandler (String path)
	{
		super(path);
	}
	
	public void prepareResponse() {
		super.prepareResponse();
		
		
		if (conn.getContentType() == "text/html") {
			modifyHTML();
		}
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
