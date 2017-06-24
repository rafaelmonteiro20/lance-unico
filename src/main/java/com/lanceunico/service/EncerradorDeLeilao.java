package com.lanceunico.service;

import java.time.LocalDate;
import java.util.List;

import com.lanceunico.dao.LeilaoDAO;
import com.lanceunico.model.Leilao;

public class EncerradorDeLeilao {

	private int total;
	
	private LeilaoDAO dao;
	
	public EncerradorDeLeilao(LeilaoDAO dao) {
		this.dao = dao;
	}
	
	public void encerrar() {
		List<Leilao> leiloesCorrentes = dao.correntes();
		
		for (Leilao leilao : leiloesCorrentes) {
			if(comecouSemanaPassada(leilao)) {
				leilao.encerra();
				this.dao.atualizar(leilao);
				this.total++;
			}
		}
	}
	
	private boolean comecouSemanaPassada(Leilao leilao) {
		LocalDate semanaPassada = LocalDate.now().minusWeeks(1);
		return leilao.getData().isBefore(semanaPassada);
	}

	public int getTotal() {
		return total;
	}

}