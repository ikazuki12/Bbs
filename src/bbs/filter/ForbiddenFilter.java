package bbs.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;

@WebFilter(urlPatterns = { "/signup.jsp", "/signup", "/control.jsp", "/control", "/settings.jsp", "/settings" })
public class ForbiddenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			User loginUser = (User) ((HttpServletRequest)request).getSession().getAttribute("loginUser");

			HttpSession session = ((HttpServletRequest)request).getSession();

			List<String> messages = new ArrayList<String>();

			if (loginUser != null) {

				Integer positonId = new Integer(loginUser.getPositionId());
				if (!positonId.equals(1)) {

					messages.add("アクセス権限がありません");
					session.setAttribute("errorMessages", messages);
					((HttpServletResponse)response).sendRedirect("./");
					return;
				} else {

					chain.doFilter(request, response);
				}
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
