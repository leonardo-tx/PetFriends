package br.com.petfriends.transporte.core.exception;

public final class EntregaNaoInicializavelException extends CoreException {
    public EntregaNaoInicializavelException() {
        super("entrega.nao.inicializavel", "Não é possível inicializar uma entrega já inicializada.");
    }
}
