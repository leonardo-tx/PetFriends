package br.com.petfriends.almoxarifado.core.exception;

public final class ItemQuantidadeReservadaInvalidaException extends CoreException {
    public ItemQuantidadeReservadaInvalidaException() {
        super("item.quantidade.reservada.invalida", "A quantidade reservada do item não pode ser maior que a disponível.");
    }
}
