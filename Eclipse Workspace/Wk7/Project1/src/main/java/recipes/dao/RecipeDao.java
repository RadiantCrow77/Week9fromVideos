package recipes.dao;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import provided.util.DaoBase;
import recipes.exception.DbException;

public class RecipeDao extends DaoBase {
	public void executeBatch(List<String> sqlBatch) {
		try (Connection conn = DbConnection.getConnection()) {
			startTransaction(conn);

			try (Statement stmt = conn.createStatement()) {
				for (String sql : sqlBatch) {
					stmt.addBatch(sql); // add ea sql stmt as a batch to the statement,
				}

				stmt.executeBatch(); // execute batch and commit
				commitTransaction(conn);
			} catch (Exception e) { // rollback transaction
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}
}
