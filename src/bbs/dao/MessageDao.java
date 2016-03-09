package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
