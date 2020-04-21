package br.com.evoluo.citros.bpm.identity.user;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;

public class EnviarEmailDeConfirmacaoTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		User user = (User) execution.getVariable("user");
	}

}
