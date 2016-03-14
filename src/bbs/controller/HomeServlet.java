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

import bbs.beans.Comment;
import bbs.beans.Position;
import bbs.beans.User;
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;
import bbs.service.PositionService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = (User) request.getSession().getAttribute("loginUser");
		boolean isShowMessageForm;
		if (user != null) {
			isShowMessageForm = true;
		} else {
			isShowMessageForm = false;
		}

		HttpSession session = request.getSession();

		List<String> errorMessages = new ArrayList<String>();

		if(request.getParameter("message_id") != null) {
			int messageId = Integer.parseInt(request.getParameter("message_id"));
			int messageUserId = Integer.parseInt(request.getParameter("user_id"));
			User userSelect = new UserService().getUser(messageUserId);
			Position position = new PositionService().getPosition(user.getPositionId());
			if (position.getName().equals("情報管理当者")) {
				new MessageService().delteMessage(messageId);
				new CommentService().delteComment(messageId);
			} else if (position.getName().equals("支店長")) {
				int positionId = userSelect.getPositionId();
				position = new PositionService().getPosition(positionId);
				if (user.getBranchId() == userSelect.getBranchId() && position.getName().equals("社員")) {
					new MessageService().delteMessage(messageId);
					new CommentService().delteComment(messageId);
				}
			} else {
				errorMessages.add("削除する権限がありません");
				session.setAttribute("errorMessages", errorMessages);
			}
		}

		List<UserMessage> messages = new MessageService().getMessage(null, null, null);
		request.setAttribute("messages", messages);

		List<Comment> comments = new CommentService().getComment();
		request.setAttribute("comments", comments);

		List<User> users = new UserService().getUsers();
		request.setAttribute("users", users);

		request.setAttribute("isShowMessageFrom", isShowMessageForm);

		request.getRequestDispatcher("./home.jsp").forward(request, response);
	}
}
