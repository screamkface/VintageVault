package Interfacce;

import java.sql.SQLException;
import java.util.List;

import Beans.OrderBean;
import Cart.CartItem;

public interface OrderDaoInterface {

	public void effettuaOrdine(List<CartItem> cis, int id_utente, String indirizzo) throws SQLException;

	public List<OrderBean> getOrdersFromUser(int id_utente) throws SQLException;

	public List<OrderBean> getAllOrders() throws SQLException;
}
