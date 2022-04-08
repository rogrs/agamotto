package br.com.rogrs.agamotto.domain.enumeration;


public enum TipoRegistro {
	CABECALHO("1"),
	INCLUSAO_ALTERACAO("2"),
	MARCACAO_PONTO("3"),
	AJUSTE_TEMPO_REAL("4"),
	INCLUSAO_ALTERACAO_EXCLUSAO("5"),
  //  TODOS("0"),
    AFDT_DETALHE("2"),
   // AFDT_ORIGINAL("O"),
    //AFDT_INCLUIDO("I"),
    //ADFT_PREASSINALADO("P"),
    TRAILER("9"),
    ANONYMOUS_USER_ROLE("-");
    


    private String registro;

    TipoRegistro(String registro) {
        this.registro = registro;
    }

    public String registro() {
        return registro;
    }

    public static TipoRegistro getTipoRegistro(String registro) {
        for (TipoRegistro e : values()) {
            if (e.registro.equals(registro)) {
                return e;
            }
        }
        return null;
    }
}
