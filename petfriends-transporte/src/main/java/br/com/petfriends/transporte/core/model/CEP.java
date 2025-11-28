package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.exception.CEPNuloException;
import br.com.petfriends.transporte.core.exception.CEPTamanhoInvalidoException;

public final class CEP {
    public static final int TAMANHO = 8;

    private final String valor;

    private CEP(String valor) {
        if (valor == null) {
            throw new CEPNuloException();
        }
        String normalized = valor.replaceAll("\\D", "");
        if (normalized.length() != TAMANHO) {
            throw new CEPTamanhoInvalidoException();
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static CEP valueOf(String valor) {
        return new CEP(valor);
    }
}
