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

import bbs.beans.Branch;
import bbs.beans.Position;
import bbs.beans.User;
import bbs.service.BranchService;
import bbs.service.PositionService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Branch> branches = new BranchService().select();
		request.setAttribute("branches", branches);
		List<Position> positions = new PositionService().select();
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		User user = new User();
		user.setLoginId(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		user.setPositionId(Integer.parseInt(request.getParameter("position_id")));

		HttpSession session = request.getSession();

		if(isValid(request, messages) == true) {

			new UserService().register(user);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			User editUser = user;
			request.setAttribute("editUser", editUser);
			List<Branch> branches = new BranchService().select();
			request.setAttribute("branches", branches);
			List<Position> positions = new PositionService().select();
			request.setAttribute("positions", positions);

			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = trim(request.getParameter("login_id")).trim();
		String password = trim(request.getParameter("password")).trim();
		String passwordCheck = trim(request.getParameter("password_check")).trim();
		String name = trim(request.getParameter("name")).trim();
		List<User> users = new UserService().getUsers();

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		} else if (loginId.length() < 6 || loginId.length() > 20) {
			messages.add("ログインIDの文字数は6文字以上20文字以下で入力してください");
		} else if (!loginId.matches("[a-zA-Z0-9]{6,20}")){
			messages.add("ログインIDは半角英数字で入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		} else if (password.length() < 6 || password.length() > 20) {
			messages.add("パスワードの文字数は6文字以上20文字以下で入力してください");
		} else if (!password.matches("[a-zA-Z0-9]{6,255}")){
			messages.add("パスワードは半角英数字で入力してください");
		}
		if (!password.equals(passwordCheck)) {
			messages.add("パスワードが確認用と一致しません。");
		}
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		} else if (name.length() > 10) {
			messages.add("名前は10文字以下で入力してください");
		}
		for (int i = 0, size = users.size(); i < size; i++){
			if (users.get(i).getLoginId().equals(loginId)){
				messages.add("既に" + loginId + "は使用されています");
			}
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
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
