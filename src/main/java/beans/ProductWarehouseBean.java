package beans;

import java.io.InputStream;
import java.io.Serializable;

public class ProductWarehouseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String nome;
	private String descrizione;
	private String tipo;
	private double prezzo;
	private int quantita;
	private String condizione;
	private InputStream immagineIS;
	
	private String stato;
	private int id_richiesta_vendita;

	public int getId_richiesta_vendita() {
		return id_richiesta_vendita;
	}

	public void setId_richiesta_vendita(int id_richiesta_vendita) {
		this.id_richiesta_vendita = id_richiesta_vendita;
	}

	public ProductWarehouseBean() {
		id = -1;
		nome = "";
		descrizione = "";
		prezzo = 0;
		quantita = 0;
		stato="";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getCondizione() {
		return condizione;
	}

	public void setCondizione(String condizione) {
		this.condizione = condizione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public InputStream getImmagineIS() {
		return immagineIS;
	}

	public void setImmagineIS(InputStream immagineIS) {
		this.immagineIS = immagineIS;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
}
