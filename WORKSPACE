load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

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
        # vert.x dependencies
        "io.vertx:vertx-core:4.2.1",
        "io.vertx:vertx-junit5:4.2.1",
        "org.junit.jupiter:junit-jupiter-api:5.8.2",
        "org.junit.jupiter:junit-jupiter-engine:5.8.2"
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
