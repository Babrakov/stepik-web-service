package ru.infoza.main;

import ru.infoza.accountServer.AccountServer;
import ru.infoza.accountServer.AccountServerController;
import ru.infoza.accountServer.AccountServerControllerMBean;
import ru.infoza.resourceServer.ResourceServerI;
import ru.infoza.resourceServer.ResourceServer;
import ru.infoza.resourceServer.ResourceServerController;
import ru.infoza.resourceServer.ResourceServerControllerMBean;
import ru.infoza.accountServer.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resources.TestResource;
import ru.infoza.sax.ReadXMLFileSAX;
import ru.infoza.servlets.HomePageServlet;
import ru.infoza.servlets.ResourceServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            logger.error("Use port as the first argument");
//            System.exit(1);
//        }
//
//        String portString = args[0];
//        int port = Integer.valueOf(portString);

        String portString = "8080";
        int port = Integer.valueOf(portString);

        logger.info("Starting at http://127.0.0.1:" + portString);

        AccountServerI accountServer = new AccountServer(1);

        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ServerManager:type=AccountServerController");
        mbs.registerMBean(serverStatistics, name);
        ObjectName admin = new ObjectName("Admin:type=AccountServerController.UsersLimit");
        mbs.registerMBean(serverStatistics, admin);

        ResourceServerI resourceServer = new ResourceServer(new TestResource());
        ResourceServerControllerMBean serverResource = new ResourceServerController(resourceServer);
        name = new ObjectName("ServerManager:type=ResourceServerController");
        mbs.registerMBean(serverResource, name);
        admin = new ObjectName("Admin:type=ResourceServerController.Resource");
        mbs.registerMBean(serverResource, admin);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new HomePageServlet(accountServer)), HomePageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new ResourceServlet(resourceServer)), ResourceServlet.PAGE_URL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");

        server.join();
    }
}
