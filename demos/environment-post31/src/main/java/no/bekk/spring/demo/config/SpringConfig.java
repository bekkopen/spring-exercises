package no.bekk.spring.demo.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import no.bekk.spring.demo.BrukerDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SuppressWarnings("unused")
@Configuration
//@ComponentScan({"no.bekk.spring.demo"})
public class SpringConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	public BrukerDAO brukerDao() {
		return new BrukerDAO(dataSource);
	}

	@Configuration
	@Profile("testing")
	static class DevConfig {

		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.addScript("classpath:/create-tables.sql").build();
		}
	}

	@Configuration
	@Profile("production")
	static class JndiDataConfig {
		
		@Bean
		public DataSource dataSource() throws Exception {
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup("jdbc/database");
		}

	}

}
