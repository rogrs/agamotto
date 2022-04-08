package br.com.rogrs.agamotto.domain.enumeration;

public enum TipoMarcacao {
	DESCONSIDERADO("D"),
	SAIDA("S"),
	ENTRADA("E"),
	UNKNOWN("");

    private String registro;

    TipoMarcacao(String registro) {
        this.registro = registro;
    }

    public String registro() {
        return registro;
    }
   
}