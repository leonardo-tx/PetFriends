package br.com.petfriends.transporte.core.exception;

public final class ItemRemessaQuantidadeNuloException extends CoreException {
    public ItemRemessaQuantidadeNuloException() {
        super("item.remessa.quantidade.nulo", "A quantidade do item remessa não pode ser nulo.");
    }
}
