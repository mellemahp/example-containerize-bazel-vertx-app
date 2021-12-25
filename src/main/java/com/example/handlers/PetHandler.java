package com.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.handlers.rest.BaseRestHandler;

import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import static com.example.handlers.Metrics.requests;

@Slf4j
public class PetHandler extends BaseRestHandler<PetInput, PetOutput> {

  @Inject
  public PetHandler(final @NonNull ObjectMapper objectMapper) {
    super(PetInput.class, objectMapper);
  }

  @Override
  public PetOutput execute(PetInput input, RoutingContext event) {
    requests.inc();
    log.info("Entered Pet handler");
    log.info("Another log");
    return new PetOutput(5, "boop");
  }
}
