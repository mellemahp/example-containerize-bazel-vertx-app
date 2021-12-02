package com.example.dagger.components;

import dagger.Component;
import javax.inject.Singleton;

import com.example.dagger.modules.DaggerVerticleFactoryModule;
import com.example.dagger.modules.VertxModule;
import com.example.starter.Application;
import com.example.dagger.modules.MainVerticleModule;

@Singleton
@Component(modules = { 
    DaggerVerticleFactoryModule.class, 
    VertxModule.class, 
    MainVerticleModule.class
})
public interface ApplicationComponent {
    Application getApplication();
}