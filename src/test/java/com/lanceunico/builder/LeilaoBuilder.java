package com.lanceunico.builder;

import java.time.LocalDate;

import com.lanceunico.model.Lance;
import com.lanceunico.model.Leilao;
import com.lanceunico.model.Usuario;

public class LeilaoBuilder {

	private Leilao leilao;
	
	public LeilaoBuilder para(String descricao) {
		this.leilao = new Leilao(descricao);
		return this;
	}
	
	public LeilaoBuilder comLance(Usuario usuario, double valor) {
		this.leilao.propoe(new Lance(usuario, valor));
		return this;
	}
	
	public LeilaoBuilder naData(LocalDate data) {
		this.leilao.setData(data);
		return this;
	}
	
	public Leilao build() {
		return this.leilao;
	}
	
}