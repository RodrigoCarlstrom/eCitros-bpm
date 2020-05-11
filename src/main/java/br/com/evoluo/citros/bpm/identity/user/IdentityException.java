package br.com.evoluo.citros.bpm.identity.user;

import br.com.evoluo.citros.bpm.CitrosBpmException;

public class IdentityException extends CitrosBpmException {

	private static final long serialVersionUID = -6794626303380947029L;

	public IdentityException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdentityException(String message) {
		super(message);
	}

}
