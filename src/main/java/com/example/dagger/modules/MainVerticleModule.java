package com.example.dagger.modules;

import com.example.starter.MainVerticle;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import io.vertx.core.Verticle;


@Module
public class MainVerticleModule {
    @Provides
    @IntoMap
    @StringKey("com.example.starter.MainVerticle")
    public Verticle provideMainVerticleMap(
        MainVerticle mainVerticle
    ) {
        return mainVerticle;
    }

    @Provides
    public MainVerticle provideMainVerticle() {
        return new MainVerticle(8888);
    }
}

