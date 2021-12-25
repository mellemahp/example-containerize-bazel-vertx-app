package com.example.dagger.modules;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import java.util.Map;

@Module
public class ObjectMappersModule {

  @Reusable
  @Provides
  ObjectMapper provideObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    // Don't throw an exception when json has extra fields, just ignore them
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Ignore null values when writing json.
    mapper.setSerializationInclusion(Include.NON_NULL);

    // Serializes maps correctly
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper
      .configOverride(Map.class)
      .setInclude(Value.construct(Include.NON_NULL, Include.NON_NULL));

    // Write times as a String instead of a Long so its human readable.
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.registerModule(new JavaTimeModule());

    return mapper;
  }
}
