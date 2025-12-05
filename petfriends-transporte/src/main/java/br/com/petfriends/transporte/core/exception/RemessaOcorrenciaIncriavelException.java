package br.com.petfriends.transporte.core.exception;

public final class RemessaOcorrenciaIncriavelException extends CoreException {
    public RemessaOcorrenciaIncriavelException() {
        super("remessa.ocorrencia.incriavel", "Não é possível criar ocorrência em uma remessa que não está em transporte.");
    }
}
