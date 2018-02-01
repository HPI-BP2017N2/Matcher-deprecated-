package de.hpi.matching.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


//@EnableConfigurationProperties(DataSourceMatchingResultsDBProperties.class)
//@ConfigurationProperties("datasource.matchingResults")
@EnableMongoRepositories(basePackages = {"de.hpi.matching.repo.matchingResults"}, mongoTemplateRef = "matchingResultsTemplate")
public class MatchingResultsDBConfiguration {
    @Bean
    @Primary
    public MongoProperties primaryDataSource() {
        return new MongoProperties();
    }
}
