package com.utils.routing;

import com.utils.handlers.contenttype.ContentTypeHandler;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.Operation;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.ValidationHandler;
import java.util.List;
import javax.xml.validation.ValidatorHandler;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

@Builder
public class OperationConfiguration {

  @NonNull
  private final ContentTypeHandler contentTypeHandler;

  @Singular
  private final List<ValidationHandler> validators;

  @Singular
  private final List<Handler<RoutingContext>> handlers;

  @Singular
  private final List<Handler<RoutingContext>> failureHandlers;

  public void configure(@NonNull final Operation operation) {
    addValidators(operation);
    operation.handler(contentTypeHandler);
    addBaseHandlers(operation);
    addFailureHandlers(operation);
  }

  private void addValidators(@NonNull final Operation operation) {
    for (ValidationHandler validator : validators) {
      operation.handler(validator);
    }
  }

  private void addBaseHandlers(@NonNull final Operation operation) {
    for (Handler<RoutingContext> handler : handlers) {
      operation.handler(handler);
    }
  }

  private void addFailureHandlers(@NonNull final Operation operation) {
    for (Handler<RoutingContext> failureHandler : failureHandlers) {
      operation.failureHandler(failureHandler);
    }
  }
}
