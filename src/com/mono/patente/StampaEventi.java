package com.mono.patente;

public class StampaEventi {
	private String desc;
	private String data;
	private String punti;
	private String ente;
	private String dataevento;
	
	// Constructor for the Phonebook class
	public StampaEventi(String desc, String data, String punti, String dataevento, String ente) {
		super();
		this.desc = desc;
		this.data = data;
		this.punti = punti;
		this.ente = ente;
		this.dataevento= dataevento;
	}
	
	// Getter and setter methods for all the fields.
	// Though you would not be using the setters for this example,
	// it might be useful later.
	public String getName() {
		return desc;
	}
	public void setName(String desc) {
		this.desc = desc;
	}
	public String getPhone() {
		return data;
	}
	public void setPhone(String data) {
		this.data = data;
	}
	public String getMail() {
		return punti;
	}
	public void setMail(String punti) {
		this.punti = punti;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getDataE() {
		return dataevento;
	}
	public void setDataE(String dataevento) {
		this.dataevento = dataevento;
	}
}
