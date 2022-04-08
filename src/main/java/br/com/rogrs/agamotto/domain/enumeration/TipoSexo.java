package br.com.rogrs.agamotto.domain.enumeration;

/**
 * The TipoSexo enumeration.
 */
public enum TipoSexo {    
    
    
    FEMININO("Feminino"),
    MASCULINO("Masculino");

    private final String displayName;

    TipoSexo(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
