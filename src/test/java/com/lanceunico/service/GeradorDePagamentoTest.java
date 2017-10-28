package com.lanceunico.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lanceunico.builder.LeilaoBuilder;
import com.lanceunico.dao.LeilaoDAO;
import com.lanceunico.dao.PagamentoDAO;
import com.lanceunico.model.Leilao;
import com.lanceunico.model.Pagamento;
import com.lanceunico.model.Usuario;

public class GeradorDePagamentoTest {

	@Mock
	private LeilaoDAO leilaoDAO;
	
	@Mock
	private PagamentoDAO pagamentoDAO;
	
	@Mock
	private Avaliador avaliador;
	
	@Mock
	private Relogio relogio;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deveGerarPagamentoParaUmLeilaoEncerrado() {
		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 4")
				.comLance(new Usuario("Rafael"), 2000.0)
				.comLance(new Usuario("Maria"), 2500.0)
				.build();
		
		when(leilaoDAO.encerrados()).thenReturn(Arrays.asList(leilao));
		when(avaliador.getMaiorLance()).thenReturn(2500.0);
		
		GeradorDePagamento gerador = new GeradorDePagamento(leilaoDAO, pagamentoDAO, avaliador);
		gerador.gerar();
		
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentoDAO).salvar(argumento.capture());
		
		Pagamento pagamentoGerado = argumento.getValue();
		assertEquals(2500.0, pagamentoGerado.getValor(), 0.0001);
	}
	
	@Test
	public void deveEnpurrarDataDePagamentoParaProximoDiaUtil() {
		Leilao leilao = new LeilaoBuilder()
				.para("Playstation 4")
				.comLance(new Usuario("Rafael"), 2000.0)
				.comLance(new Usuario("Maria"), 2500.0)
				.build();
		
		// Dia 28/10/2017 é um sábado
		LocalDate sabado = LocalDate.of(2017, Month.OCTOBER, 28);
		
		when(leilaoDAO.encerrados()).thenReturn(Arrays.asList(leilao));
		when(relogio.hoje()).thenReturn(sabado);
		
		GeradorDePagamento gerador = new GeradorDePagamento(leilaoDAO, pagamentoDAO, avaliador, relogio);
		gerador.gerar();
		
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentoDAO).salvar(argumento.capture());
		
		Pagamento pagamentoGerado = argumento.getValue();
		assertEquals(pagamentoGerado.getData().getDayOfWeek(), DayOfWeek.MONDAY);
	}
	
}
