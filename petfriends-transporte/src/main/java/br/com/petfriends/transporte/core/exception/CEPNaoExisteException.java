package br.com.petfriends.transporte.core.exception;

public final class CEPNaoExisteException extends CoreException {
    public CEPNaoExisteException() {
        super("cep.nao.existe", "O CEP não existe no Brasil.");
    }
}
