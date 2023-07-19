package interfaces;

import java.sql.SQLException;

import beans.ProductBean;
import beans.ProductWarehouseBean;
import others.CartItem;

import java.util.List;

public interface IProductWarehouseDao {
	public void addRequest(ProductBean pb, int id_utente) throws SQLException;
	
	public List<ProductWarehouseBean> getRichiestaVenditaByIdUtente(int id_utente) throws SQLException;
	
	public List<ProductWarehouseBean> getAllNewRichiesteVendita() throws SQLException;
	
	public ProductWarehouseBean getRichiestaVenditaById(int id) throws SQLException;
	
	public void changeRichiestaVenditaStato(int id, String stato) throws SQLException;
	
	public void addRichiestaToProdInVendita(int id, double prezzo) throws SQLException;
}
