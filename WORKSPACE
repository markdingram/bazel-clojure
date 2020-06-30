workspace(name = "cdk_clojure")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.2"

RULES_JVM_EXTERNAL_SHA = "82262ff4223c5fda6fb7ff8bd63db8131b51b413d26eb49e3131037e79e324af"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

NETTY_VERSION = "4.1.50.Final"

maven_install(
    artifacts = [
        maven.artifact(
            group = "org.clojure",
            artifact = "clojure",
            version = "1.10.1",
            exclusions = [
                "org.clojure:spec.alpha",
                "org.clojure:core.specs.alpha",
            ],
        ),
        maven.artifact(
            group = "org.clojure",
            artifact = "spec.alpha",
            version = "0.2.187",
            exclusions = [
                "org.clojure:clojure",
            ],
        ),
        maven.artifact(
            group = "org.clojure",
            artifact = "core.specs.alpha",
            version = "0.2.44",
            exclusions = [
                "org.clojure:clojure",
                "org.clojure:spec.alpha",
            ],
        ),
        "cheshire:cheshire:5.10.0",
        "lambdaisland:kaocha:1.0.632",
        
        "spyscope:spyscope:0.1.6",
        "org.clojure:core.rrb-vector:0.1.1"
    ],
    repositories = [
        "https://jcenter.bintray.com/",
        "https://repo.clojars.org",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

