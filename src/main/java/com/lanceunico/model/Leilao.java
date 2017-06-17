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

	public void propoe(Usuario usuario, double valor) {
		this.lances.add(new Lance(usuario, valor));
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public List<Lance> getLances() {
		return Collections.unmodifiableList(this.lances);
	}
	
}