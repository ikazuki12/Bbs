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

@WebServlet(urlPatterns = { "/delete" })
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("loginUser");

		HttpSession session = request.getSession();

		List<String> errorMessages = new ArrayList<String>();

		String successMessage = null;

		if (request.getParameter("comment_id") != null) {
			Integer messageId = new Integer(request.getParameter("message_id"));
			Integer commentId = new Integer(request.getParameter("comment_id"));
			Integer messageUserId = new Integer(request.getParameter("user_id"));
			User userSelect = new UserService().getUser(messageUserId);
			Integer branchId = new Integer(userSelect.getBranchId());
			if (branchId.equals(2)) {
				new CommentService().delteComment(0, commentId);
				successMessage = String.format("投稿%sのコメント%sを削除しました", messageId, commentId);
//				successMessage = "投稿" + messageId + "のコメント" + commentId + "を削除しました";
			} else if (branchId.equals(3)) {
				Integer positionId = new Integer(userSelect.getPositionId());
				if (branchId.equals(user.getBranchId()) && positionId.equals(3)) {
					new CommentService().delteComment(0, commentId);
					successMessage = String.format("投稿%sのコメント%sを削除しました", messageId, commentId);

				}
			} else {
				errorMessages.add("削除する権限がありません");
			}
		} else {
			if(request.getParameter("message_id") != null) {
				int messageId = Integer.parseInt(request.getParameter("message_id"));
				int messageUserId = Integer.parseInt(request.getParameter("user_id"));
				User userSelect = new UserService().getUser(messageUserId);
				Position loginUserPosition = new PositionService().getPosition(user.getPositionId());
				if (loginUserPosition.getName().equals("情報管理当者")) {
					new MessageService().delteMessage(messageId);
					new CommentService().delteComment(messageId, 0);
					successMessage = String.format("投稿%sを削除しました", messageId);
				} else if (loginUserPosition.getName().equals("支店長")) {
					int positionId = userSelect.getPositionId();
					Position messageUserPosition = new PositionService().getPosition(positionId);
					if (user.getBranchId() == userSelect.getBranchId() && messageUserPosition.getName().equals("社員")) {
						new MessageService().delteMessage(messageId);
						new CommentService().delteComment(messageId, 0);
						successMessage = String.format("投稿%sを削除しました", messageId);

					}
				} else {
					errorMessages.add("削除する権限がありません");
				}

			}
		}
		if (successMessage != null) {
			session.setAttribute("successMessage", successMessage);
		}
		session.setAttribute("errorMessages", errorMessages);
		List<UserMessage> messages = new MessageService().getMessages(null, null, null);
		request.setAttribute("messages", messages);
		request.setAttribute("selectMessages", messages);

		List<Comment> comments = new CommentService().getComment();
		request.setAttribute("comments", comments);

		List<User> users = new UserService().getUsers();
		request.setAttribute("users", users);

		request.setAttribute("getParameter", "notNull");

		request.getRequestDispatcher("./home.jsp").forward(request, response);
	}
}
