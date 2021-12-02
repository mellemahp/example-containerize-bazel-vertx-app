package com.example.dagger.modules;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.NoSuchElementException;

import javax.inject.Provider;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;

public class DaggerVerticleFactory implements VerticleFactory {
    private static final String prefix = "Dagger";
    private final Map<String, Provider<Verticle>> verticleMap; 

    public DaggerVerticleFactory(Map<String, Provider<Verticle>> verticleMap) {
        this.verticleMap = verticleMap;
    }

    @Override
    public String prefix() {
        return prefix;
    }

    @Override
    public void createVerticle(final String verticleName, ClassLoader classLoader, Promise<Callable<Verticle>> promise) {
        final String identifier = VerticleFactory.removePrefix(verticleName);
        try {
            Verticle verticle = Optional.ofNullable(verticleMap.get(identifier))
                .orElseThrow(() -> new NoSuchElementException("No provider for verticle type found for: " + verticleName))
                .get();
            promise.complete(() -> verticle);
            System.out.printf("Verticle for type: %s created", verticleName);
        } catch (Exception e) {
            promise.fail(e);
        }
    }    
}