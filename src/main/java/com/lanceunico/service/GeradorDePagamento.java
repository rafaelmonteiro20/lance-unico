package com.lanceunico.service;

import java.time.DayOfWeek;
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
	private final Relogio relogio;
	
	public GeradorDePagamento(LeilaoDAO leilaoDAO, PagamentoDAO pagamentoDAO, Avaliador avaliador) {
		this(leilaoDAO, pagamentoDAO, avaliador, new RelogioSistema());
	}
	
	public GeradorDePagamento(LeilaoDAO leilaoDAO, PagamentoDAO pagamentoDAO, Avaliador avaliador, Relogio relogio) {
		this.leilaoDAO = leilaoDAO;
		this.pagamentoDAO = pagamentoDAO;
		this.avaliador = avaliador;
		this.relogio = relogio;
	}

	public void gerar() {
		List<Leilao> encerrados = leilaoDAO.encerrados();
		
		for (Leilao leilao : encerrados) {
			avaliador.avaliar(leilao);
			
			Pagamento pagamento = new Pagamento(proximoDiaUtil(), avaliador.getMaiorLance());
			pagamentoDAO.salvar(pagamento);
		}
	}

	private LocalDate proximoDiaUtil() {
		LocalDate hoje = relogio.hoje();
		
		if(hoje.getDayOfWeek() == DayOfWeek.SATURDAY)
			return hoje.plusDays(2);
		
		if(hoje.getDayOfWeek() == DayOfWeek.SUNDAY)
			return hoje.plusDays(1);
		
		return hoje;
	}
}
