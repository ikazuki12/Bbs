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
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/messgeSelect" })
public class MessgeSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String category = request.getParameter("category");
		String startDate = request.getParameter("start_date");
		String endDate = request.getParameter("end_date");
		List<UserMessage> messages = new ArrayList<UserMessage>();

		HttpSession session = request.getSession();

		List<String> errorMessages = new ArrayList<String>();

		if (category.equals("all")){
			category = null;
		}
		if (StringUtils.isEmpty(startDate) == true) {
			startDate = null;
		}
		if (StringUtils.isEmpty(endDate) == true) {
			endDate = null;
		}
		messages = new MessageService().getMessage(category, startDate, endDate);

		if (messages.size() != 0) {
			request.setAttribute("messages", messages);
			List<Comment> comments = new CommentService().getComment();
			request.setAttribute("comments", comments);

			List<User> users = new UserService().getUsers();
			request.setAttribute("users", users);
		} else {
			errorMessages.add("指定に該当する投稿がありません");
			session.setAttribute("errorMessages", errorMessages);
		}

		request.setAttribute("editCategory", category);
		request.setAttribute("editStartDate", startDate);
		request.setAttribute("editEndtDate", endDate);

		request.getRequestDispatcher("./home.jsp").forward(request, response);

	}
}
