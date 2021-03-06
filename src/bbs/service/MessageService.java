package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Message;
import bbs.beans.UserMessage;
import bbs.dao.MessageDao;
import bbs.dao.UserMessageDao;

public class MessageService {

	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();

			messageDao.insert(connection, message);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public Message getMessage(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();

			message = messageDao.getMessage(connection, message);

			commit(connection);

			return message;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<UserMessage> getMessages(String category, String startDate, String endDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao userMessageDao = new UserMessageDao();
			List<UserMessage> ret =
					userMessageDao.getUserMessage(connection, category, startDate, endDate);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void delteMessage(int messageId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao userMessageDao = new UserMessageDao();
			userMessageDao.deleteMessage(connection, messageId);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}
