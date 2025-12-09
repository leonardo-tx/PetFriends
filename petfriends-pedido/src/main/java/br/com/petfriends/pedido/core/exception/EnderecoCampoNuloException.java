package br.com.petfriends.pedido.core.exception;

public final class EnderecoCampoNuloException extends CoreException {
    public EnderecoCampoNuloException(String fieldName) {
        super(getCode(fieldName), getMessage(fieldName));
    }

    private static String getCode(String fieldName) {
        return String.format("endereco.%s.nulo", fieldName);
    }

    private static String getMessage(String fieldName) {
        return String.format("O(a) %s não pode ser nulo.", fieldName);
    }
}
