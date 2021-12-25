package com.example.handlers;

import io.prometheus.client.Counter;

public class Metrics {

  public static final Counter requests = Counter
    .build()
    .name("requests_total")
    .help("Total requests.")
    .register();
}
