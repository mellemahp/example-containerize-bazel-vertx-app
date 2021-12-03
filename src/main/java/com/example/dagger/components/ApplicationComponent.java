package com.example.dagger.components;

import dagger.Component;
import javax.inject.Singleton;

import com.example.starter.Application;
import com.example.dagger.modules.MainVerticleModule;

@Singleton
@Component(modules = { MainVerticleModule.class })
public interface ApplicationComponent {
    Application getApplication();
}