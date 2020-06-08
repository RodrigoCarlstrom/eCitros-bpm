package br.com.evoluo.citros.bpm.identity.user;

import javax.mail.internet.AddressException;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.spin.json.SpinJsonNode;

import br.com.evoluo.citros.bpm.commons.Mail;
import br.com.evoluo.citros.bpm.commons.MailAction;
import br.com.evoluo.citros.bpm.commons.MailException;

public class EnviarEmailDeConfirmacaoTask implements JavaDelegate {

	private User user;

	@Override
	public void execute(DelegateExecution execution) throws IdentityException {

		SpinJsonNode jsonUser = (SpinJsonNode) execution.getVariable("user");

		// Validate
		IdentityService idendityService = execution.getProcessEngine().getIdentityService();
		String id = jsonUser.prop("profile").prop("id").stringValue();
		user = idendityService.createUserQuery().userId(id).singleResult();
		if (user != null) {
			throw new IdentityException("CPF " + id + " já cadastrado!");
		}
		String email = jsonUser.prop("profile").prop("email").stringValue();
		user = idendityService.createUserQuery().userEmail(email).singleResult();
		if (user != null) {
			throw new IdentityException("Email " + email + " já cadastrado!");
		}

		String firstName = jsonUser.prop("profile").prop("firstName").stringValue();
		String lastName = jsonUser.prop("profile").prop("lastName").stringValue();
		String password = generatePassword();

		user = idendityService.newUser(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		idendityService.saveUser(user);

		String subject = "Usuário criado com sucesso";
		String title = "Olá " + user.getFirstName() + "!";
		StringBuffer message = new StringBuffer("Seu usuário foi criado com sucesso!").append(System.lineSeparator());
		message.append("Segue sua senha: ").append(password).append(System.lineSeparator());
		String instruction = "NOTA: Você terá 7 dias para alterá-la!";
		Mail mail;
		try {
			MailAction action = new MailAction();
			action.setAction("/login");
			action.setLabel("Entre aqui!");
			action.setHtmlClass("btn btn-primary");
			mail = new Mail(user, subject, title, message.toString(), instruction, action );
			mail.send();
		} catch (MailException | AddressException e) {
			idendityService.deleteUser(user.getId());
			throw new IdentityException("Erro ao criar usuário!", e);
		}

	}

	private String generatePassword() {
		StringBuffer password = new StringBuffer();
		String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < 6; i++) {
			password.append(possible.charAt((int) Math.floor(Math.random() * possible.length())));
		}
		return password.toString();
	}

}
