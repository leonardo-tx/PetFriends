package br.com.petfriends.almoxarifado.core.exception;

public final class ItemIdentificadorNuloException extends CoreException {
    public ItemIdentificadorNuloException() {
        super("item.id.nulo", "O identificador do item não pode ser nulo.");
    }
}
