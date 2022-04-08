package br.com.rogrs.agamotto.domain.enumeration;

public enum TipoAjustes {

	ABONO("Abono"), FALTA_COM_DESCONTO("Falta com Desconto"), AJUSTE_HORARIO("Ajuste no Horário"),
	DESCANSO_SEMANAL_REMUNERADO("Descanso Semanal Remunerado");

	private final String displayName;

	TipoAjustes(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
