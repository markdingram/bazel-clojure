package tools.aot;

import clojure.java.api.Clojure;
import clojure.lang.Compiler;
import clojure.lang.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@SupportedAnnotationTypes("tools.aot.Aot")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AotProcessor extends AbstractProcessor {

    private void aot(String namespace, Path outputPath) {
        final ClassLoader prior = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            IFn compileFn = Clojure.var("clojure.core", "compile");

            Var.pushThreadBindings(RT.map(
                    Compiler.COMPILE_PATH, outputPath.toString(),
                    Compiler.COMPILE_FILES, Boolean.TRUE));
            compileFn.invoke(Symbol.create(namespace));
            Var.popThreadBindings();
        } finally {
            Thread.currentThread().setContextClassLoader(prior);
        }
    }

    private Path getOutputPath() {
        try {
            FileObject resource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "tmp", (Element[]) null);
            Path outputPath = Paths.get(resource.toUri()).getParent();
            resource.delete();
            return outputPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printClasspath() {
        //System.getenv().forEach((s, s2) -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ENV " + s + " = " + s2));
        //System.getProperties().forEach((s, s2) -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "PROP " + s + " = " + s2));
        //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, System.getProperty("java.class.path"));
        //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, System.getenv("user.dir"));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                Aot aot = element.getAnnotation(Aot.class);
                Path outputPath = getOutputPath();
                PackageElement packageElement = (PackageElement)element;
                String namespace = aot.namespace().equals("") ? packageElement.getQualifiedName().toString() : aot.namespace();
                // printClasspath();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Clojure AOT for ns: " + namespace + ", output: " + outputPath);
                aot(namespace, outputPath);
            }
        }
      	return true;
    }
}
