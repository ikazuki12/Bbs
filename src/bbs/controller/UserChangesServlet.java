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
import bbs.utils.CipherUtil;

@WebServlet(urlPatterns = { "/settings" })
public class UserChangesServlet extends HttpServlet {
	private static final long serialVersionUID  = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("user_id") != null) {
			int userId = Integer.parseInt(request.getParameter("user_id"));
			User user = new UserService().getUser(userId);
			request.setAttribute("user", user);
		}
		List<Branch> branches = new BranchService().select();
		request.setAttribute("branches", branches);
		List<Position> positions = new PositionService().select();
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String successMessage = null;

		List<String> messages = new ArrayList<String>();

		String password = request.getParameter("password");

		User user = new User();
		user.setId(Integer.parseInt(request.getParameter("user_id")));
		user.setLoginId(request.getParameter("login_id"));
		if (StringUtils.isEmpty(password) == false) {
			user.setPassword(CipherUtil.encrypt(password));
		}
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		user.setPositionId(Integer.parseInt(request.getParameter("position_id")));
		HttpSession session = request.getSession();
		if (isValid(request, messages) == true) {

			new UserService().userUpdete(user, password);

			successMessage = "編集が完了しました";

			session.setAttribute("successMessage", successMessage);

			response.sendRedirect("/Bbs/control");
		} else {
			session.setAttribute("errorMessages", messages);
			List<Branch> branches = new BranchService().select();
			request.setAttribute("branches", branches);
			List<Position> positions = new PositionService().select();
			request.setAttribute("positions", positions);
			User editUser = user;
			request.setAttribute("user", editUser);

			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = trim(request.getParameter("login_id").trim());
		String password = trim(request.getParameter("password")).trim();
		String passwordCheck = trim(request.getParameter("password_check")).trim();
		String name = trim(request.getParameter("name")).trim();

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}
		else if (loginId.length() < 6 || loginId.length() > 20) {
			messages.add("ログインIDは6文字以上20文字以下で入力してください");
		}
		else if (!loginId.matches("[a-zA-Z0-9]{6,20}")){
			messages.add("ログインIDは半角英数字で入力してください");
		}
		if (StringUtils.isEmpty(password) == false) {
			if (password.length() < 6 || password.length() > 20) {
				messages.add("パスワードは6文字以上20文字以下で入力してください");
			}
			else if (!password.equals(passwordCheck)) {
				messages.add("パスワードが確認用と一致しません。");
			}
		}
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}
		else if (name.length() > 10) {
			messages.add("名前は10文字以下で入力してください");
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
