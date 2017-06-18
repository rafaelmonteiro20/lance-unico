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
		if(naoTemLances() || podeDarLance(lance)) {
			this.lances.add(lance);
		}
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
	
	private boolean podeDarLance(Lance lance) {
		Usuario usuario = lance.getUsuario();
		
		return naoDeuUltimoLance(usuario) 
				&& quantidadeDeLancesDo(usuario) < 5;
	}
	
	private boolean naoDeuUltimoLance(Usuario usuario) {
		return !ultimoLanceDado().getUsuario()
			.equals(usuario);
	}
	
	private int quantidadeDeLancesDo(Usuario usuario) {
		int total = 0;
		
		for (Lance l : lances) {
			if(l.getUsuario().equals(usuario)) {
				total++;
			}
		}
		
		return total;
	}
	
	public Lance ultimoLanceDado() {
		if(naoTemLances())
			return null;
		
		return lances.get(quantidadeLances() - 1);
	}
	
}