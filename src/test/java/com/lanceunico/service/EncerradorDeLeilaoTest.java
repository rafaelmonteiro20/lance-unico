package com.lanceunico.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lanceunico.builder.LeilaoBuilder;
import com.lanceunico.dao.LeilaoDAO;
import com.lanceunico.model.Leilao;
import com.lanceunico.notification.Notificador;

public class EncerradorDeLeilaoTest {

	private EncerradorDeLeilao encerrador;
	
	@Mock
	private LeilaoDAO dao;
	
	@Mock
	private Notificador notificador;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.encerrador = new EncerradorDeLeilao(dao, notificador);
	}
	
	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {
		LocalDate data = LocalDate.now().minusDays(10);
		
		Leilao leilao1 = new LeilaoBuilder().para("Geladeira")
							.naData(data)
							.build();
		
		Leilao leilao2 = new LeilaoBuilder().para("Fogão")
							.naData(data)
							.build();
		
		List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);
		
		when(dao.correntes()).thenReturn(leiloesAntigos);
		encerrador.encerrar();
		
		assertTrue(leilao1.isEncerrado());
		assertTrue(leilao2.isEncerrado());
		assertEquals(2, encerrador.getTotal());
	}
	
	@Test
	public void naoDeveEncerrarLeiloesQueComecaramHaMenosDeUmaSemana() {
		LocalDate data1 = LocalDate.now().minusDays(10);
		LocalDate data2 = LocalDate.now().minusDays(5);
		
		
		Leilao leilao1 = new LeilaoBuilder().para("Geladeira")
							.naData(data1)
							.build();
		
		Leilao leilao2 = new LeilaoBuilder().para("Fogão")
							.naData(data2)
							.build();
		
		List<Leilao> leiloes = Arrays.asList(leilao1, leilao2);
		
		when(dao.correntes()).thenReturn(leiloes);
		encerrador.encerrar();
		
		assertTrue(leilao1.isEncerrado());
		assertFalse(leilao2.isEncerrado());
		assertEquals(1, encerrador.getTotal());
	}
	
	@Test
	public void naoDeveEncerrarLeiloesCasoNaoHajaNenhum() {
		List<Leilao> leiloes = Arrays.asList();
		
		when(dao.correntes()).thenReturn(leiloes);
		encerrador.encerrar();
		
		assertEquals(0, encerrador.getTotal());
	}
	
	@Test
	public void deveAtualizarLeiloesEncerrados() {
		LocalDate data = LocalDate.now().minusDays(10);
		
		Leilao leilao = new LeilaoBuilder().para("Geladeira")
						    .naData(data)
							.build();
		
		List<Leilao> leiloesAntigos = Arrays.asList(leilao);
		
		when(dao.correntes()).thenReturn(leiloesAntigos);
		encerrador.encerrar();
		
		verify(dao, times(1)).atualizar(leilao);
	}
	
	@Test
	public void deveContinuarExecucaoMesmoQuandoDaoFalhar() {
		LocalDate data = LocalDate.now().minusDays(10);
		
		Leilao leilao1 = new LeilaoBuilder().para("Geladeira")
				.naData(data)
				.build();

		Leilao leilao2 = new LeilaoBuilder().para("Fogão")
				.naData(data)
				.build();
		
		List<Leilao> leiloes = Arrays.asList(leilao1, leilao2);
		
		when(dao.correntes()).thenReturn(leiloes);
		doThrow(new RuntimeException()).when(dao).atualizar(leilao1);
		
		encerrador.encerrar();
		
		verify(dao).atualizar(leilao2);
		verify(notificador).notificar(leilao2);
	}
	
}