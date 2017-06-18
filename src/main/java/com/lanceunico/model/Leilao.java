package com.lanceunico.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;

	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<>();
	}

	public void propoe(Lance lance) {
		this.lances.add(lance);
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public List<Lance> getLances() {
		return Collections.unmodifiableList(this.lances);
	}

	public boolean naoTemLances() {
		return lances.isEmpty();
	}
	
	public int quantidadeLances() {
		return lances.size();
	}
	
}