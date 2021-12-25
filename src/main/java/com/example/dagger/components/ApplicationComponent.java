package com.example.dagger.components;

import dagger.Component;
import javax.inject.Singleton;

import com.example.dagger.modules.MainVerticleModule;
import com.example.dagger.modules.ObjectMappersModule;
import com.example.starter.Application;

@Singleton
@Component(modules = { MainVerticleModule.class, ObjectMappersModule.class})
public interface ApplicationComponent {
    Application getApplication();
}