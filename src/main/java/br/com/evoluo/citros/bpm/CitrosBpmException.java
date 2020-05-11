package br.com.evoluo.citros.bpm;

public class CitrosBpmException extends Exception {

	private static final long serialVersionUID = 5819343365028840542L;

	public CitrosBpmException(String message, Throwable cause) {
		super(message, cause);
	}

	public CitrosBpmException(String message) {
		super(message);
	}

}
