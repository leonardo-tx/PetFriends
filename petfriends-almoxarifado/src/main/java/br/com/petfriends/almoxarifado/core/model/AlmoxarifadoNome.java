package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNomeNuloException;
import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNomeTamanhoInvalidoException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public final class AlmoxarifadoNome {
    public static final int TAMANHO_MINIMO = 4;
    public static final int TAMANHO_MAXIMO = 50;

    private final String valor;

    private AlmoxarifadoNome(String valor) {
        if (valor == null) {
            throw new AlmoxarifadoNomeNuloException();
        }
        if (valor.length() < TAMANHO_MINIMO || valor.length() > TAMANHO_MAXIMO) {
            throw new AlmoxarifadoNomeTamanhoInvalidoException();
        }
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

    @JsonCreator
    public static AlmoxarifadoNome valueOf(String valor) {
        return new AlmoxarifadoNome(valor);
    }
}
