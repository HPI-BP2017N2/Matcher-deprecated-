package de.hpi.matching.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;

public abstract class AbstractMongoConfig {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PUBLIC) String host, database, username;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PUBLIC) char[] password;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PUBLIC) int port;

    // convenience
    public MongoDbFactory mongoDbFactory() throws Exception {
        ServerAddress serverAddress = new ServerAddress(getHost(), getPort());
        MongoCredential credential = MongoCredential.createCredential(getUsername(), getDatabase(), getPassword());
        return new SimpleMongoDbFactory(new MongoClient(serverAddress, Arrays.asList(credential)), getDatabase());
    }

    abstract public MongoTemplate getMongoTemplate() throws Exception;
}

