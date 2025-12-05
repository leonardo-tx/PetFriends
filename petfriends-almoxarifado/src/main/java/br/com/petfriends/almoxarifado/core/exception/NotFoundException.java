package br.com.petfriends.almoxarifado.core.exception;

public abstract class NotFoundException extends CoreException {
    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
