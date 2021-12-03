package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.Router;
import io.vertx.core.Future;

public class MainVerticle extends AbstractVerticle {
  
  private final int listeningPort;
  private final Future<RouterBuilder> routerBuilderFuture;

  public MainVerticle(final int listeningPort, final Future<RouterBuilder> routerBuilderFuture) {
    this.listeningPort = listeningPort;
    this.routerBuilderFuture = routerBuilderFuture;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    routerBuilderFuture.onSuccess(routerBuilder -> {
      Router router = routerBuilder.createRouter();
      vertx.createHttpServer()
        .requestHandler(router)
        .listen(listeningPort, http -> {
          if (http.succeeded()) {
            startPromise.complete();
            System.out.printf("HTTP server started on port %s%n", listeningPort);
          } else {
            startPromise.fail(http.cause());
          }
        });
    });
  }
}