package br.com.rogrs.agamotto.domain.enumeration;

public enum TipoOperacao {
	INCLUSAO("I"),
	ALTERACAO("A"),
	EXCLUSAO("E"),
	MARCACAO_PONTO("3"),
	CNPJ("1"),
	CPF("2"),
    UNKNOWN("");

    private String registro;

    TipoOperacao(String registro) {
        this.registro = registro;
    }

    public String registro() {
        return registro;
    }

    public static  TipoOperacao getTipoOperacao(String registro) {
        for ( TipoOperacao e : values()) {
            if (e.registro.equals(registro)) {
                return e;
            }
        }
        return null;
    }
}
