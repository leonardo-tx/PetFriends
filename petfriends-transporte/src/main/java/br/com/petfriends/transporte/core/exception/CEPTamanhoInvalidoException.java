package br.com.petfriends.transporte.core.exception;

import br.com.petfriends.transporte.core.model.CEP;

public final class CEPTamanhoInvalidoException extends CoreException {
    private static final String MESSAGE = String.format(
            "O CEP deve ter exatamente %d dígitos.",
            CEP.TAMANHO
    );

    public CEPTamanhoInvalidoException() {
        super("cep.tamanho.invalido", MESSAGE);
    }
}
