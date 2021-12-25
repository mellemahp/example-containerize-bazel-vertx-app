package main.java.com.example.dagger.modules;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;

@Module
public class MetricsModule {

  public static final String METRICS_ENDPOINT = "/metrics";
  public static final int METRICS_PORT = 8080;

  @Provides
  public MetricsOptions provideMetricsOptions() {
    return new MicrometerMetricsOptions()
      .setPrometheusOptions(
        new VertxPrometheusOptions()
          .setEnabled(true)
          .setStartEmbeddedServer(true)
          .setEmbeddedServerOptions(
            new HttpServerOptions().setPort(METRICS_PORT)
          )
          .setEmbeddedServerEndpoint(METRICS_ENDPOINT)
      )
      .setEnabled(true);
  }
}
