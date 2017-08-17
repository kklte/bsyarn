package ch.kklte.container;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = BatchAutoConfiguration.class)
public class ContainerApplication {

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step operation() {
        return steps.get("remoteStep1").tasklet(new TestTasklet()).build();
    }

    public static void main(String args[]) {
        SpringApplication.run(ContainerApplication.class, args);
    }

}
