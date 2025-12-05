package br.com.petfriends.almoxarifado.core.exception;

import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoNome;

public final class AlmoxarifadoNomeTamanhoInvalidoException extends CoreException {
    private static final String MESSAGE = String.format(
            "O nome do almoxarifado deve ter entre %d a %d caracteres.",
            AlmoxarifadoNome.TAMANHO_MINIMO,
            AlmoxarifadoNome.TAMANHO_MAXIMO
    );

    public AlmoxarifadoNomeTamanhoInvalidoException() {
        super("almoxarifado.nome.tamanho.invalido", MESSAGE);
    }
}
