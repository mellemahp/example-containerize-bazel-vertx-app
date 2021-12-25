package com.utils.routing;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.Operation;
import io.vertx.ext.web.openapi.RouterBuilder;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

@Builder
public class RouterConfiguration {

  @NonNull
  private final Map<String, OperationConfiguration> operationMap;

  @Singular
  private final List<Handler<RoutingContext>> globalHandlers;

  public void configure(@NonNull final RouterBuilder routerbuilder) {
    configureGlobalHandlers(routerbuilder);
    configureOperations(routerbuilder);
  }

  private void configureGlobalHandlers(@NonNull final RouterBuilder routerbuilder) {
    for (Handler<RoutingContext> handler: globalHandlers) {
      routerbuilder.rootHandler(handler);
    }
  }

  private void configureOperations(@NonNull final RouterBuilder routerbuilder) {
    for (Map.Entry<String, OperationConfiguration> operationEntry : operationMap.entrySet()) {
      String operationName = operationEntry.getKey();
      OperationConfiguration operationConfiguration = operationEntry.getValue();
      Operation operation = routerbuilder.operation(operationName);
      operationConfiguration.configure(operation);
    }
  }
}
