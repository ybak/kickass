package org.kickass.gameserver.boot;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.kickass.gameserver.temp.HelloServlet;

public class Boot {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[] { connector });
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/hello");
        context.addServlet(HelloServlet.class, "/");
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
