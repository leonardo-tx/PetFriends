package br.com.petfriends.transporte.core.exception;

public final class AlmoxarifadoIdentificadorNuloException extends CoreException {
    public AlmoxarifadoIdentificadorNuloException() {
        super("almoxarifado.id.nulo", "O identificador do almoxarifado não pode ser nulo.");
    }
}
