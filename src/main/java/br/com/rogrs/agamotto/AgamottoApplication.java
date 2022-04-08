package br.com.rogrs.agamotto;

import java.net.InetAddress;

import br.com.rogrs.agamotto.config.DefaultProfileUtil;
import br.com.rogrs.agamotto.config.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@Slf4j
public class AgamottoApplication {

	private final Environment env;

	public AgamottoApplication(Environment env) {
		this.env = env;
	}
	
	@Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(AgamottoApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
						+ "External: \t{}://{}:{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
				hostAddress, env.getProperty("server.port"), env.getActiveProfiles());
	}

}
