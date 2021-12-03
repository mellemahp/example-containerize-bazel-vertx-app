# Dagger setup
java_plugin(
    name = "dagger_plugin",
    processor_class = "dagger.internal.codegen.ComponentProcessor",
    deps = [
        "@maven//:com_google_dagger_dagger_compiler",
    ],
)

java_library(
    name = "dagger_lib",
    exported_plugins = ["dagger_plugin"],
    exports = [
        "@maven//:com_google_dagger_dagger",
        "@maven//:javax_inject_javax_inject",
    ],
)

java_library(
    name = "common_dependencies",
    exports = [
        ":dagger_lib",
        "@maven//:io_vertx_vertx_core",
        "@maven//:io_vertx_vertx_web_openapi",
        "@maven//:io_vertx_vertx_web_validation",
        "@maven//:io_vertx_vertx_web",
        "@maven//:io_vertx_vertx_web_api_contract"
    ],
)

filegroup(
    name="petstoreyaml",
    srcs = ["src/main/resources/petstore.yaml"],
)


# Build Handlers
java_binary(
    name = "MainVerticle",
    srcs = glob(["src/main/java/com/example/**/*.java"]),
    main_class = "com.example.starter.Launcher",
    deps = [
        ":common_dependencies"
    ],
    resources = [
        ":petstoreyaml"
    ]
)
