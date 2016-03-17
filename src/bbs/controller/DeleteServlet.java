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
import bbs.beans.User;
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;
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
			Integer positionId = new Integer(user.getPositionId());
			if (positionId.equals(2)) {
				new CommentService().delteComment(0, commentId);
				successMessage = String.format("投稿[%s]のコメント[%s]を削除しました", messageId, commentId);
			} else if (positionId.equals(3)) {
				if (branchId.equals(user.getBranchId()) && userSelect.getPositionId() == 4) {
					new CommentService().delteComment(0, commentId);
					successMessage = String.format("投稿[%s]のコメント[%s]を削除しました", messageId, commentId);

				} else {
					errorMessages.add("削除する権限がありません");
				}
			} else {
				errorMessages.add("削除する権限がありません");
			}
		} else {
			if(request.getParameter("message_id") != null) {
				Integer messageId = new Integer(request.getParameter("message_id"));
				Integer messageUserId = new Integer(request.getParameter("user_id"));
				User userSelect = new UserService().getUser(messageUserId);
				Integer branchId = new Integer(userSelect.getBranchId());
				Integer positionId = new Integer(user.getPositionId());
				if (user.getPositionId() == 2) {
					new MessageService().delteMessage(messageId);
					new CommentService().delteComment(messageId, 0);
					successMessage = String.format("投稿[%s]を削除しました", messageId);
				} else if (positionId.equals(3)) {
					if (branchId.equals(user.getBranchId()) && userSelect.getPositionId() == 4) {
						new MessageService().delteMessage(messageId);
						new CommentService().delteComment(messageId, 0);
						successMessage = String.format("投稿[%s]を削除しました", messageId);

					} else {
						errorMessages.add("削除する権限がありません");
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
