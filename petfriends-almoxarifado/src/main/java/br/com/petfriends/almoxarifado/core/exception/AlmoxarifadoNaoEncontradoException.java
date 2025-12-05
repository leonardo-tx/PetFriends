package br.com.petfriends.almoxarifado.core.exception;

public class AlmoxarifadoNaoEncontradoException extends NotFoundException {
    public AlmoxarifadoNaoEncontradoException() {
        super("almoxarifado.nao.encontrado", "Não foi possível encontrar o almoxarifado.");
    }
}
