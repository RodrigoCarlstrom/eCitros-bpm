package br.com.evoluo.citros.bpm.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class Utils {
	
	public static Properties getProperties() throws IOException {
		Properties properties = new Properties();
		FileInputStream file = new FileInputStream(new ClassPathResource("application.properties").getFile());
		properties.load(file);
		return properties;
	}

}
