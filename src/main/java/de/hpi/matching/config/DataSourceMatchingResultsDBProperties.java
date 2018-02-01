package de.hpi.matching.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("datasource.matchingResults")
public class DataSourceMatchingResultsDBProperties {
    private MongoProperties mongodb = new MongoProperties();
}