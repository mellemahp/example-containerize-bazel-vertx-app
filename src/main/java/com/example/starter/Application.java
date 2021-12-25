package com.example.starter;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class Application {

  @NonNull
  private final Vertx vertx;

  @NonNull
  private final MainVerticle mainVerticle;

  public void run() {
    vertx.deployVerticle(mainVerticle);
  }
}
