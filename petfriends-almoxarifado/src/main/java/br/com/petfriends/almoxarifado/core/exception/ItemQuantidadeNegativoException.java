package br.com.petfriends.almoxarifado.core.exception;

public final class ItemQuantidadeNegativoException extends CoreException {
    public ItemQuantidadeNegativoException() {
        super("item.quantidade.negativo", "A quantidade do item não pode ser negativa.");
    }
}
