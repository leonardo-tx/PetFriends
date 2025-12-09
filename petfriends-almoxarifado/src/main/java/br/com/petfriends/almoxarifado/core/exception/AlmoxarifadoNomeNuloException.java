package br.com.petfriends.almoxarifado.core.exception;

public final class AlmoxarifadoNomeNuloException extends CoreException {
    public AlmoxarifadoNomeNuloException() {
        super("almoxarifado.nome.nulo", "O nome do almoxarifado não pode ser nulo.");
    }
}
