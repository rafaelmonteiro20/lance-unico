package com.lanceunico.service;

import java.time.LocalDate;

public class RelogioSistema implements Relogio {

	@Override
	public LocalDate hoje() {
		return LocalDate.now();
	}

}
