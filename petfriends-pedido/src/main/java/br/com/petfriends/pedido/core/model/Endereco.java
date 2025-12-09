package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.EnderecoCampoNuloException;
import br.com.petfriends.pedido.core.exception.EnderecoCampoTamanhoInvalidoException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public record Endereco(
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        CEP cep
) {

    public static final int TAMANHO_MINIMO = 1;
    public static final int TAMANHO_MAXIMO = 255;

    @JsonCreator
    public Endereco {
        validarCampo("rua", rua);
        validarCampo("numero", numero);

        if (complemento != null) {
            validarCampo("complemento", complemento);
        }

        validarCampo("bairro", bairro);
        validarCampo("cidade", cidade);
        validarCampo("estado", estado);

        Objects.requireNonNull(cep);
    }

    private static void validarCampo(String fieldName, String value) {
        if (value == null) {
            throw new EnderecoCampoNuloException(fieldName);
        }
        int tamanho = value.length();
        if (tamanho < TAMANHO_MINIMO || tamanho > TAMANHO_MAXIMO) {
            throw new EnderecoCampoTamanhoInvalidoException(fieldName);
        }
    }
}
