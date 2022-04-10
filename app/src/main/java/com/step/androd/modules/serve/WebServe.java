package com.step.androd.modules.serve;

import com.step.androd.modules.nanohttpd.core.NanoHTTPD;

import java.util.Map;

/**
 * Create By: Meng
 * Create Date: 26/11/21
 * Desc:
 */
public class WebServe extends NanoHTTPD {

    public WebServe(int port) {
        super(port);
    }

    public WebServe(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        msg += "<p>这是手机启动的web</p>";
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
}
