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
import javax.servlet.http.HttpServletResponse;

import bbs.beans.User;

@WebFilter(urlPatterns = { "/signup.jsp", "/signup" })
public class ForbiddenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			User loginUser = (User) ((HttpServletRequest)request).getSession().getAttribute("loginUser");

			if(loginUser != null && loginUser.getId() == 1) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse)response).sendRedirect("./");
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
