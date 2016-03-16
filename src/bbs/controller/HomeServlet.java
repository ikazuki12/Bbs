package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.Comment;
import bbs.beans.User;
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = (User) request.getSession().getAttribute("loginUser");

		List<UserMessage> messages = new MessageService().getMessages(null, null, null);
		request.setAttribute("messages", messages);
		request.setAttribute("selectMessages", messages);

		List<Comment> comments = new CommentService().getComment();
		request.setAttribute("comments", comments);

		List<User> users = new UserService().getUsers();
		request.setAttribute("users", users);

		boolean isShowMessageForm;
		if (user != null) {
			isShowMessageForm = true;
			request.getRequestDispatcher("./home.jsp").forward(request, response);
		} else {
			isShowMessageForm = false;
			response.sendRedirect("./login");
		}
		request.setAttribute("isShowMessageFrom", isShowMessageForm);

	}
}
