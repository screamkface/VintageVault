package Cart;

import java.util.ArrayList;
import java.util.List;

import Beans.ProductBean;

public class Cart {
	private List<CartItem> items;

	public Cart() {
		items = new ArrayList<>();
	}

	public void addProduct(ProductBean product) {
		for (CartItem item : items) {
			if (item.getProductBean().getId() == product.getId()) {
				item.setQuantita(item.getQuantita() + 1);
				return;
			}
		}
		items.add(new CartItem(product, 1));
	}

	public void deleteProduct(ProductBean product) {
		for (CartItem item : items) {
			if (item.getProductBean().getId() == product.getId()) {
				if (item.getQuantita() > 1) {
					item.setQuantita(item.getQuantita() - 1);
				} else {
					items.removeIf((a) -> a.getProductBean().getId() == product.getId());
				}
				return;
			}
		}
	}

	public List<CartItem> getProducts() {
		return items;
	}

	public int getItemsCount() {
		return items.stream().mapToInt(i -> i.getQuantita()).sum();
	}

	public double getTotale() {
		return items.stream().mapToDouble(i -> i.getProductBean().getPrezzo() * i.getQuantita()).sum();
	}
}
