package br.com.evoluo.citros.bpm.commons;

import br.com.evoluo.citros.bpm.CitrosBpmException;

public class MailException extends CitrosBpmException {

	private static final long serialVersionUID = 9134005142386497467L;

	public MailException(String message, Throwable cause) {
		super(message, cause);
	}

}
