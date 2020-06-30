Bazel Clojure
=============

Simple started project with Repl / AOT / Testing as a starting point.

Requires Bazelisk / Yarn / OpenJDK 11, possibly other things. Tested on Mac OSX & Linux.

Some example commands:

- build:  `bazel build //...`
- repl (socket repl, port 5555): `bazel run repl` 
- test (kaocha): `bazel test //...`

- query external JARs: `bazel query @maven//... | sort`


Notes on implementation
=======================

Inspired by:
<https://github.com/simuons/rules_clojure>

This repo differs by attempting to build directly on the built in Java rules, which has some implications:

- The root folder names `java` and `javatests` are mandated by these rules (or the trad Maven `src/main/java`).

- AOT is made possible through a <https://docs.bazel.build/versions/master/be/java.html#java_plugin>.
  This triggers a Java Annotation Processor that picks up the namespace to AOT from a Java annotation. A bit 
  roundabout but seems to work quite nicely - whether it is a good idea remains to be seen! 
  
  One nice feature of this approach is that the exact files produced by AOT can be separately inspected via: `unzip -l dist/bin/java/example/bin.jar`


- Kaocha is integrated via a basic Bazel macro in [rules.bzl](rules/kaocha/rules.bzl)

- The Intellij plugin doesn't add runtime deps as External Libraries. The `tools/intellij/BUILD` is
  a hack to get at least the direct dependencies showing up in Intellij. Few issues around this, 
  e.g. <https://github.com/bazelbuild/intellij/issues/1825>,
  <https://github.com/bazelbuild/intellij/issues/490>