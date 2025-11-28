package br.com.petfriends.transporte.core.exception;

import br.com.petfriends.transporte.core.model.Endereco;

public final class EnderecoCampoTamanhoInvalidoException extends CoreException {
    public EnderecoCampoTamanhoInvalidoException(String fieldName) {
        super(getCode(fieldName), getMessage(fieldName));
    }

    private static String getCode(String fieldName) {
        return String.format("endereco.%s.tamanho.invalido", fieldName);
    }

    private static String getMessage(String fieldName) {
        return String.format(
                "O(a) %s deve possuir um tamanho entre %d a %d caracteres",
                fieldName,
                Endereco.TAMANHO_MINIMO,
                Endereco.TAMANHO_MAXIMO
        );
    }
}
