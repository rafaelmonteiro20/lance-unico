package com.lanceunico.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lanceunico.model.Leilao;
import com.lanceunico.model.Usuario;

public class AvaliadorTest {

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
	}
	
}