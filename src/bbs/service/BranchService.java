package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Branch;
import bbs.dao.BranchDao;

public class BranchService {

	public List<Branch> select() {
		Connection connection = null;
		try {
			connection = getConnection();
			BranchDao branchDao = new BranchDao();
			List<Branch> branch = branchDao.getBranchList(connection);

			commit(connection);
			return branch;
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
