package datatools.ex1;

import io.jmix.core.security.CoreSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleDataToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleDataToolsApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @EnableWebSecurity
    static class SecurityConfiguration extends CoreSecurityConfiguration {
    }
}
