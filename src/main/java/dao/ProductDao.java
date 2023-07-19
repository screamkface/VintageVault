package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import beans.ProductBean;
import interfaces.IProductDao;
import others.CartItem;

public class ProductDao implements IProductDao {
	private static final String TABLE_INFO_PRODOTTO = "INFO_PRODOTTO";
	private static final String TABLE_PROD_IN_VENDITA = "PROD_IN_VENDITA";
	private DataSource ds = null;

	public ProductDao(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void doSave(ProductBean pb) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query1 = "INSERT INTO " + ProductDao.TABLE_INFO_PRODOTTO
				+ " (nome, descrizione, tipo, immagine) VALUES (?, ?, ?, ?)";
		String query2 = "INSERT INTO " + ProductDao.TABLE_PROD_IN_VENDITA
				+ " (id_info_prodotto, prezzo, quantita, condizione) VALUES (?, ?, ?, ?)";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, pb.getNome());
			p.setString(2, pb.getDescrizione());
			p.setString(3, pb.getTipo());
			p.setBinaryStream(4, pb.getImmagineIS());
			p.executeUpdate();

			// prendo l'id della nuova row creata
			ResultSet rs = p.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			rs.close();
			p.close();

			// metto il prodotto in vendita
			p = c.prepareStatement(query2);
			p.setInt(1, id);
			p.setDouble(2, pb.getPrezzo());
			p.setInt(3, pb.getQuantita());
			p.setString(4, pb.getCondizione());
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
	public byte[] getProductImageById(int id) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		byte[] bytes = null;

		String query = "SELECT immagine FROM " + ProductDao.TABLE_INFO_PRODOTTO + " WHERE id = ?";
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				bytes = rs.getBytes("immagine");
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

		return bytes;
	}

	@SuppressWarnings("null")
	@Override
	public ProductBean getProductById(int id) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		ProductBean pb = null;

		String query = 
				"SELECT * FROM " + ProductDao.TABLE_PROD_IN_VENDITA 
				+ " INNER JOIN " + ProductDao.TABLE_INFO_PRODOTTO
				+ " ON id_info_prodotto = id " 
				+ " WHERE id = ?";
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				pb = new ProductBean();
				pb.setId(rs.getInt("id"));
				pb.setPrezzo(rs.getDouble("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setCondizione(rs.getString("condizione"));
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));
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
		return pb;
	}

	@Override
	public List<ProductBean> getAllProducts(int limit, int offset) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		List<ProductBean> pbs = new ArrayList<>();

		String query = "SELECT * FROM " + ProductDao.TABLE_PROD_IN_VENDITA + " INNER JOIN "
				+ ProductDao.TABLE_INFO_PRODOTTO + " ON id_info_prodotto = id " + " LIMIT ? OFFSET ?";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, limit);
			p.setInt(2, offset);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setId(rs.getInt("id"));
				pb.setPrezzo(rs.getDouble("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setCondizione(rs.getString("condizione"));
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));
				pbs.add(pb);
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
		return pbs;
	}

	@Override
	public void editProduct(int id, double prezzo, int quantita) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query = "UPDATE " + ProductDao.TABLE_PROD_IN_VENDITA 
					+ " SET prezzo = ?, quantita = ?"
					+ " WHERE id_info_prodotto = ?"; 

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setDouble(1, prezzo);
			p.setInt(2, quantita);
			p.setInt(3, id);
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
	public void deleteProduct(int id) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query = "DELETE FROM " + ProductDao.TABLE_PROD_IN_VENDITA + " WHERE id_info_prodotto = ?";

		try {
			c = ds.getConnection();

			p = c.prepareStatement(query);
			p.setInt(1, id);
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
	public List<ProductBean> getFilteredProducts(String search, String condizione, double min_price, double max_price, String ordina,
			List<String> categorie, int limit, int offset) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;
		List<ProductBean> pbs = new ArrayList<>();

		// where condition
		String where = "";
		if (condizione != null) {
			where += "(condizione = ?)";
		}
		if (search != null) {
			where += "(nome LIKE ?)";
		}
		if (min_price != 0.0) {
			where += " AND (prezzo >= ?)";
		}
		if (max_price != 0.0) {
			where += " AND (prezzo <= ?)";
		}

		if (categorie != null && categorie.size() > 0) {
			where += " AND (";
			for (String cat : categorie) {
				where += "tipo = ? OR ";
			}
			where = where.substring(0, where.length() - 4);
			where += ")";
		}

		String ordinamento = "";
		if (ordina != null && ordina.equals("prezzo_asc"))
			ordinamento = " ORDER BY prezzo ASC";
		else if (ordina != null && ordina.equals("prezzo_desc"))
			ordinamento = " ORDER BY prezzo DESC";
		
		if(!where.isBlank())
			where = " WHERE "+where;

		String query = "SELECT * FROM " + ProductDao.TABLE_PROD_IN_VENDITA 
				+ " INNER JOIN "
				+ ProductDao.TABLE_INFO_PRODOTTO 
				+ " ON id_info_prodotto = id " 
				+ where 
				+ ordinamento
				+ " LIMIT ? OFFSET ?";

		//System.out.println(query);

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			int statementCount = 0;
			if (condizione != null) {
				p.setString(++statementCount, condizione);
			}
			if (search != null) {
				p.setString(++statementCount, "%"+search+"%");
			}
			if (min_price != 0.0) {
				p.setDouble(++statementCount, min_price);	
			}
			if (max_price != 0.0) {
				p.setDouble(++statementCount, max_price);
			}

			if (categorie != null && categorie.size() > 0) {
				int index = 1;
				for (String cat : categorie)
					p.setString(statementCount + index++, cat);
			}

			p.setInt(statementCount + categorie.size() + 1, limit);
			p.setInt(statementCount + categorie.size() + 2, offset);

			//System.out.println(p);

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setId(rs.getInt("id"));
				pb.setPrezzo(rs.getInt("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setCondizione(rs.getString("condizione"));
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));
				pbs.add(pb);
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
		return pbs;
	}

	@Override
	public List<ProductBean> getSearchProducts(String search) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;
		List<ProductBean> pbs = new ArrayList<>();
		
		String query = "SELECT * FROM " + ProductDao.TABLE_PROD_IN_VENDITA 
				+ " INNER JOIN "
				+ ProductDao.TABLE_INFO_PRODOTTO 
				+ " ON id_info_prodotto = id "
				+ " WHERE nome LIKE ? "
				+ " LIMIT 5"; 
		
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			
			p.setString(1, '%'+search+'%');

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setId(rs.getInt("id"));
				pb.setPrezzo(rs.getInt("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setCondizione(rs.getString("condizione"));
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));
				pbs.add(pb);
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
		return pbs;
	}
}
