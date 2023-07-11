package Interfacce;

import java.sql.SQLException;
import java.util.List;

import Beans.ProductBean;

public interface ProductDatoInterface {
	public void doSave(ProductBean pb) throws SQLException;

	public byte[] getProductImageById(int id) throws SQLException;

	public ProductBean getProductById(int id) throws SQLException;

	public List<ProductBean> getAllProducts(int limit, int offset) throws SQLException;

	public List<ProductBean> getFilteredProducts(String search, String condizione, double min_price, double max_price,
			String ordina, List<String> categorie, int limit, int offset) throws SQLException;

	public List<ProductBean> getSearchProducts(String search) throws SQLException;

	public void editProduct(int id, double prezzo, int quantita) throws SQLException;

	public void deleteProduct(int id) throws SQLException;
}
