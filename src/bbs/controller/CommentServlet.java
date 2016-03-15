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

import bbs.beans.Comment;
import bbs.beans.User;
import bbs.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");
		Comment comment = new Comment();

		comment.setText(request.getParameter("text"));

		if (isValid(request, messages) == true) {

			comment.setMessageId(Integer.parseInt(request.getParameter("message_id")));
			comment.setUserId(user.getId());

			new CommentService().register(comment);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("editText", request.getParameter("text"));

			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String message = trim(request.getParameter("text")).trim();

		if (StringUtils.isEmpty(message) == true) {
			messages.add("内容を入力してください");
		}
		if (500 < message.length()) {
			messages.add("500文字以下で入力してください");
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
