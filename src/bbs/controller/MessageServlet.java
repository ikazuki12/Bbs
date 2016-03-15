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
		message.setSubject(trim(request.getParameter("subject").trim()));
		message.setText(trim(request.getParameter("text").trim()));
		message.setCategory(trim(request.getParameter("category").trim()));

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

		String subject = trim(request.getParameter("subject").trim());
		String text = trim(request.getParameter("text").trim());
		String category = trim(request.getParameter("category").trim());

		if (StringUtils.isEmpty(subject) == true) {
			messages.add("件名を入力してください");
		} else if (50 < subject.length()) {
			messages.add("件名は50文字以下で入力してください");
		}
		if (StringUtils.isEmpty(text) == true) {
			messages.add("本文を入力してください");
		} else if (1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}
		if (StringUtils.isEmpty(category) == true) {
				messages.add("カテゴリーを入力してください");
		}else if (10 < category.length()) {
			messages.add("カテゴリーはは10文字以下で入力してください");
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
