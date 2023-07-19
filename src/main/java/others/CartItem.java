package others;

import beans.ProductBean;

public class CartItem {
	private ProductBean productBean;
	private int quantita;

	public CartItem(ProductBean productBean, int quantita) {
		this.productBean = productBean;
		this.quantita = quantita;
	}

	public ProductBean getProductBean() {
		return productBean;
	}

	public void setProductBean(ProductBean pb) {
		this.productBean = pb;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
}
