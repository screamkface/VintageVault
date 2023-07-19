package dao;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import beans.UserBean;
import interfaces.IUserDao;

public class UserDao implements IUserDao {

	private static final String TABLE_NAME = "UTENTE";
	private DataSource ds = null;

	public UserDao(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public synchronized void doSave(UserBean ub) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query = "INSERT INTO " + UserDao.TABLE_NAME + " (nome, cognome, email, password) VALUES (?, ?, ?, ?)";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setString(1, ub.getNome());
			p.setString(2, ub.getCognome());
			p.setString(3, ub.getEmail());
			p.setString(4, toHash(ub.getPassword()));
			p.executeUpdate();
		} finally {
			try {
				if (p != null)
					p.close();
			} finally {
				if (c != null)
					c.close();
			}
		}
	}

	@Override
	public synchronized UserBean doRetrieveByEmailAndPass(String email, String password) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		UserBean bean = null;

		String query = "SELECT * FROM " + UserDao.TABLE_NAME + " WHERE email = ? AND password = ?";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setString(1, email);
			p.setString(2, toHash(password));

			ResultSet rs = p.executeQuery();

			if (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				// bean.setPassword(rs.getString("password"));
			}
		} finally {
			try {
				if (p != null)
					p.close();
			} finally {
				if (c != null)
					c.close();
			}
		}

		return bean;
	}

	@Override
	public synchronized boolean checkUserEmailExistance(String email) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		boolean exists = false;

		String query = "SELECT * FROM " + UserDao.TABLE_NAME + " WHERE email = ?";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setString(1, email);

			ResultSet rs = p.executeQuery();

			if (rs.next())
				exists = true;

		} finally {
			try {
				if (p != null)
					p.close();
			} finally {
				if (c != null)
					c.close();
			}
		}

		return exists;
	}

	private String toHash(String password) {
		String hashString = null;
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			hashString = "";
			for (int i = 0; i < hash.length; i++) {
				hashString += Integer.toHexString((hash[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3);
			}
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return hashString;
	}

	@Override
	public synchronized boolean checkUserIsAdmin(int id) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		boolean isAdmin = false;

		String query = "SELECT * FROM ADMIN WHERE id = ?";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, id);

			ResultSet rs = p.executeQuery();

			if (rs.next())
				isAdmin = true;

		} finally {
			try {
				if (p != null)
					p.close();
			} finally {
				if (c != null)
					c.close();
			}
		}
		return isAdmin;
	}

}
