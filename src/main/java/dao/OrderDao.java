package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import beans.OrderBean;
import beans.ProductBean;
import interfaces.IOrderDao;
import others.CartItem;

public class OrderDao implements IOrderDao {
	// private static final String TABLE_INFO_PRODOTTO = "INFO_PRODOTTO";
	private static final String TABLE_PROD_IN_VENDITA = "PROD_IN_VENDITA";
	private static final String TABLE_ORDINE = "ORDINE";
	private static final String TABLE_ORDER_ITEM = "ORDER_ITEM";
	private DataSource ds = null;

	public OrderDao(DataSource ds) {
		this.ds = ds;
	}

	public OrderDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effettuaOrdine(List<CartItem> cis, int id_utente, String indirizzo) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		String query1 = "INSERT INTO " + TABLE_ORDINE + " (id_utente, indirizzo)" + " VALUES (?,?)";
		String query2 = "INSERT INTO " + TABLE_ORDER_ITEM + " (id_ordine, id_info_prodotto, quantita, prezzo)"
				+ " VALUES (?,?,?,?)";
		String queryDiminuisciQuantita = "UPDATE " + TABLE_PROD_IN_VENDITA + " SET quantita = quantita - ?"
				+ " WHERE id_info_prodotto = ?";
		String queryRemoveProdottiFiniti = "DELETE FROM " + TABLE_PROD_IN_VENDITA + " WHERE quantita = 0 ";

		for (int i = 1; i < cis.size(); i++) {
			query2 += ",(?,?,?,?)";
		}

		try {
			c = ds.getConnection();
			// prendo l'id della nuova row creata
			p = c.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, id_utente);
			p.setString(2, indirizzo);
			p.executeUpdate();
			ResultSet rs = p.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			rs.close();
			p.close();

			p = c.prepareStatement(query2);
			for (int i = 0; i < cis.size(); i++) {
				p.setInt((i * 4) + 1, id);
				p.setInt((i * 4) + 2, cis.get(i).getProductBean().getId());
				p.setInt((i * 4) + 3, cis.get(i).getQuantita());
				p.setDouble((i * 4) + 4, cis.get(i).getProductBean().getPrezzo());
				PreparedStatement p_diminuisci = c.prepareStatement(queryDiminuisciQuantita);
				p_diminuisci.setInt(1, cis.get(i).getQuantita());
				p_diminuisci.setInt(2, cis.get(i).getProductBean().getId());
				p_diminuisci.executeUpdate();
				p_diminuisci.close();
			}
			p.executeUpdate();
			p.close();

			p = c.prepareStatement(queryRemoveProdottiFiniti);
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

	public OrderBean getOrderById(int orderId) throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		OrderBean order = null;

		String query = "SELECT o.id_ordine, o.id_utente, o.data, o.indirizzo, oi.id_info_prodotto, oi.quantita, oi.prezzo, ip.nome, ip.descrizione, ip.tipo "
				+ "FROM ORDINE as o" + " INNER JOIN ORDER_ITEM as oi ON o.id = oi.id_ordine"
				+ " INNER JOIN INFO_PRODOTTO as ip ON ip.id = oi.id_info_prodotto" + " WHERE o.id_ordine = ?" // Aggiungiamo
																												// //
																												// dell'ordine
				+ " ORDER BY o.data DESC";

		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, orderId); // Impostiamo il parametro per l'ID dell'ordine

			ResultSet rs = p.executeQuery();
			List<ProductBean> products = new ArrayList<>(); // Crea una nuova lista di prodotti per l'ordine
			while (rs.next()) {
				if (order == null) {
					int userId = rs.getInt("id_utente");
					String date = rs.getString("data");
					String address = rs.getString("indirizzo");
					order = new OrderBean(products, date, address, userId, orderId);
				}

				ProductBean pb = new ProductBean();
				pb.setId(rs.getInt("id_info_prodotto"));
				pb.setPrezzo(rs.getInt("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));

				products.add(pb); // Aggiungi il prodotto alla lista di ProductBean
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

		return order;
	}

	public List<OrderBean> cercaPerData(String dataDa, String dataA) throws SQLException {
		List<OrderBean> ordini = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM ordine WHERE data >= ? AND data <= ?");
			stmt.setString(1, dataDa);
			stmt.setString(2, dataA);
			rs = stmt.executeQuery();

			while (rs.next()) {
				OrderBean ob = new OrderBean();
				ob.setIdOrdine(rs.getInt("id"));
				ob.setData(rs.getString("data"));
				ob.setIdUtente(rs.getInt("id_utente"));
				ob.setIndirizzo(rs.getString("indirizzo"));

				// Recupera i prodotti dell'ordine
				List<ProductBean> pb = new ArrayList<>();
				PreparedStatement stmt2 = conn
						.prepareStatement("SELECT ip.nome, ip.descrizione, ip.immagine, p.prezzo, p.quantita\n"
								+ "FROM prod_in_vendita p\n" + "JOIN info_prodotto ip ON p.id_info_prodotto = ip.id\n"
								+ "WHERE p.id_info_prodotto IN (SELECT id_info_prodotto FROM order_item WHERE id_ordine = ?);");
				stmt2.setInt(1, ob.getIdOrdine());
				ResultSet rs2 = stmt2.executeQuery();
				while (rs2.next()) {
					ProductBean prodotto = new ProductBean();
					prodotto.setId(rs2.getInt("id_info_prodotto"));
					prodotto.setNome(rs2.getString("nome"));
					prodotto.setPrezzo(rs2.getDouble("prezzo"));
					prodotto.setQuantita(rs2.getInt("quantita"));
					pb.add(prodotto);

				}

				ob.setPb(pb);

				ordini.add(ob);
			}
		} finally {
			conn.close();
		}
		return ordini;
	}

	@Override
	public List<OrderBean> getOrdersFromUser(int id_utente) throws SQLException {
		Connection c = null;

		PreparedStatement p = null;

		List<OrderBean> ordini = new ArrayList<>();

		String query = "SELECT id_ordine, id_utente, id_info_prodotto, quantita, prezzo, data, indirizzo, ip.nome, descrizione, tipo"
				+ "	FROM ORDER_ITEM as oi" + " INNER JOIN ORDINE as o ON o.id = oi.id_ordine"
				+ " INNER JOIN UTENTE as u ON o.id_utente = u.id"
				+ " INNER JOIN INFO_PRODOTTO as ip ON ip.id = oi.id_info_prodotto" + "	WHERE id_utente = ?"
				+ "	ORDER BY data desc";
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);
			p.setInt(1, id_utente);

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setId(rs.getInt("id_info_prodotto"));
				pb.setPrezzo(rs.getInt("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));

				int curr_id_ordine = rs.getInt("id_ordine");
				int curr_id_utente = rs.getInt("id_utente");
				OrderBean ob = ordini.stream().filter((e) -> e.getIdOrdine() == curr_id_ordine).findFirst()
						.orElse(null);
				if (ob == null) {
					List<ProductBean> pbs = new ArrayList<>();

					pbs.add(pb);

					ordini.add(new OrderBean(pbs, rs.getString("data"), rs.getString("indirizzo"), curr_id_utente,
							curr_id_ordine));
				} else {
					ob.getPb().add(pb);
				}
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

		return ordini;
	}

	@Override
	public List<OrderBean> getAllOrders() throws SQLException {
		Connection c = null;
		PreparedStatement p = null;

		List<OrderBean> ordini = new ArrayList<>();

		String query = "SELECT id_ordine, id_utente, id_info_prodotto, quantita, prezzo, data, indirizzo, ip.nome, descrizione, tipo"
				+ "	FROM ORDER_ITEM as oi" + " INNER JOIN ORDINE as o ON o.id = oi.id_ordine"
				+ " INNER JOIN UTENTE as u ON o.id_utente = u.id"
				+ " INNER JOIN INFO_PRODOTTO as ip ON ip.id = oi.id_info_prodotto" + "	ORDER BY data desc";
		try {
			c = ds.getConnection();
			p = c.prepareStatement(query);

			System.out.print(p);

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setId(rs.getInt("id_info_prodotto"));
				pb.setPrezzo(rs.getInt("prezzo"));
				pb.setQuantita(rs.getInt("quantita"));
				pb.setNome(rs.getString("nome"));
				pb.setDescrizione(rs.getString("descrizione"));
				pb.setTipo(rs.getString("tipo"));

				int curr_id_ordine = rs.getInt("id_ordine");
				int curr_id_utente = rs.getInt("id_utente");
				OrderBean ob = ordini.stream().filter((e) -> e.getIdOrdine() == curr_id_ordine).findFirst()
						.orElse(null);
				if (ob == null) {
					List<ProductBean> pbs = new ArrayList<>();
					pbs.add(pb);
					ordini.add(new OrderBean(pbs, rs.getString("data"), rs.getString("indirizzo"), curr_id_utente,
							curr_id_ordine));
				} else {
					ob.getPb().add(pb);
				}
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

		System.out.println(ordini);

		return ordini;
	}

}
