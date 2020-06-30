_TEMPLATE = "//rules/kaocha:kaocha.edn.tpl"

def _config_impl(ctx):
    out = ctx.actions.declare_file("kaocha.edn")
    # print (out)
    ctx.actions.expand_template(
        template = ctx.file._template,
        output = out,
        substitutions = {
            "{PREFIX}": ctx.attr.prefix,
        })

    return [DefaultInfo(
        files = depset([out]),
        data_runfiles = ctx.runfiles(files = [out]),
    )]

config = rule(
    implementation = _config_impl,
    attrs = {
        "prefix": attr.string(),
        "_template": attr.label(
            default = Label(_TEMPLATE),
            allow_single_file = True,
        ),
    }
)

def kaocha(name, prefix, package, deps, tests, testResources, **kwargs):
    """
      prefix - the prefix to the "test" directory

      tests - kaocha finds tests via file system (not classpath) so these are passed as data.
      testResources - added to classpath via resources. The prefix is stripped.
    """
    config_name = name + "_config"
    config(name = config_name, prefix = prefix, **kwargs)
    config_file_location = "%s/%s/kaocha.edn" % (prefix, package)
    native.java_test(
        name = name,
        data = [config_name] + tests,
        resources = testResources,
        resource_strip_prefix = prefix,
        main_class = "clojure.main",
        args = ["-m", "kaocha.runner", "--config-file", config_file_location], # , "--print-config"
        runtime_deps = deps + [
            "@maven//:lambdaisland_kaocha",
            "@maven//:org_clojure_clojure",
            "@maven//:org_clojure_core_specs_alpha",
            "@maven//:org_clojure_spec_alpha",
        ],
        use_testrunner = False,
        **kwargs
    )
