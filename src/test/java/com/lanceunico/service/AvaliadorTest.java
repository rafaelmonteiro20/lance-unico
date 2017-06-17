package com.lanceunico.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lanceunico.model.Leilao;
import com.lanceunico.model.Usuario;

public class AvaliadorTest {

	@Test
	public void deveEntederLeilaoComApenasUmLance() {
		Usuario u1 = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Notebook i7");
		leilao.propoe(u1, 1000);
		
		Avaliador avaliador = new Avaliador();
		avaliador.avaliar(leilao);
		
		assertEquals(1000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(1000.0, avaliador.getMaiorLance(), 0.0001);
	}
	
	
	@Test
	public void deveEntederLancesEmOrderCrescente() {
		Usuario u1 = new Usuario("Maria");
		Usuario u2 = new Usuario("Guilherme");
		Usuario u3 = new Usuario("Rafael");
		
		Leilao leilao = new Leilao("Notebook i7");
		leilao.propoe(u1, 2000);
		leilao.propoe(u2, 2500);
		leilao.propoe(u3, 2800);
		
		Avaliador avaliador = new Avaliador();
		avaliador.avaliar(leilao);
		
		assertEquals(2000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(2800.0, avaliador.getMaiorLance(), 0.0001);
	}
	
	@Test
	public void deveEntederLancesEmOrderDecrescente() {
		Usuario u1 = new Usuario("Maria");
		Usuario u2 = new Usuario("Guilherme");
		Usuario u3 = new Usuario("Rafael");
		
		Leilao leilao = new Leilao("Notebook i7");
		leilao.propoe(u1, 3000);
		leilao.propoe(u2, 2500);
		leilao.propoe(u3, 2000);
		
		Avaliador avaliador = new Avaliador();
		avaliador.avaliar(leilao);
		
		assertEquals(2000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(3000.0, avaliador.getMaiorLance(), 0.0001);
	}
	
	@Test
	public void deveEntederLancesEmOrderAleatoria() {
		Usuario u1 = new Usuario("Maria");
		Usuario u2 = new Usuario("Guilherme");
		Usuario u3 = new Usuario("Rafael");
		Usuario u4 = new Usuario("Maurício");
		
		Leilao leilao = new Leilao("Notebook i7");
		leilao.propoe(u1, 3000);
		leilao.propoe(u2, 3200);
		leilao.propoe(u3, 2000);
		leilao.propoe(u4, 2500);
		
		Avaliador avaliador = new Avaliador();
		avaliador.avaliar(leilao);
		
		assertEquals(2000.0, avaliador.getMenorLance(), 0.0001);
		assertEquals(3200.0, avaliador.getMaiorLance(), 0.0001);
	}
	
}