package com.lanceunico.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeilaoTest {

	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Geladeira");
		
		assertEquals(0, leilao.quantidadeLances());
		
		leilao.propoe(new Lance(new Usuario("Rafael"), 1500.0));
		
		assertEquals(1, leilao.quantidadeLances());
	}
	
	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new Leilao("Geladeira");
		
		assertEquals(0, leilao.quantidadeLances());
		
		leilao.propoe(new Lance(new Usuario("Rafael"), 1500.0));
		leilao.propoe(new Lance(new Usuario("Guilherme"), 1200.0));
		leilao.propoe(new Lance(new Usuario("Paulo"), 1800.0));
		
		assertEquals(3, leilao.quantidadeLances());
	}
	
	
	
}