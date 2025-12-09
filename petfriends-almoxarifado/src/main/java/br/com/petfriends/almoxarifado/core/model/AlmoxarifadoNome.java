package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNomeNuloException;
import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNomeTamanhoInvalidoException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record AlmoxarifadoNome(@JsonValue String valor) {
    public static final int TAMANHO_MINIMO = 4;
    public static final int TAMANHO_MAXIMO = 50;

    @JsonCreator
    public AlmoxarifadoNome {
        if (valor == null) {
            throw new AlmoxarifadoNomeNuloException();
        }
        if (valor.length() < TAMANHO_MINIMO || valor.length() > TAMANHO_MAXIMO) {
            throw new AlmoxarifadoNomeTamanhoInvalidoException();
        }
    }
}
