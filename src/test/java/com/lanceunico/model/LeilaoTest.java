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
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new Leilao("Geladeira");
		
		Usuario usuario = new Usuario("Rafael");
		
		leilao.propoe(new Lance(usuario, 1200.0));
		leilao.propoe(new Lance(usuario, 1350.0));
		
		Lance ultimoLance = leilao.ultimoLanceDado();
		
		assertEquals(1, leilao.quantidadeLances());
		assertEquals(1200.0, ultimoLance.getValor(), 0.0001);
	}
	
	@Test
	public void naoDeveAceitarMaisQueCincoLancesDoMesmoUsuario() {
		Leilao leilao = new Leilao("TV 42'");
		
		Usuario u1 = new Usuario("Rafael");
		Usuario u2 = new Usuario("Maurício");
		
		leilao.propoe(new Lance(u1, 1000.0));
		leilao.propoe(new Lance(u2, 1100.0));
		leilao.propoe(new Lance(u1, 1200.0));
		leilao.propoe(new Lance(u2, 1300.0));
		leilao.propoe(new Lance(u1, 1400.0));
		leilao.propoe(new Lance(u2, 1500.0));
		leilao.propoe(new Lance(u1, 1600.0));
		leilao.propoe(new Lance(u2, 1700.0));
		leilao.propoe(new Lance(u1, 1800.0));
		leilao.propoe(new Lance(u2, 1900.0));
		leilao.propoe(new Lance(u1, 2000.0)); //Deve ser ignorado
		
		Lance ultimoLance = leilao.ultimoLanceDado();
		
		assertEquals(10, leilao.quantidadeLances());
		assertEquals(1900.0, ultimoLance.getValor(), 0.0001);
	}
	
}