package br.com.petfriends.almoxarifado.core.exception;

public final class ItemNaoEncontradoException extends NotFoundException {
    public ItemNaoEncontradoException() {
        super("item.nao.encontrado", "Não foi possível encontrar o item especificado.");
    }
}
