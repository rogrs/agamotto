package br.com.rogrs.agamotto.domain.enumeration;

/**
 * The TipoBoolean enumeration.
 */
public enum TipoBoolean {

	SIM("Sim"), NAO("Não");

	private final String displayName;

	TipoBoolean(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
