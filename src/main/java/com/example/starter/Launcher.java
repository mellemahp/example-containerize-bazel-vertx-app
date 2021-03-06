package com.example.starter;

import com.example.dagger.components.DaggerApplicationComponent;
import com.example.starter.MainVerticle;
import io.vertx.core.Verticle;

public class Launcher {

  private static final Application application = DaggerApplicationComponent
    .create()
    .getApplication();

  public static void main(String... args) {
    application.run();
  }
}
