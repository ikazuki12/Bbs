package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.User;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/control" })
public class UserControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		if (request.getParameter("stopped") != null) {
			int userId = Integer.parseInt(request.getParameter("stopped"));
			Boolean stopped = false;
			new UserService().doStop(stopped, userId);
		}

		if (request.getParameter("stop") != null) {
			int userId = Integer.parseInt(request.getParameter("stop"));
			Boolean stopped = true;
			new UserService().doStop(stopped, userId);
		}

		List<User> users = new UserService().getUsers();
		request.setAttribute("users", users);

		request.getRequestDispatcher("./control.jsp").forward(request, response);
	}
}
