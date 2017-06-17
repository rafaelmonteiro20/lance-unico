package com.lanceunico.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.lanceunico.builder.LeilaoBuilder;
import com.lanceunico.model.Leilao;
import com.lanceunico.model.Usuario;

public class AvaliadorTest {
	
	private Avaliador avaliador;
	
	private Usuario u1;
	
	private Usuario u2;
	
	private Usuario u3; 
	
	@Before
	public void init() {
		avaliador = new Avaliador();
		u1 = new Usuario("Maurício");
		u2 = new Usuario("Guilherme");
		u3 = new Usuario("Rafael");
	}
	

	@Test
	public void deveEntederLeilaoComApenasUmLance() {
		Leilao leilao = new LeilaoBuilder()
						.para("Notebook i7")
						.comLance(u1, 1000.0)
						.build();
		
		avaliador.avaliar(leilao);
		
		assertEquals(1000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(1000.0, avaliador.getMaiorLance(), 0.0001);
	}
	
	
	@Test
	public void deveEntederLancesEmOrderCrescente() {
		Leilao leilao = new LeilaoBuilder()
						.para("Notebook i7")
						.comLance(u1, 2000.0)
						.comLance(u2, 2500.0)
						.comLance(u3, 2800.0)
						.build();
											
		avaliador.avaliar(leilao);
		
		assertEquals(2000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(2800.0, avaliador.getMaiorLance(), 0.0001);
	}
	
	@Test
	public void deveEntederLancesEmOrderDecrescente() {
		Leilao leilao = new LeilaoBuilder()
						.para("Notebook i7")
						.comLance(u1, 3000.0)
						.comLance(u2, 2500.0)
						.comLance(u3, 2000.0)
						.build();
		
		avaliador.avaliar(leilao);
		
		assertEquals(2000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(3000.0, avaliador.getMaiorLance(), 0.0001);
	}
	
	@Test
	public void deveEntederLancesEmOrderAleatoria() {
		Leilao leilao = new LeilaoBuilder()
						.para("Notebook i7")
						.comLance(u1, 3000.0)
						.comLance(u2, 3200.0)
						.comLance(u3, 2000.0)
						.comLance(new Usuario("Paulo"), 2500.0)
						.build();
		
		avaliador.avaliar(leilao);
		
		assertEquals(2000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(3200.0, avaliador.getMaiorLance(), 0.0001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAvaliarLeilaoSemLances() {
		Leilao leilao = new LeilaoBuilder()
				.para("Geladeira")
				.build();
		
		avaliador.avaliar(leilao);
	}
	
}