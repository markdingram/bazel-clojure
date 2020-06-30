package(default_visibility = ["//visibility:public"])

java_library(
    name = "example",
    resources = glob(["*.clj"]),
    runtime_deps = [
        "@maven//:cheshire_cheshire",
    ],
)

# this is the only way to add the lib sources to the plugin classpath - this can be hidden in a macro easily enough
java_plugin(
    name = "aot",
    deps = [":example"],
)

java_binary(
    name = "bin",
    srcs = [":hello/package-info.java"],
    main_class = "example.hello",
    plugins = [
        ":aot",
        "//java/tools/aot",
    ],
    deps = [
        ":example",
        "//java/tools/aot",
    ],
)
