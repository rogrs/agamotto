package br.com.rogrs.agamotto.domain.enumeration;

/**
 * The TipoMotivoAjuste enumeration.
 */
public enum TipoMotivoAjuste {

	AJUSTE("Ajuste"), FALTA("Falta"), CONTROLE_PONTO("Controle_Ponto");

	private final String displayName;

	TipoMotivoAjuste(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
