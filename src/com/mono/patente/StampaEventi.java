/* Copyright (C) 2010 Lorenzo Braghetto
 * 
 * This file is part of Patente.
 * 
 *   Patente is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *  Patente is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
*/

package com.mono.patente;

public class StampaEventi {
	private String var1;
	private String var2;
	private String var3;
	private String var4;
	private String var5;
	private String var6;

	
	// Constructor for the Phonebook class
	public StampaEventi(String var1, String var2, String var3, String var4, String var5, String var6) {
		super();
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
		this.var4 = var4;
		this.var5 = var5;
		this.var6 = var6;
	}
	
	// Getter and setter methods for all the fields.
	// Though you would not be using the setters for this example,
	// it might be useful later.
	public String getName() {
		return var1;
	}
	public void setName(String var1) {
		this.var1 = var1;
	}
	public String getPhone() {
		return var2;
	}
	public void setPhone(String var2) {
		this.var2 = var2;
	}
	public String getMail() {
		return var3;
	}
	public void setMail(String var3) {
		this.var3 = var3;
	}
	public String getEnte() {
		return var4;
	}
	public void setEnte(String var4) {
		this.var4 = var4;
	}
	public String getDataE() {
		return var5;
	}
	public void setDataE(String var5) {
		this.var5 = var5;
	}
	public String getData6() {
		return var6;
	}
	public void setData6(String var6) {
		this.var5 = var6;
	}
}
