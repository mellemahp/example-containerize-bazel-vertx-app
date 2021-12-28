load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

git_repository(
    name="vertx-utils",
    branch="main",
    remote="https://github.com/mellemahp/vertx-utils",
)

# import libraries from maven
RULES_JVM_EXTERNAL_TAG = "2.8"

RULES_JVM_EXTERNAL_SHA = "79c9850690d7614ecdb72d68394f994fef7534b292c4867ce5e7dec0aa7bdfad"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        # dagger dependencies
        "com.google.dagger:dagger:2.32",
        "com.google.dagger:dagger-compiler:2.32",
        "javax.inject:javax.inject:1",
        # openapi build
        "org.junit.jupiter:junit-jupiter-api:5.8.2",
        "org.junit.jupiter:junit-jupiter-engine:5.8.2",
        # vert.x dependencies
        "io.vertx:vertx-core:4.2.1",
        "io.vertx:vertx-junit5:4.2.1",
        "io.vertx:vertx-web-openapi:4.2.1",
        "io.vertx:vertx-web-validation:4.2.1",
        "io.vertx:vertx-web:4.2.1",
        "io.vertx:vertx-json-schema:4.2.1",
        # nullaway
        "com.google.code.findbugs:jsr305:3.0.2",
        "com.uber.nullaway:nullaway:0.3.4",
        # lombok
        "org.projectlombok:lombok:1.18.22",
        # logging
        "org.slf4j:slf4j-api:1.8.0-beta4",
        "ch.qos.logback:logback-classic:1.3.0-alpha4",
        # structured logging
        "net.logstash.logback:logstash-logback-encoder:6.4",
        # contextual logging
        "io.reactiverse:reactiverse-contextual-logging:1.1.2",
        # jackson
        "com.fasterxml.jackson.core:jackson-databind:2.12.4",
        "com.fasterxml.jackson.core:jackson-core:2.12.4",
        "com.fasterxml.jackson.module:jackson-module-afterburner:2.12.4",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.4",
        "com.fasterxml.jackson.core:jackson-annotations:2.12.4",
        "com.fasterxml.jackson.module:jackson-module-paranamer:2.12.4",
        # metrics
        "io.vertx:vertx-micrometer-metrics:4.2.3",
        "io.micrometer:micrometer-registry-prometheus:1.8.1",
        "io.prometheus:simpleclient_vertx:0.14.1",
        "io.prometheus:simpleclient:0.14.1",
        # smithy
        "javax.annotation:javax.annotation-api:1.3.2",
        "io.swagger:swagger-annotations:1.6.3",
        "org.openapitools:jackson-databind-nullable:0.2.2",
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)

http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "1f4e59843b61981a96835dc4ac377ad4da9f8c334ebe5e0bb3f58f80c09735f4",
    strip_prefix = "rules_docker-0.19.0",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.19.0/rules_docker-v0.19.0.tar.gz"],
)

load(
    "@io_bazel_rules_docker//repositories:repositories.bzl",
    container_repositories = "repositories",
)

container_repositories()

load(
    "@io_bazel_rules_docker//container:container.bzl",
    "container_pull",
)
load(
    "@io_bazel_rules_docker//java:image.bzl",
    _java_image_repos = "repositories",
)

_java_image_repos()

container_pull(
    name = "openjdk_jre_base",
    digest = "sha256:fed60138327b2d86167759c565751f69af7a0c1b4f85af515d7ce52b8f00fdc1",
    registry = "index.docker.io",
    repository = "adoptopenjdk/openjdk11",
    tag = "jdk-11.0.13_8-alpine-slim",
)

#######################
# Setup OpenApi tools
#######################
OPENAPI_GEN_VERSION = "0.1.6"

OPENAPI_GEN_SHA = "6e3019e4f63a5cb478d84e6e3852fa1f573365c5a65a513b25e8ff9def4d54e7"

http_archive(
    name = "openapi_tools_generator_bazel",
    sha256 = OPENAPI_GEN_SHA,
    url = "https://github.com/mellemahp/openapi-generator-bazel/releases/download/%s/openapi-tools-generator-bazel-%s.tar.gz" % (OPENAPI_GEN_VERSION, OPENAPI_GEN_VERSION),
)

load("@openapi_tools_generator_bazel//:defs.bzl", "openapi_tools_generator_bazel_repositories")

openapi_tools_generator_bazel_repositories()

#######################
# Setup Smithy Rules
#######################
SMITHY_RULES_TAG = "1.0.2"

SMITHY_RULES_SHA = "f75d5c677ea2e80dcae324f77b925152b7980b37d5b7f9624203eda652410ee3"

http_archive(
    name = "smithy_rules",
    url = "https://github.com/mellemahp/smithy-bazel-rules/releases/download/%s/release.tar.gz" % SMITHY_RULES_TAG,
)

load("@smithy_rules//smithy:deps.bzl", "smithy_cli_init")

smithy_cli_init()
