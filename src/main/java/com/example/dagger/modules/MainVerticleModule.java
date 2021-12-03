package com.example.dagger.modules;

import com.example.starter.MainVerticle;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;
import javax.inject.Singleton;
import javax.inject.Named;
import io.vertx.ext.web.validation.BadRequestException;
import com.example.dagger.modules.VertxModule;
import io.vertx.core.Future;

@Module(includes = VertxModule.class)
public class MainVerticleModule {
    public static final int SERVE_PORT = 8888;
    public static final String OPEN_API_LOCATION = "petstore.yaml";
    public static final String LIST_PETS_OPERATION_NAME = "listPets";

    @Provides
    @IntoMap
    @StringKey("com.example.starter.MainVerticle")
    public Verticle provideMainVerticleMap(
        MainVerticle mainVerticle
    ) {
        return mainVerticle;
    }

    @Provides
    public MainVerticle provideMainVerticle(Future<RouterBuilder> routerBuilderFuture) {
        return new MainVerticle(SERVE_PORT, routerBuilderFuture);
    }

    @Provides
    @Singleton
    Future<RouterBuilder> provideOpenApiRouterBuilder(Vertx vertx) {
        return RouterBuilder.create(vertx, OPEN_API_LOCATION)
            .onSuccess(routerBuilder -> {
                routerBuilder.operation(LIST_PETS_OPERATION_NAME)
                .handler(routingContext -> {
                    routingContext.response()
                        .putHeader("content-type", "text/plain")
                        .setStatusMessage("Called listPets")
                        .end("Hello! ListingPets");
                })
                .handler(routingContext -> { 
                    Throwable failure = routingContext.failure();
                    if (failure instanceof BadRequestException) {
                        routingContext
                            .response()
                            .setStatusCode(400)
                            .putHeader("content-type", "application/json")
                            .end(((BadRequestException) failure).toJson().toBuffer());
                    }
                });
            })
            .onFailure(err -> {});
    }
}

