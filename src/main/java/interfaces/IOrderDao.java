package interfaces;


import java.sql.SQLException;
import java.util.List;

import beans.OrderBean;
import beans.ProductBean;
import others.CartItem;

public interface IOrderDao {

	public void effettuaOrdine(List<CartItem> cis, int id_utente, String indirizzo) throws SQLException;

	public List<OrderBean> getOrdersFromUser(int id_utente)throws SQLException;
	
	public List<OrderBean> getAllOrders()throws SQLException;
}
