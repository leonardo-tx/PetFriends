package br.com.petfriends.transporte.core.exception;

import br.com.petfriends.transporte.core.model.ItemRemessa;

public final class ItemRemessaQuantidadeInvalidaException extends CoreException {
    private static final String MESSAGE = String.format(
            "A quantidade do item de uma remessa não pode ser menor que %d.",
            ItemRemessa.QUANTIDADE_MINIMA
    );

    public ItemRemessaQuantidadeInvalidaException() {
        super("item.remessa.quantidade.invalida", MESSAGE);
    }
}
