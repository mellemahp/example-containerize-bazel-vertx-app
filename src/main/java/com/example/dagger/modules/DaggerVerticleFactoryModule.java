package com.example.dagger.modules;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import com.example.dagger.modules.DaggerVerticleFactory;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

/**
 */
@Module
public class DaggerVerticleFactoryModule {
    @Provides
    @Singleton
    VerticleFactory provideVerticleFactory(Map<String, Provider<Verticle>> verticleMap) {
        return new DaggerVerticleFactory(verticleMap);
    }
}
