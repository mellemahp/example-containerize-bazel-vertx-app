package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MainVerticle extends AbstractVerticle {

  @NonNull
  private final HttpServerOptions serverOptions;

  @NonNull
  private final Future<RouterBuilder> routerBuilderFuture;

  @Override
  public void start(final @NonNull Promise<Void> startPromise)
    throws Exception {
    routerBuilderFuture.onSuccess(
      routerBuilder -> {
        Router router = routerBuilder.createRouter();
        vertx
          .createHttpServer(serverOptions)
          .requestHandler(router)
          .listen(
            serverOptions.getPort(),
            http -> {
              if (http.succeeded()) {
                startPromise.complete();
                log.info(
                  "HTTP server started on port {}",
                  serverOptions.getPort()
                );
              } else {
                startPromise.fail(http.cause());
              }
            }
          );
      }
    );
  }
}
