package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;


public class MainVerticle extends AbstractVerticle {
  
  private final int listeningPort;

  public MainVerticle(final int listeningPort) {
    this.listeningPort = listeningPort;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(listeningPort, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.printf("HTTP server started on port %s%n", listeningPort);
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
