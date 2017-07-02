package com.lanceunico.service;

import java.time.LocalDate;
import java.util.List;

import com.lanceunico.dao.LeilaoDAO;
import com.lanceunico.model.Leilao;
import com.lanceunico.notification.Notificador;

public class EncerradorDeLeilao {

	private int total;
	
	private LeilaoDAO dao;
	
	private Notificador notificador;
	
	public EncerradorDeLeilao(LeilaoDAO dao, Notificador notificador) {
		this.dao = dao;
		this.notificador = notificador;
	}
	
	public void encerrar() {
		List<Leilao> leiloesCorrentes = dao.correntes();
		
		for (Leilao leilao : leiloesCorrentes) {
			try {
				if(comecouSemanaPassada(leilao)) {
					leilao.encerrar();
					this.total++;
	
					this.dao.atualizar(leilao);
					this.notificador.notificar(leilao);
				} 
			} catch (Exception e) {
				// Salvar no log
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