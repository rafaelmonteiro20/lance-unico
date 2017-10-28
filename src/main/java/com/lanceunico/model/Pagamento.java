package com.lanceunico.model;

import java.time.LocalDate;

public class Pagamento {

	private LocalDate data;
	
	private double valor;

	public Pagamento(LocalDate data, double valor) {
		this.data = data;
		this.valor = valor;
	}
	
	public LocalDate getData() {
		return data;
	}	
	
	public double getValor() {
		return valor;
	}
	
}
