package com.utils.handlers.contenttype;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;

@RequiredArgsConstructor
public abstract class ContentTypeHandler implements Handler<RoutingContext> {
    @NonNull private final String contentTypeHeaderKey;
    @NonNull private final String contentTypeHeaderValue;

    @Override
    public void handle(RoutingContext context) {
      context.response().putHeader(contentTypeHeaderKey, contentTypeHeaderValue);
      context.next();
    }
}
