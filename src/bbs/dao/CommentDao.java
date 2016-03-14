package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Comment;
import bbs.exception.SQLRuntimeException;

public class CommentDao {

	public void delte(Connection connection, int messageId) {
		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("delete from comments where message_id = ?");

			ps = connection.prepareStatement(mySql.toString());

			ps.setInt(1, messageId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("insert into comments (");
			mySql.append("user_id");
			mySql.append(", message_id");
			mySql.append(", text");
			mySql.append(", insert_date");
			mySql.append(") values (");
			mySql.append("?");
			mySql.append(", ?");
			mySql.append(", ?");
			mySql.append(", current_timestamp");
			mySql.append(")");

			ps = connection.prepareStatement(mySql.toString());

			ps.setInt(1, comment.getUserId());
			ps.setInt(2, comment.getMessageId());
			ps.setString(3, comment.getText());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Comment> getComment(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("select * from comments ");

			ps = connection.prepareStatement(mySql.toString());

			ResultSet rs = ps.executeQuery();
			List<Comment> ret = toComment(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Comment> toComment(ResultSet rs)
			throws SQLException {

		List<Comment> ret = new ArrayList<Comment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("message_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				Comment comment = new Comment();
				comment.setId(id);
				comment.setUserId(userId);
				comment.setMessageId(messageId);
				comment.setText(text);
				comment.setInsertDate(insertDate);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
