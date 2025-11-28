package br.com.petfriends.transporte.infra.config;

import com.mongodb.client.MongoClient;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonMongoConfig {
    private final MongoClient mongoClient;
    private final String databaseName;

    public AxonMongoConfig(MongoClient mongoClient, @Value("${MONGODB_DATABASE}") String databaseName) {
        this.mongoClient = mongoClient;
        this.databaseName = databaseName;
    }

    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongoClient, databaseName)
                .domainEventsCollectionName("axon_events")
                .snapshotEventsCollectionName("axon_snapshots")
                .build();
    }

    @Bean
    public EventStorageEngine storageEngine(MongoTemplate mongoTemplate, Serializer serializer) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(mongoTemplate)
                .eventSerializer(serializer)
                .snapshotSerializer(serializer)
                .build();
    }

    @Bean
    public EventStore eventStore(EventStorageEngine storageEngine) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .build();
    }
}

