package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bbs.beans.Message;
import bbs.exception.SQLRuntimeException;

public class MessageDao {

	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("insert into messages (");
			mySql.append("user_id");
			mySql.append(", subject ");
			mySql.append(", text ");
			mySql.append(", category ");
			mySql.append(", insert_date ");
			mySql.append(") values (");
			mySql.append("?");
			mySql.append(", ?");
			mySql.append(", ?");
			mySql.append(", ?");
			mySql.append(", current_timestamp");
			mySql.append(")");

			ps = connection.prepareStatement(mySql.toString());

			ps.setInt(1, message.getUserId());
			ps.setString(2, message.getSubject());
			ps.setString(3, message.getText());
			ps.setString(4, message.getCategory());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public Message getMessage(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("select * from messages where ");
			mySql.append("user_id = ? ");
			mySql.append("and subject = ? ");
			mySql.append("and text = ? ");
			mySql.append("and category = ? ");

			ps = connection.prepareStatement(mySql.toString());

			ps.setInt(1, message.getUserId());
			ps.setString(2, message.getSubject());
			ps.setString(3, message.getText());
			ps.setString(4, message.getCategory());

			ResultSet rs = ps.executeQuery();
			List<Message> massageList = toMessageList(rs);
			if (massageList.isEmpty() == true) {
				return null;
			} else if (2 <= massageList.size()) {
				throw new IllegalStateException("2 <= massageList.size()");
			} else {
				return massageList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<Message> toMessageList(ResultSet rs) throws SQLException {

		List<Message> ret = new ArrayList<Message>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Date insertDate = rs.getDate("insert_date");

				Message message = new Message();
				message.setId(id);
				message.setUserId(userId);
				message.setSubject(subject);
				message.setText(text);
				message.setCategory(category);
				message.setInsertDate(insertDate);


				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
