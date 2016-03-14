package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Position;
import bbs.dao.PositionDao;

public class PositionService {

	public List<Position> select() {
		Connection connection = null;
		try {
			connection = getConnection();
			PositionDao positionDao = new PositionDao();
			List<Position> position = positionDao.getPositionList(connection);

			commit(connection);
			return position;
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
	public Position getPosition(int positionId) {
		Connection connection = null;
		try {
			connection = getConnection();
			PositionDao positionDao = new PositionDao();
			Position position = positionDao.getPosition(connection, positionId);

			commit(connection);
			return position;
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
