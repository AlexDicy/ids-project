package it.unicam.cs.ids;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;

import java.time.OffsetTime;
import java.time.ZoneOffset;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String dbName;
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Override
    @NonNull
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        return MongoClients.create(connectionString);
    }

    @Override
    public void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter adapter) {
        adapter.registerConverter(new OffsetTimeToDocumentConverter());
        adapter.registerConverter(new DocumentToOffsetTimeConverter());
    }

    @WritingConverter
    public static class OffsetTimeToDocumentConverter implements Converter<OffsetTime, Document> {
        @Override
        public Document convert(OffsetTime source) {
            OffsetTime utc = source.withOffsetSameInstant(ZoneOffset.UTC);
            return new Document("hour", utc.getHour())
                    .append("minute", utc.getMinute())
                    .append("offset", utc.getOffset().getId());
        }
    }

    @ReadingConverter
    public static class DocumentToOffsetTimeConverter implements Converter<Document, OffsetTime> {
        @Override
        public OffsetTime convert(Document source) {
            ZoneOffset offset = ZoneOffset.of(source.getString("offset"));
            return OffsetTime.of(source.getInteger("hour"), source.getInteger("minute"), 0, 0, offset);
        }
    }
}
