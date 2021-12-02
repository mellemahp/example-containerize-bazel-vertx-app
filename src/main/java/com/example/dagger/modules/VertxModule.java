package com.example.dagger.modules;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import javax.inject.Singleton;

@Module
public class VertxModule {
    @Provides
    @Singleton
    Vertx provideVertx(VerticleFactory verticleFactory) {
        Vertx vertx = Vertx.vertx();
        vertx.registerVerticleFactory(verticleFactory);
        return vertx;
    }
}