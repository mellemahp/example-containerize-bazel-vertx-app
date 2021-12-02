package com.example.starter;

import java.util.List;

import javax.inject.Inject;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

public class Application {
    private final Vertx vertx;
    private final MainVerticle mainVerticle;

    @Inject
    public Application(Vertx vertx, MainVerticle mainVerticle) {
        this.vertx = vertx;
        this.mainVerticle = mainVerticle;
    }

    public void run() {
        vertx.deployVerticle(mainVerticle);
    }
}
