package com.utils.handlers.rest;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.conversion.RestRequestConverter;
import com.utils.conversion.RestResponseConverter;

@Slf4j
public abstract class BaseRestHandler<InputType, OutputType>
  implements Handler<RoutingContext> {

  @NonNull
  private final RestRequestConverter<InputType> requestConverter;

  @NonNull
  private final RestResponseConverter<OutputType> responseConverter;

  protected BaseRestHandler(
    @NonNull final Class<InputType> inputTypeClass,
    @NonNull final ObjectMapper objectMapper
  ) {
    this.requestConverter = new RestRequestConverter<>(inputTypeClass, objectMapper);
    this.responseConverter = new RestResponseConverter<>(objectMapper);
  }

  @Override
  public void handle(@NonNull final RoutingContext context) {
    InputType input = requestConverter.convertToInputType(context);
    OutputType output = this.execute(input, context);
    responseConverter.createResponse(output, context);
  }

  public abstract OutputType execute(InputType input, RoutingContext event);
}
