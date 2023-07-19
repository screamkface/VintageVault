package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import beans.ProductBean;
import beans.ProductWarehouseBean;
import interfaces.IProductWarehouseDao;

public class ProductWarehouseDao implements IProductWarehouseDao
{
	private static final String TABLE_INFO_PRODOTTO = "INFO_PRODOTTO";
	private static final String TABLE_RICHIESTA_VENDITA = "RICHIESTA_VENDITA";
	private static final String TABLE_PROD_IN_VENDITA = "PROD_IN_VENDITA";
	private DataSource ds = null;

	public ProductWarehouseDao(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void addRequest(ProductBean pb, int id_utente) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query1 = "INSERT INTO " + TABLE_INFO_PRODOTTO
				+ " (nome, descrizione, tipo, immagine) VALUES (?, ?, ?, ?)";
		String query2 = "INSERT INTO " + TABLE_RICHIESTA_VENDITA
				+ " (id_info_prodotto, prezzo, stato, id_utente) VALUES (?, ?, ?, ?)";

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

			// aggiungo il prodotto alle richieste vendita
			p = c.prepareStatement(query2);
			p.setInt(1, id);
			p.setDouble(2, pb.getPrezzo());
			p.setString(3, "IN ATTESA DI REVISIONE");
			p.setInt(4, id_utente);
			System.out.println(p);
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
	public List<ProductWarehouseBean> getRichiestaVenditaByIdUtente(int id_utente) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;
		ProductWarehouseBean pb = null;
		
		List<ProductWarehouseBean> pbs = new ArrayList<>();

		String query = 
				"SELECT * FROM " + TABLE_RICHIESTA_VENDITA + " as rv"
				+ " INNER JOIN " + TABLE_INFO_PRODOTTO + " as ip"
				+ " ON rv.id_info_prodotto = ip.id " 
				+ " WHERE rv.id_utente = ?";
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, id_utente);
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				pb = new ProductWarehouseBean();
				pb.setId(rs.getInt("id_info_prodotto"));
				pb.setPrezzo(rs.getDouble("prezzo"));
				pb.setCondizione("warehouse");
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));
				pb.setStato(rs.getString("stato"));
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
	public List<ProductWarehouseBean> getAllNewRichiesteVendita() throws SQLException {
		Connection c = null;
		PreparedStatement p = null;
		ProductWarehouseBean pb = null;
		
		List<ProductWarehouseBean> pbs = new ArrayList<>();

		String query = 
				"SELECT rv.id, prezzo, id_info_prodotto, nome, descrizione, tipo, stato FROM " + TABLE_RICHIESTA_VENDITA + " as rv"
				+ " INNER JOIN " + TABLE_INFO_PRODOTTO + " as ip"
				+ " ON rv.id_info_prodotto = ip.id "
				+ " WHERE stato = 'IN ATTESA DI REVISIONE'";
		
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				pb = new ProductWarehouseBean();
				pb.setId(rs.getInt("id_info_prodotto"));
				pb.setPrezzo(rs.getDouble("prezzo"));
				pb.setCondizione("warehouse");
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));
				pb.setStato(rs.getString("stato"));
				pb.setId_richiesta_vendita(rs.getInt("id"));
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
	public ProductWarehouseBean getRichiestaVenditaById(int id) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		ProductWarehouseBean pb = null;

		String query = 
				"SELECT rv.id, prezzo, id_info_prodotto, nome, descrizione, tipo, stato FROM " + TABLE_RICHIESTA_VENDITA + " as rv"
				+ " INNER JOIN " + TABLE_INFO_PRODOTTO + " as ip"
				+ " ON rv.id_info_prodotto = ip.id "
				+ " WHERE rv.id = ?";
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				pb = new ProductWarehouseBean();
				pb.setId(rs.getInt("id"));
				pb.setPrezzo(rs.getDouble("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setCondizione("warehouse");
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));
				pb.setStato(rs.getString("stato"));
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
	public void changeRichiestaVenditaStato(int id, String stato) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query = "UPDATE " + TABLE_RICHIESTA_VENDITA
					+ " SET stato = ?"
					+ " WHERE id = ?"; 

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setString(1, stato);
			p.setInt(2, id);
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
	public void addRichiestaToProdInVendita(int id, double prezzo) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query1 = "SELECT id_info_prodotto FROm " + TABLE_RICHIESTA_VENDITA
						+ " WHERE id = ?";
		String query2 = "INSERT INTO " + TABLE_PROD_IN_VENDITA
				+ " (id_info_prodotto, prezzo, quantita, condizione) VALUES (?, ?, ?, ?)";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query1);
			p.setInt(1, id);
			p.executeQuery();
			ResultSet rs = p.executeQuery();
			
			rs.next();
			int id_info_prodotto = rs.getInt("id_info_prodotto");
			rs.close();
			p.close();

			// metto il prodotto in vendita
			p = c.prepareStatement(query2);
			p.setInt(1, id_info_prodotto);
			p.setDouble(2, prezzo);
			p.setInt(3, 1);
			p.setString(4, "warehouse");
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

}
