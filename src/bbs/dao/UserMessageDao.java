package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.UserMessage;
import bbs.exception.NoRowsUpdatedRuntimeException;
import bbs.exception.SQLRuntimeException;

public class UserMessageDao {

	public void deleteMessage(Connection connection, int messageId) {

		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("delete from messages where id = ?");

			ps = connection.prepareStatement(mySql.toString());

			ps.setInt(1, messageId);
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UserMessage> getUserMessage(Connection connection, String category, String startDate, String endDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("select * from user_message ");
			if (category != null) {
				mySql.append("where ");
				mySql.append("category = ? ");
				if (startDate != null){
					mySql.append("and insert_date >= ? ");
					if (endDate != null) {
						mySql.append("and insert_date <= ? ");
					}
				} else if (endDate != null) {
					mySql.append("and insert_date <= ? ");
				}
			} else if (startDate != null){
				mySql.append("where ");
				mySql.append("insert_date >= ? ");
				if (endDate != null) {
					mySql.append("and insert_date <= ? ");
				}
			} else if (endDate != null) {
				mySql.append("where ");
				mySql.append("insert_date <= ? ");
			}
			mySql.append("order by insert_date desc");

			ps = connection.prepareStatement(mySql.toString());

			if (category != null) {
				ps.setString(1, category);
				if (startDate != null){
					ps.setString(2, startDate);
					if (endDate != null) {
						ps.setString(3, endDate);
					}
				} else if (endDate != null) {
					ps.setString(2, endDate);
				}
			} else if (startDate != null){
				ps.setString(1, startDate);
				if (endDate != null) {
					ps.setString(2, endDate);
				}
			} else if (endDate != null) {
				ps.setString(1, endDate);
			}
			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toMessageComment(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toMessageComment(ResultSet rs)
			throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("message_id");
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				String name = rs.getString("name");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage message = new UserMessage();
				message.setUserId(userId);
				message.setMessageId(messageId);
				message.setSubject(subject);
				message.setText(text);
				message.setCategory(category);
				message.setName(name);
				message.setInsertDate(insertDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
