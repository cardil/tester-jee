/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wave.tester;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@WebServlet(name = "DumpRequestServlet", urlPatterns = {"/"})
public class DumpRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println(
					"<style>body {font-family: 'Segoe UI'; color: #444;} pre {background-color: #f9f9f9; border:1px solid #ddd; box-shadow: inset 1px 1px 16px #ddd; padding: 1em; border-radius: 3px; margin: 0.5em;font-family: Consolas}</style>");
			out.println("<head>");
			out.println("<title>Servlet DumpRequestServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet DumpRequestServlet</h1>");
			out.println("<h3>Headers</h3>");
			out.println("<pre>" + dumpHeaders(request) + "</pre>");
			out.println("<h3>Request</h3>");
			out.println("<pre>" + dumpRequest(request) + "</pre>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	private String dumpHeaders(final HttpServletRequest request) {
		final StringBuilder builder = new StringBuilder();

		for (final Enumeration<String> enums = request.getHeaderNames(); enums.hasMoreElements();) {
			final String name = enums.nextElement();
			builder.append(name);
			builder.append(":\t");
			builder.append(request.getHeader(name));
			builder.append("\n");
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	private String dumpRequest(final HttpServletRequest request) {
		final StringBuilder builder = new StringBuilder();
		dump(builder, request, "Protocol");
		dump(builder, request, "ContextPath");
		dump(builder, request, "Method");
		dump(builder, request, "Scheme");
		dump(builder, request, "ServerPort");
		dump(builder, request, "ServerName");
		dump(builder, request, "Secure");
		dump(builder, request, "Cookies");
		dump(builder, request, "AuthType");
		dump(builder, request, "PathInfo");
		dump(builder, request, "PathTranslated");
		dump(builder, request, "ParameterMap");
		dump(builder, request, "QueryString");
		dump(builder, request, "RequestURL");
		dump(builder, request, "RequestURI");

		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	private void dump(final StringBuilder builder, final HttpServletRequest request, final String prop) {
		try {
			String methodName = "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
			Method getter;
			try {
				getter = request.getClass().getMethod(methodName);
			} catch (NoSuchMethodException ex) {
				methodName = "is" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
				getter = request.getClass().getMethod(methodName);
			}
			builder.append(prop);
			builder.append(":\t");
			Object o = getter.invoke(request);
			builder.append(o);
			builder.append("\n");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
