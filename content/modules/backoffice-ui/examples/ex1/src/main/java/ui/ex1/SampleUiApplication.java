package ui.ex1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleUiApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
