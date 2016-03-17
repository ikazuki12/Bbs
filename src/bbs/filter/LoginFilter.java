package bbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import bbs.beans.User;

@WebFilter(urlPatterns = { "/home.jsp", "/home" ,"/message.jsp", "/message", "/signup.jsp", "/signup", "/control.jsp", "/control", "/settings.jsp", "/settings" })
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			User loginUser = (User) ((HttpServletRequest)request).getSession().getAttribute("loginUser");

			if (loginUser != null) {

				chain.doFilter(request, response);
			} else {

				request.getRequestDispatcher("./login").forward(request, response);
				return;
			}
		} catch (ServletException se) {

	    } catch (IOException e) {

	    }
	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void destroy() {
	}
}
