package ru.infoza.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.TestResource;
import ru.infoza.resourceServer.ResourceServerI;
import ru.infoza.sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ResourceServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private final ResourceServerI resourceServer;

    public ResourceServlet(ResourceServerI resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(path);
//        System.out.println(resource.toString());
        resourceServer.setTestResource(resource);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

//        String remove = request.getParameter("remove");
//
//        if (remove != null) {
//            resourceServer.removeUser();
//            response.getWriter().println("Hasta la vista!");
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }

        String name = resourceServer.getName();
        int age = resourceServer.getAge();

        logger.info("Name: {}. Age {}", name, age);

        response.getWriter().println("Hello, " + name + " (" + age + ")!");
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
