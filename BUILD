# Add rules here to build your software
# See https://docs.bazel.build/versions/master/build-ref.html#BUILD_files

# Clojure Socket REPL

REPL_PORT = 5555

java_binary(
    name = "repl",
    testonly = True,
    args = [
        "-e",
        """\"(require '[{ns}]) (in-ns '{ns}) (clojure.main/repl)\"""".format(ns = "user"),
    ],
    jvm_flags = ['-Dclojure.server.repl="{:port %s,:accept clojure.core.server/repl}"' % REPL_PORT],
    main_class = "clojure.main",
    runtime_deps = [
        "//dev",
        "//java/example",
        "//javatests/example",
        "@maven//:org_clojure_clojure",
        "@maven//:org_clojure_core_specs_alpha",
        "@maven//:org_clojure_spec_alpha",
    ],
)
