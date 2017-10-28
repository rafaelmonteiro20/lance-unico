package com.lanceunico.dao;

import java.util.List;

import com.lanceunico.model.Leilao;

public interface LeilaoDAO {

	List<Leilao> correntes();
	
	List<Leilao> encerrados();
	
	void atualizar(Leilao leilao);
	
}