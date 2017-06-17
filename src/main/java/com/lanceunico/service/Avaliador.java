package com.lanceunico.service;

import com.lanceunico.model.Lance;
import com.lanceunico.model.Leilao;

public class Avaliador {

	private double menorLance = Double.MAX_VALUE;
	private double maiorLance = Double.MIN_VALUE;
	
	public void avaliar(Leilao leilao) {
		for (Lance lance : leilao.getLances()) {
			if(lance.getValor() < menorLance) {
				menorLance = lance.getValor();
			}
			
			if(lance.getValor() > maiorLance) {
				maiorLance = lance.getValor();
			}
		} 
	}

	public double getMenorLance() {
		return menorLance;
	}
	
	public double getMaiorLance() {
		return maiorLance;
	}
	
}