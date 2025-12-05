package br.com.petfriends.almoxarifado.core.exception;

public final class ItemQuantidadeNuloException extends CoreException {
    public ItemQuantidadeNuloException() {
        super("item.quantidade.nulo", "A quantidade do item não pode ser nula.");
    }
}
