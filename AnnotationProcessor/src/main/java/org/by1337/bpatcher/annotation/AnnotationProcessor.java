package org.by1337.bpatcher.annotation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.by1337.bpatcher.patcher.api.Inject;
import org.by1337.bpatcher.patcher.api.Main;
import org.by1337.bpatcher.patcher.api.Patch;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SupportedAnnotationTypes({"org.by1337.bpatcher.patcher.api.Patch", "org.by1337.bpatcher.patcher.api.Inject", "org.by1337.bpatcher.patcher.api.Main"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    private Set<String> injectClasses = new HashSet<>();
    private Set<String> patchClasses = new HashSet<>();
    private String main;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Patch.class)) {
            if (element instanceof TypeElement) {
                String className = ((TypeElement) element).getQualifiedName().toString();
                patchClasses.add(className);
            }
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(Inject.class)) {
            if (element instanceof TypeElement) {
                String className = ((TypeElement) element).getQualifiedName().toString();
                injectClasses.add(className);
            }
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(Main.class)) {
            if (element instanceof TypeElement) {
                String className = ((TypeElement) element).getQualifiedName().toString();
                if (main != null) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Multiply main!");
                }
                main = className;
            }
        }

        if (roundEnv.processingOver()) {
            try {
                FileObject resource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "bpatcher-lookup.json");
                try (Writer writer = resource.openWriter()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonObject jsonObject = new JsonObject();

                    jsonObject.add("inject", toJsonArray(injectClasses));
                    jsonObject.add("patch", toJsonArray(patchClasses));
                    if (main != null) {
                        jsonObject.addProperty("main", main);
                    }
                    gson.toJson(jsonObject, writer);
                }
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to write JSON file: " + e.getMessage());
            }
        }
        return true;
    }

    private JsonArray toJsonArray(Collection<String> collection) {
        JsonArray array = new JsonArray(collection.size());
        for (String s : collection) {
            array.add(s);
        }
        return array;
    }
}
