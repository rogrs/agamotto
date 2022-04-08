package br.com.rogrs.agamotto.domain.enumeration;

/**
 * The StatusArquivo enumeration.
 */
public enum StatusSistema {

	CRIADO("Criado"), ANDAMENTO("Andamento"), ERRO("Erro"), PROCESSADO("Processado"), NAO_PROCESSADO("Não Processado"), CONCLUIDO("Concluido");

	private final String displayName;

	StatusSistema(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
