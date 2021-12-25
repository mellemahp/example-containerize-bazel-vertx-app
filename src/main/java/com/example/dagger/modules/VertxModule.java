package com.example.dagger.modules;

import com.utils.dagger.DaggerVerticleFactory;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.VerticleFactory;
import java.util.Map;
import javax.inject.Provider;
import javax.inject.Singleton;
import main.java.com.example.dagger.modules.MetricsModule;

@Module(includes = { MetricsModule.class })
public class VertxModule {

  @Provides
  @Singleton
  VerticleFactory provideVerticleFactory(
    final Map<String, Provider<Verticle>> verticleMap
  ) {
    return new DaggerVerticleFactory(verticleMap);
  }

  @Provides
  @Singleton
  Vertx provideVertx(
    VerticleFactory verticleFactory,
    VertxOptions vertxOptions
  ) {
    Vertx vertx = Vertx.vertx(vertxOptions);
    vertx.registerVerticleFactory(verticleFactory);
    return vertx;
  }

  @Provides
  @Singleton
  VertxOptions provideVertxOptions(MetricsOptions metricsOptions) {
    return new VertxOptions().setMetricsOptions(metricsOptions);
  }
}
