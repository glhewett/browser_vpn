package com.strand3.proxy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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
public class DefaultProxyHandler extends ProxyHandler {

    public void doProxy(OutputStream out) throws IOException {
        InputStream in = getInputStream();
        byte[] buf = new byte[1024];
        int len;
        
        while ((len = in.read(buf, 0, buf.length)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
    }
}
