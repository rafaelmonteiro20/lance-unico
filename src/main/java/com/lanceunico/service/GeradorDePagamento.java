package com.lanceunico.service;

import java.time.LocalDate;
import java.util.List;

import com.lanceunico.dao.LeilaoDAO;
import com.lanceunico.dao.PagamentoDAO;
import com.lanceunico.model.Leilao;
import com.lanceunico.model.Pagamento;

public class GeradorDePagamento {

	private final PagamentoDAO pagamentoDAO;
	private final LeilaoDAO leilaoDAO;
	private final Avaliador avaliador;
	
	public GeradorDePagamento(LeilaoDAO leilaoDAO, PagamentoDAO pagamentoDAO, Avaliador avaliador) {
		this.leilaoDAO = leilaoDAO;
		this.pagamentoDAO = pagamentoDAO;
		this.avaliador = avaliador;
	}

	public void gerar() {
		List<Leilao> encerrados = leilaoDAO.encerrados();
		
		for (Leilao leilao : encerrados) {
			avaliador.avaliar(leilao);
			
			Pagamento pagamento = new Pagamento(LocalDate.now(), avaliador.getMaiorLance());
			pagamentoDAO.salvar(pagamento);
		}
	}
}
