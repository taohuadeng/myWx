package com.thd.wx;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyMain {
    public static void main(String[] args) throws Exception {
        Server jettyServer = new Server();

        SocketConnector conn = new SocketConnector();
        conn.setPort(8008);
        jettyServer.setConnectors(new Connector[]{conn});

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/myWx");
        webAppContext.setWar("web");
        jettyServer.setHandler(webAppContext);
        jettyServer.start();
    }
}
