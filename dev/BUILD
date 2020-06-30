package(default_visibility = ["//visibility:public"])

java_library(
    name = "dev",
    resource_strip_prefix = "dev",
    resources = glob([
        "*.clj",
    ]),
    runtime_deps = [
        "@maven//:spyscope_spyscope",
    ],
)
