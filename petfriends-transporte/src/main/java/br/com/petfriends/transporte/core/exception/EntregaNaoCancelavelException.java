package br.com.petfriends.transporte.core.exception;

public final class EntregaNaoCancelavelException extends CoreException {
    public EntregaNaoCancelavelException() {
        super("entrega.nao.cancelavel", "Não é possível cancelar uma entrega que não está em processamento.");
    }
}
