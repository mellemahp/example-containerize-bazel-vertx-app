package com.example.handlers;

import com.example.model.GetPetByIdRequest;
import com.example.model.GetPetByIdResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmellema.vertxutils.handlers.rest.BaseRestHandler;
import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PetHandler
    extends BaseRestHandler<GetPetByIdRequest, GetPetByIdResponse> {

  @Inject
  public PetHandler(final @NonNull ObjectMapper objectMapper) {
    super(GetPetByIdRequest.class, objectMapper);
  }

  @Override
  public GetPetByIdResponse execute(
      GetPetByIdRequest input,
      RoutingContext event
  ) {
    log.info("Entered Pet handler");
    log.info("Another log");
    return new GetPetByIdResponse().id(5).name("Test");
  }
}
