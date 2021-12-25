package com.utils.conversion;

import java.util.Optional;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RestRequestConverter<InputType> {

  @NonNull
  private final Class<InputType> inputTypeClass;

  @NonNull
  private final ObjectMapper objectMapper;

  public InputType convertToInputType(
    final @NonNull RoutingContext routingContext
  ) {
    JsonObject inputData = extractCombinedParams(routingContext);
    return objectMapper.convertValue(inputData, inputTypeClass);
  }

  private JsonObject extractCombinedParams(
    final @NonNull RoutingContext context
  ) {
    JsonObject metadata = extractParameters(context);
    Optional.ofNullable(context.getBodyAsJson())
        .ifPresent(bodyJsonData -> metadata.mergeIn(bodyJsonData));

    return metadata;
  }

  private JsonObject extractParameters(final @NonNull RoutingContext context) {
    MultiMap allParams = MultiMap.caseInsensitiveMultiMap();
    allParams.addAll(context.queryParams());
    allParams.addAll(context.pathParams());

    return multiMapToJsonObject(allParams);
  }

  private JsonObject multiMapToJsonObject(final @NonNull MultiMap multimap) {
    JsonObject jsonData = new JsonObject();
    for (Map.Entry<String, String> entry : multimap.entries()) {
      jsonData.put(entry.getKey(), entry.getValue());
    }
    return jsonData;
  }
}
