package com.example.dagger.modules;

import com.example.dagger.operations.GetPetsByIdModule;
import com.example.starter.MainVerticle;
import com.utils.handlers.requestid.RequestIdHandler;
import com.utils.routing.OperationConfiguration;
import com.utils.routing.RouterConfiguration;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.openapi.RouterBuilder;
import java.util.Map;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Module(includes = { VertxModule.class, GetPetsByIdModule.class })
public class MainVerticleModule {

  public static final int SERVE_PORT = 8888;
  public static final String OPEN_API_LOCATION = "api_openapi_spec.openapi.json";

  @Provides
  @IntoMap
  @StringKey("com.example.starter.MainVerticle")
  @Singleton
  public Verticle provideMainVerticleMap(MainVerticle mainVerticle) {
    return mainVerticle;
  }

  @Provides
  @Singleton
  public MainVerticle provideMainVerticle(
    HttpServerOptions serverOptions,
    Future<RouterBuilder> routerBuilderFuture
  ) {
    return new MainVerticle(serverOptions, routerBuilderFuture);
  }

  @Provides
  @Singleton
  public HttpServerOptions provideServerOptions() {
    HttpServerOptions serverOptions = new HttpServerOptions();
    serverOptions
      .setCompressionSupported(true)
      .setPort(SERVE_PORT)
      .setReuseAddress(true)
      .setReusePort(true);

    return serverOptions;
  }

  @Provides
  @Reusable
  public RequestIdHandler provideRequestIdHandler() {
    return new RequestIdHandler();
  }

  @Provides
  @Singleton
  public RouterConfiguration provideRouterConfiguration(
    Map<String, OperationConfiguration> operationMap,
    RequestIdHandler requestIdHandler
  ) {
    return RouterConfiguration
      .builder()
      .operationMap(operationMap)
      .globalHandler(requestIdHandler)
      .build();
  }

  @Provides
  @Singleton
  Future<RouterBuilder> provideOpenApiRouterBuilder(
    Vertx vertx,
    RouterConfiguration configuration
  ) {
    return RouterBuilder
      .create(vertx, OPEN_API_LOCATION)
      .onSuccess(routerBuilder -> configuration.configure(routerBuilder))
      .onFailure(Throwable::printStackTrace);
  }
}
