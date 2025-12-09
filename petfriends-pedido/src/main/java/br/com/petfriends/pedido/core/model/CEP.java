package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.CEPNuloException;
import br.com.petfriends.pedido.core.exception.CEPTamanhoInvalidoException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record CEP(@JsonValue String valor) {
    public static final int TAMANHO = 8;

    @JsonCreator
    public CEP {
        if (valor == null) {
            throw new CEPNuloException();
        }
        String normalized = valor.replaceAll("\\D", "");
        if (normalized.length() != TAMANHO) {
            throw new CEPTamanhoInvalidoException();
        }
        valor = normalized;
    }
}
