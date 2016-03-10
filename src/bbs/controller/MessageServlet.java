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

import bbs.beans.Message;
import bbs.beans.User;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/message" })
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		Message message = new Message();
		message.setSubject(request.getParameter("subject"));
		message.setText(request.getParameter("text"));
		message.setCategory(request.getParameter("category"));

		List<String> messages = new ArrayList<String>();

		if (isValid(request, messages) == true) {

			User user = (User) session.getAttribute("loginUser");
			message.setUserId(user.getId());

			new MessageService().register(message);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			Message editMessage = message;
			request.setAttribute("editMessage", editMessage);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String subject = request.getParameter("subject");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if (StringUtils.isEmpty(subject) == true) {
			messages.add("件名を入力してください");
		}
		if (StringUtils.isEmpty(text) == true) {
			messages.add("本文を入力してください");
		}
		if (StringUtils.isEmpty(text) == true) {
			messages.add("カテゴリーを入力してください");
		}
		if (50 < category.length()) {
			messages.add("件名は50文字以下で入力してください");
		}
		if (1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
