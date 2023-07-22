package beans;

import java.io.Serializable;
import java.util.List;

public class OrderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ProductBean> pb;
	private String data;
	private String indirizzo;
	private int idUtente;
	private int idOrdine;

	public OrderBean() {
		// TODO Auto-generated constructor stub
	}

	public OrderBean(List<ProductBean> pb, String data, String indirizzo, int idUtente, int idOrdine) {
		super();
		this.pb = pb;
		this.data = data;
		this.indirizzo = indirizzo;
		this.idUtente = idUtente;
		this.idOrdine = idOrdine;
	}

	public List<ProductBean> getPb() {
		return pb;
	}

	public void setPb(List<ProductBean> pb) {
		this.pb = pb;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

}
