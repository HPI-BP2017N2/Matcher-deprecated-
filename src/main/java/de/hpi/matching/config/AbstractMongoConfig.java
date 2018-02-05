package de.hpi.matching.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;

public abstract class AbstractMongoConfig {

    @Setter(AccessLevel.PUBLIC) String host, database, username;
    @Setter(AccessLevel.PUBLIC) char[] password;
    @Setter(AccessLevel.PUBLIC) int port;

    // convenience
    public MongoDbFactory mongoDbFactory() throws Exception {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoCredential credential = MongoCredential.createCredential(username, database, password);
        return new SimpleMongoDbFactory(new MongoClient(serverAddress, Arrays.asList(credential)), database);
    }

    abstract public MongoTemplate getMongoTemplate() throws Exception;
}

