package com.example.dagger.operations;

import com.example.dagger.modules.VertxModule;
import com.example.dagger.operations.GetPetsByIdModule;
import com.example.handlers.PetHandler;
import com.example.handlers.PetHandler;
import com.example.starter.MainVerticle;
import com.utils.handlers.contenttype.JsonContentTypeHandler;
import com.utils.routing.OperationConfiguration;
import com.utils.routing.OperationConfiguration;
import dagger.Module;
import dagger.Module;
import dagger.Provides;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.Operation;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.impl.OpenAPI3ValidationHandlerGenerator;
import io.vertx.ext.web.openapi.impl.OperationImpl;
import io.vertx.ext.web.validation.BadRequestException;
import io.vertx.ext.web.validation.ParameterProcessorException;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.validation.builder.Parameters;
import io.vertx.ext.web.validation.impl.ValidationHandlerImpl;
import io.vertx.json.schema.SchemaParser;
import io.vertx.json.schema.SchemaRouter;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.inject.Singleton;
import javax.naming.OperationNotSupportedException;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;

@Module
@Slf4j
public class GetPetsByIdModule {

  public static final String GET_BY_ID_OPERATION_NAME = "GetPetById";

  @Provides
  @IntoMap
  @Singleton
  @StringKey(GET_BY_ID_OPERATION_NAME)
  OperationConfiguration provideOperationConfig(PetHandler petHandler) {
    return OperationConfiguration
      .builder()
      .contentTypeHandler(new JsonContentTypeHandler())
      .handler(
        requestContext -> {
          log.info("Pre-handler log");
          requestContext.next();
        }
      )
      .handler(petHandler)
      .failureHandler(
        routingContext -> {
          Throwable failure = routingContext.failure();
          if (failure instanceof BadRequestException) {
            routingContext
              .response()
              .setStatusCode(400)
              .end(((BadRequestException) failure).toJson().toBuffer());
          }
        }
      )
      .build();
  }
}
