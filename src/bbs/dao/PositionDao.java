package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Position;
import bbs.exception.SQLRuntimeException;

public class PositionDao {

	public List<Position> getPositionList(Connection connection) {

		try {
			Statement statement = connection.createStatement();
			String mySql = "select * from positions";
			ResultSet rs = statement.executeQuery(mySql);
			List<Position> positionList = toPositionList(rs);
			if (positionList.isEmpty() == true) {
				return null;
			} else {
				return positionList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	private List<Position> toPositionList(ResultSet rs) throws SQLException {

		List<Position> ret = new ArrayList<Position>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Position position = new Position();
				position.setId(id);
				position.setName(name);

				ret.add(position);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public Position getPosition(Connection connection, int positionId) {

		PreparedStatement ps = null;
		try {
			StringBuilder mySql = new StringBuilder();
			mySql.append("select * from positions where id = ? ");

			ps = connection.prepareStatement(mySql.toString());

			ps.setInt(1, positionId);

			ResultSet rs = ps.executeQuery();
			List<Position> positionList = toPositionList(rs);
			if (positionList.isEmpty() == true) {
				return null;
			} else if (2 <= positionList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return positionList.get(0);
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
