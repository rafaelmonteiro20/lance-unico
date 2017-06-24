package com.lanceunico.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.lanceunico.builder.LeilaoBuilder;
import com.lanceunico.dao.LeilaoDAO;
import com.lanceunico.model.Leilao;

public class EncerradorDeLeilaoTest {

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
		
		LeilaoDAO dao = mock(LeilaoDAO.class);
		when(dao.correntes()).thenReturn(leiloesAntigos);

		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
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
		
		LeilaoDAO dao = mock(LeilaoDAO.class);
		when(dao.correntes()).thenReturn(leiloes);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerrar();
		
		assertTrue(leilao1.isEncerrado());
		assertFalse(leilao2.isEncerrado());
		assertEquals(1, encerrador.getTotal());
	}
	
	@Test
	public void naoDeveEncerrarLeiloesCasoNaoHajaNenhum() {
		List<Leilao> leiloes = Arrays.asList();
		
		LeilaoDAO dao = mock(LeilaoDAO.class);
		when(dao.correntes()).thenReturn(leiloes);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
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
		
		LeilaoDAO dao = mock(LeilaoDAO.class);
		when(dao.correntes()).thenReturn(leiloesAntigos);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerrar();
		
		verify(dao, times(1)).atualizar(leilao);
	}
	
}