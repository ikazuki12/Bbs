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

import bbs.beans.Users;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		Users user = new Users();
		user.setLoginId(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		user.setPositionId(Integer.parseInt(request.getParameter("position_id")));

		HttpSession session = request.getSession();

		if(isValid(request, messages) == true) {

			new UserService().register(user);

			response.sendRedirect("./signup.jsp");
		} else {
			session.setAttribute("errorMessages", messages);
			Users editUser = user;
			request.setAttribute("editUser", editUser);

			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}
		if (loginId.length() < 6 || loginId.length() > 20) {
			messages.add("ログインIDの文字数は6文字以上20文字以下で入力してください");
		}
		if (!loginId.matches("[a-zA-Z0-9]{6,20}")){
			messages.add("ログインIDは半角英数字で入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (password.length() < 6 || password.length() > 20) {
			messages.add("パスワードの文字数は6文字以上20文字以下で入力してください");
		}
		if (!password.matches("[a-zA-Z0-9]{6,255}")){
			messages.add("パスワードは半角英数字で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
