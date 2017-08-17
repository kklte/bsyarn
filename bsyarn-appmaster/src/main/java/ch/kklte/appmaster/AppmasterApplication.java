package ch.kklte.appmaster;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.yarn.batch.config.EnableYarnBatchProcessing;
import org.springframework.yarn.batch.partition.StaticPartitionHandler;

@Configuration
@EnableAutoConfiguration
@EnableYarnBatchProcessing
public class AppmasterApplication {

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;

    @Bean
    private Job job() {
        return jobFactory
                .get("job")
                .incrementer(jobParametersIncrementer())
                .start(master())
                .build();
    }

    @Bean
    private JobParametersIncrementer jobParametersIncrementer() {
        return new RunIdIncrementer();
    }

    @Bean
    private Step master() {
        return stepFactory
                .get("master")
                .partitioner("remoteStep1", partitioner())
                .partitionHandler(partitionHandler())
                .build();
    }

    @Bean
    private Partitioner partitioner() {
        return new SimplePartitioner();
    }

    @Bean
    private PartitionHandler partitionHandler() {
        StaticPartitionHandler handler = new StaticPartitionHandler();
        handler.setGridSize(1);
        return handler;
    }
}
