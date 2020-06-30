package(default_visibility = ["//visibility:public"])

java_plugin(
    name = "aot",
    srcs = glob(["*.java"]),
    processor_class = "tools.aot.AotProcessor",
    deps = [
        "@maven//:org_clojure_clojure",
        "@maven//:org_clojure_core_specs_alpha",
        "@maven//:org_clojure_spec_alpha",
    ],
)
