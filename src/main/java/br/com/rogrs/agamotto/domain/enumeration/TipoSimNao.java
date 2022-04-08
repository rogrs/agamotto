package br.com.rogrs.agamotto.domain.enumeration;

public enum TipoSimNao {

	SIM("Sim"), NAO("Não");

	private final String displayName;

	TipoSimNao(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
