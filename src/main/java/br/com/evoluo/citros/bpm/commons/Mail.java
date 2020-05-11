package br.com.evoluo.citros.bpm.commons;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.identity.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Mail {

	private final static Logger LOGGER = Logger.getLogger(Mail.class.getName());

	private String from;
	private User to;
	private String subject;
	private String title;
	private String message;
	private String instructions;
	private List<MailAction> actions = new LinkedList<>();

	private Properties properties;

	public Mail(User to, String subject, String title, String message) throws MailException {
		super();
		try {
			this.properties = Utils.getProperties();
		} catch (IOException e) {
			throw new MailException("Erro ao carregar o arquivo de properties!", e);
		}
		this.from = properties.getProperty("mail.username");
		this.to = to;
		this.subject = subject;
		this.title = title;
		this.message = message;
	}

	public Mail(User to, String subject, String title, String message, String instructions, List<MailAction> actions)
			throws MailException {
		this(to, subject, title, message);
		this.instructions = instructions;
		this.actions = actions;
	}

	public Mail(User to, String subject, String title, String message, String instructions, MailAction action)
			throws MailException {
		this(to, subject, title, message);
		this.instructions = instructions;
		this.actions.add(action);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public List<MailAction> getActions() {
		return actions;
	}

	public void setActions(List<MailAction> actions) {
		this.actions = actions;
	}

	public void send() throws AddressException, MailException {

		LOGGER.log(Level.INFO, "Mail.send(" + message + ") - start");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.username"),
						properties.getProperty("mail.password"));
			}
		});
		session.setDebug(true);

		Message mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(this.from));
			Address[] toUser = InternetAddress.parse(to.getEmail());
			mimeMessage.setRecipients(Message.RecipientType.TO, toUser);
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(processTemplate(), "text/html");

			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			throw new MailException("Erro ao configurar mensagem!", e);
		}

		LOGGER.log(Level.INFO, "Mail.send() - end");
	}

	private String processTemplate() throws MailException {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/template/");
		Template tamplete;
		try {
			tamplete = configuration.getTemplate("email-template.ftl");
		} catch (IOException e) {
			throw new MailException("Erro ao carregar o template de e-mail!", e);
		}
		Writer out = new StringWriter();
		try {
			tamplete.process(this, out);
		} catch (TemplateException | IOException e) {
			throw new MailException("Erro ao processar o template de e-mail!", e);
		}
		return out.toString();
	}

}
