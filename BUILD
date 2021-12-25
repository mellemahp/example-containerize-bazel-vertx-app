load("@io_bazel_rules_docker//java:image.bzl", "java_image")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")

# Dagger setup
java_plugin(
    name = "dagger_plugin",
    generates_api = 1,
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

### Lombok
java_library(
    name = "lombok",
    exported_plugins = [
        ":lombok_plugin",
    ],
    exports = [
        "@maven//:org_projectlombok_lombok",
    ],
)

java_plugin(
    name = "lombok_plugin",
    processor_class = "lombok.launch.AnnotationProcessorHider$AnnotationProcessor",
    deps = [
        ":lombok_jar",
    ],
)

java_import(
    name = "lombok_jar",
    jars = [
        "@maven//:v1/https/repo1.maven.org/maven2/org/projectlombok/lombok/1.18.22/lombok-1.18.22.jar",
    ],
)

### nullaway
java_plugin(
    name = "nullaway",
    deps = [
        "@maven//:com_uber_nullaway_nullaway",
    ],
)

java_library(
    name = "preprocessors",
    exports = [
        # NOTE: ORDER OF PLUGINS DOES MATTER
        ":lombok",
        ":dagger_lib",
        ":nullaway",
    ],
)

java_library(
    name = "runtime_logging_deps",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:net_logstash_logback_logstash_logback_encoder",
    ],
)

java_library(
    name = "databind_deps",
    exports = [
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_jsr310",
        "@maven//:com_fasterxml_jackson_module_jackson_module_afterburner",
        "@maven//:com_fasterxml_jackson_module_jackson_module_paranamer",
    ],
)

java_library(
    name = "common_dependencies",
    exports = [
        ":databind_deps",
        # logging
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:ch_qos_logback_logback_classic",
        "@maven//:net_logstash_logback_logstash_logback_encoder",
        "@maven//:io_reactiverse_reactiverse_contextual_logging",
        # vertx
        "@maven//:io_vertx_vertx_core",
        "@maven//:io_vertx_vertx_web_openapi",
        "@maven//:io_vertx_vertx_web_validation",
        "@maven//:io_vertx_vertx_web",
        "@maven//:io_vertx_vertx_json_schema",
        # metrics
        "@maven//:io_vertx_vertx_micrometer_metrics",
        "@maven//:io_micrometer_micrometer_registry_prometheus",
        "@maven//:io_prometheus_simpleclient_vertx",
        "@maven//:io_prometheus_simpleclient",
    ],
)

filegroup(
    name = "logback_config",
    srcs = ["src/main/resources/logback.xml"],
)

# Build Handlers
java_binary(
    name = "Launcher",
    srcs = glob(["src/main/java/com/**/*.java"]),
    javacopts = [
        # Sets nullaway errors to break build
        "-Xep:NullAway:ERROR",
        # sets packages for nullaway to run on
        "-XepOpt:NullAway:AnnotatedPackages=com.example",
        # Tries to ignore dagger-generated classes
        "-XepOpt:NullAway:UnannotatedSubPackages=com.example.dagger",
    ],
    main_class = "com.example.starter.Launcher",
    resources = [
        ":logback_config",
        ":api_openapi_spec",
    ],
    runtime_deps = [
        ":runtime_logging_deps",
    ],
    deps = [
        ":common_dependencies",
        ":preprocessors",
        ":api_models"
    ],
)

container_image(
    name = "app_container",
    base = "@openjdk_jre_base//image",
    entrypoint = [
        "java",
        "-jar",
        "Launcher_deploy.jar",
    ],
    files = [":Launcher_deploy.jar"],
    ports = ["8888"],
    visibility = ["//visibility:public"],
)

#### SMITHY
load("@smithy_rules//smithy:smithy.bzl", "smithy_java_models", "smithy_openapi")
load("@smithy_rules//smithy/validation:validation.bzl", "smithy_validator_library")

filegroup(
    name = "model_files",
    srcs = [] + glob(["model/*"]),
)

SERVICE_NAME = "PetStoreService"

smithy_validator_library(
    name = "default_model_validations",
)

smithy_openapi(
    name = "api_openapi_spec",
    srcs = [":model_files"],
    config = "smithy-build.json",
    projection = "model",
    service_name = SERVICE_NAME,
    deps = [
        ":default_model_validations",
    ],
)

smithy_java_models(
    name = "api_models",
    srcs = [":model_files"],
    config = "smithy-build.json",
    model_namespace = "com.example.model",
    projection = "model",
    service_name = SERVICE_NAME,
    deps = [],
)
