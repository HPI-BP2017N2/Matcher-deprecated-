package de.hpi.matching.config;

import com.mongodb.MongoClient;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public abstract class AbstractMongoConfig {

    @Setter(AccessLevel.PUBLIC) String host, database;
    @Setter(AccessLevel.PUBLIC) int port;

    // convenience
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(host, port), database);
    }

    abstract public MongoTemplate getMongoTemplate() throws Exception;
}

