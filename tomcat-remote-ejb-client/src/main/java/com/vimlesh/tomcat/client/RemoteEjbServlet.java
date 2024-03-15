package com.vimlesh.tomcat.client;

import com.vimlesh.HelloWorld;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * Servlet implementation class RemoteEjbServlet
 */

/**
 * @author preetham
 *
 */
public class RemoteEjbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoteEjbServlet() {
        System.out.println("RemoteEjbServlet Constructor called!");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("RemoteEjbServlet \"Init\" method called");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("RemoteEjbServlet \"Destroy\" method called");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RemoteEjbServlet \"Service\" method(inherited) called");
		System.out.println("RemoteEjbServlet \"DoGet\" method called");
		
		storeInSessionAndRespond(request, response);
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RemoteEjbServlet \"Service\" method(inherited) called");
        System.out.println("RemoteEjbServlet \"DoPost\" method called");

        try {
            remoteMethodCall(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	private  void remoteMethodCall (HttpServletRequest request,HttpServletResponse response) throws Exception {
		String host = request.getParameter("host");
		Properties jndiProps = new Properties();
		jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProps.put(Context.PROVIDER_URL,"http-remoting://"+host+":8080");
		// username
		jndiProps.put(Context.SECURITY_PRINCIPAL, "ejb");
		// password
		jndiProps.put(Context.SECURITY_CREDENTIALS, "test");
		// This is an important property to set if you want to do EJB invocations via the remote-naming project
		jndiProps.put("jboss.naming.client.ejb.context", true);
		System.out.println("calling with property"+jndiProps);
		// create a context passing these properties
		Context ctx = new InitialContext(jndiProps);
		// lookup the bean     Foo
		HelloWorld beanRemoteInterface= (HelloWorld) ctx.lookup("/wildfly-remote-ejb-server-1.0-SNAPSHOT/HelloWorldEJB!com.vimlesh.HelloWorld");
		String bar = beanRemoteInterface.getHelloWorldMessage();
		System.out.println("Hurray!! Remote bean called and the message is \"" + bar +"\"");

		request.setAttribute("message", Collections.singletonList(bar));
		RequestDispatcher view = request.getRequestDispatcher("result.jsp");
		view.forward(request, response);
		ctx.close();
	}
	
	private void storeInSessionAndRespond(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String uname = request.getParameter("uname");
		String emailId = request.getParameter("email");
		System.out.println("Username from jsp page is "+ uname + " and email id is "+ emailId);
		//Create a session
		HttpSession session = request.getSession(true);
		if(session!=null)
		{
			//store the attributes
			session.setAttribute("uname", uname);
			session.setAttribute("emailId", emailId);
			System.out.println("Username and email id is stored in the session");
		}

		out.write("<html><body><h4>Check console to understand the flow</h4></body></html>");
		out.write("<html><body><h2>Username and email id is stored in the session, go back and click on \"TestSession\" to test the session</h2></body></html>");
		out.write("<html><body><p>&copy 2024 Vimlesh</p></body></html>");
	}

	public static void main(String[] args) throws NamingException {
		Properties jndiProps = new Properties();
		jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProps.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
		// username
		jndiProps.put(Context.SECURITY_PRINCIPAL, "ejb");
		// password
		jndiProps.put(Context.SECURITY_CREDENTIALS, "test");
		// This is an important property to set if you want to do EJB invocations via the remote-naming project
		jndiProps.put("jboss.naming.client.ejb.context", true);
		// create a context passing these properties
        Context ctx = null;
        try {
			System.out.println("calling with property"+jndiProps);
            ctx = new InitialContext(jndiProps);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        // lookup the bean     Foo
		HelloWorld beanRemoteInterface= (HelloWorld) ctx.lookup("/wildfly-remote-ejb-server-1.0-SNAPSHOT/HelloWorldEJB!com.vimlesh.HelloWorld");
		String bar = beanRemoteInterface.getHelloWorldMessage();
		System.out.println("Hurray!! Remote bean called and the message is \"" + bar +"\"");

        try {
            ctx.close();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

}
