package com.ifeed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan({"com.ifeed"})
@EnableAsync
@EnableJpaRepositories(basePackages = "com.ifeed.repository")
@EnableTransactionManagement
public class InteractiveFeedbackApplication extends SpringBootServletInitializer {
	private static Logger log = LoggerFactory.getLogger(InteractiveFeedbackApplication.class);

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(InteractiveFeedbackApplication.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			log.info("instanciated bean: \t" + beanName);
		}
	}
}