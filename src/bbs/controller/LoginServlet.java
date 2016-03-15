package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bbs.beans.User;
import bbs.service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(loginId, password);

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		if(isValid(request, messages, user) == true) {
			session.setAttribute("loginUser", user);
			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("editUser", loginId);

			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private boolean isValid (HttpServletRequest request, List<String> messages, User user) {
		String loginId = trim(request.getParameter("login_id")).trim();
		String password = trim(request.getParameter("password")).trim();

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (user != null) {
			if (!user.isStopped()) {
				messages.add("ログインに失敗しました");
			}
		} else {
			messages.add("ログインに失敗しました");
		}
		if (messages.size() != 0) {
			return false;
		} else {
			return true;
		}
	}

	public static String trim(String str) {
	    if (str == null) {
	        return null;
	    }

	    char[] val = str.toCharArray();
	    int len = val.length;
	    int st = 0;

	    while ((st < len) && (val[len - 1] <= ' ' || val[len - 1] == '　')) {
	        len--;
	    }

	    return ((st>0) || (len<val.length)) ? str.substring(st,len):str;
	}
}
