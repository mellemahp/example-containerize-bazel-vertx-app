package com.utils.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RestResponseConverter<OutputType> {

  private static final int BAD_JSON_STATUS_CODE = 400;
  private static final String BAD_JSON_ERROR_MESSAGE = "Malformed Request";

  @NonNull
  private final ObjectMapper objectMapper;

  public void createResponse(
    final @NonNull OutputType outputObject,
    final @NonNull RoutingContext context
  ) {
    try {
      context.response().end(objectMapper.writeValueAsString(outputObject));
    } catch (JsonProcessingException exception) {
      log.error("Failed to process input data.", exception);
      returnMalformedRequestException(context);
    }
  }

  private void returnMalformedRequestException(
    final @NonNull RoutingContext context
  ) {
    context
      .response()
      .setStatusCode(BAD_JSON_STATUS_CODE)
      .setStatusMessage(BAD_JSON_ERROR_MESSAGE)
      .end();
  }
}
