package com.stefanini.application.domain.enums;

public enum Cargo {

	GERENTE("Gerente"), 
	BP("Business Partner"), 
	MENTOR("Mentor"), 
	DESENVOLVEDOR_SENIOR("Desenvolvedor Senior"),
	DESENVOLVEDOR_PLENO("Desenvolvedor Pleno"), 
	DESENVOLVEDOR_JUNIOR("Desenvolvedor Junior");

	private String descricao;

	private Cargo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Cargo fromDescricao(String descricao) {
		Cargo aux = null;
		for (Cargo it : Cargo.values()) {
			if (it.getDescricao().equalsIgnoreCase(descricao)) {
				aux = it;
			}
		}
		return aux;
	}

	public static Cargo proximo(Integer nivel, Cargo cargo) {
		if (nivel > 2 && cargo.equals(Cargo.DESENVOLVEDOR_JUNIOR)) {
			return Cargo.DESENVOLVEDOR_PLENO;
		} else if (nivel > 3 && cargo.equals(Cargo.DESENVOLVEDOR_PLENO)) {
			return Cargo.DESENVOLVEDOR_SENIOR;
		}
		return cargo;
	}
}
