package io.github.quinnandrews.spring.data.geo.us.generator.states;

import io.github.quinnandrews.spring.data.geo.us.generator.common.ShapeFileHandler;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import io.github.quinnandrews.spring.data.geo.us.generator.common.FileHandler;
import io.github.quinnandrews.spring.data.geo.us.generator.common.GenerationException;
import io.github.quinnandrews.spring.data.geo.us.generator.states.model.GeoState;

import javax.annotation.processing.Generated;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class GeoStateGenerator {

    private static final String STATE_SHAPE_FILE_PATH
            = "io/github/quinnandrews/spring/data/geo/us/generator/states/source/tl_2020_us_state.shp";

    private static final String STATE_OUTPUT_PACKAGE
            = "io.github.quinnandrews.spring.data.geo.us.states";
    private static final String STATE_SOURCE_OUTPUT_PACKAGE
            = STATE_OUTPUT_PACKAGE + ".source";
    private static final String STATE_REPOSITORY_OUTPUT_PACKAGE
            = STATE_OUTPUT_PACKAGE + ".repository";

    private static final String STATE_OUTPUT_DIRECTORY
            = "lib/src/main/java/io/github/quinnandrews/spring/data/geo/us/states";
    private static final String STATE_SOURCE_OUTPUT_DIRECTORY
            = STATE_OUTPUT_DIRECTORY + "/source";
    private static final String STATE_REPOSITORY_OUTPUT_DIRECTORY
            = STATE_OUTPUT_DIRECTORY + "/repository";

    private static final String STATE_STUB_FILE_DIRECTORY
            = "generator/src/main/java/io/github/quinnandrews/spring/data/geo/us/generator/states/model";
    private static final String STATE_STUB_FILE_PATH
            = STATE_STUB_FILE_DIRECTORY + "/GeoState.java";
    private static final String STATE_REPOSITORY_STUB_FILE_PATH
            = STATE_STUB_FILE_DIRECTORY + "/repository/GeoStateRepository.java";

    private static final String STATE_SHAPE_PROPERTIES_FILE_CLASS_PATH
            = "io/github/quinnandrews/spring/data/geo/us/states/source";
    private static final String STATE_SHAPE_PROPERTIES_FILE_PATH
            = "lib/src/main/resources/" + STATE_SHAPE_PROPERTIES_FILE_CLASS_PATH;
    private static final String STATE_SHAPE_PROPERTIES_FILE_NAME
            = "state-shape.properties";

    public static void generate() {
        final var states = getStates();
        renderStateClass();
        renderStateSourceClasses(states);
        renderStateShapePropertiesFile(states);
        renderStateRepositoryClass(states);
    }

    private static void renderStateClass() {
        final var stubSource = FileHandler.readFile(STATE_STUB_FILE_PATH);
        final var classSource = Roaster.parse(JavaClassSource.class, stubSource);
        classSource.setPackage(STATE_OUTPUT_PACKAGE)
                .addAnnotation(Generated.class)
                .setStringValue(GeoStateGenerationRunner.class.getCanonicalName());
        classSource.getField("STATE_SHAPE_PROPERTIES_FILE")
                .setStringInitializer(STATE_SHAPE_PROPERTIES_FILE_CLASS_PATH + "/" + STATE_SHAPE_PROPERTIES_FILE_NAME);
        FileHandler.writeClassFile(classSource, STATE_OUTPUT_DIRECTORY);
    }

    private static void renderStateSourceClasses(final List<GeoStateFeatures> states) {
        states.forEach(s -> {
            final var classSource = Roaster.create(JavaClassSource.class);
            classSource.setPackage(STATE_SOURCE_OUTPUT_PACKAGE)
                    .setPublic()
                    .setName(s.getClassName())
                    .addAnnotation(Generated.class)
                    .setStringValue(GeoStateGenerationRunner.class.getCanonicalName());
            classSource.addField()
                    .setPublic()
                    .setStatic(Boolean.TRUE)
                    .setFinal(Boolean.TRUE)
                    .setType(String.class)
                    .setName("NAME")
                    .setStringInitializer(s.getName());
            classSource.addField()
                    .setPublic()
                    .setStatic(Boolean.TRUE)
                    .setFinal(Boolean.TRUE)
                    .setType(String.class)
                    .setName("CODE")
                    .setStringInitializer(s.getCode());
            FileHandler.writeClassFile(classSource, STATE_SOURCE_OUTPUT_DIRECTORY);
        });
    }

    private static void renderStateShapePropertiesFile(final List<GeoStateFeatures> states) {
        final var properties = new Properties();
        states.forEach(s -> properties.setProperty(s.getShapePropertyName(), s.getShape().toText()));
        FileHandler.writePropertiesFile(properties, STATE_SHAPE_PROPERTIES_FILE_PATH, STATE_SHAPE_PROPERTIES_FILE_NAME);
    }

    private static void renderStateRepositoryClass(final List<GeoStateFeatures> states) {
        final var stubSource = FileHandler.readFile(STATE_REPOSITORY_STUB_FILE_PATH);
        final var classSource = Roaster.parse(JavaClassSource.class, stubSource);
        classSource.setPackage(STATE_REPOSITORY_OUTPUT_PACKAGE)
                .getImport(GeoState.class)
                .setName(STATE_OUTPUT_PACKAGE + "." + GeoState.class.getSimpleName());
        classSource.addImport(STATE_SOURCE_OUTPUT_PACKAGE + ".*");
        classSource.addAnnotation(Generated.class)
                .setStringValue(GeoStateGenerationRunner.class.getCanonicalName());
        classSource.getMethod("init")
                .setBody(states.stream()
                        .map(s -> "states.add(new GeoState(" +
                                s.getClassName() + ".NAME, " +
                                s.getClassName() + ".CODE, " +
                                "\"" + s.getShapePropertyName() + "\"" +
                                "));")
                        .collect(Collectors.joining()));
        FileHandler.writeClassFile(classSource, STATE_REPOSITORY_OUTPUT_DIRECTORY);
    }

    private static List<GeoStateFeatures> getStates() {
        try {
            return ShapeFileHandler.readShapeFile(STATE_SHAPE_FILE_PATH).stream()
                    .map(f -> new GeoStateFeatures(
                            ShapeFileHandler.getFeatureAttributeString(f, 7),
                            ShapeFileHandler.getFeatureAttributeString(f, 6),
                            ShapeFileHandler.getFeatureAttributeString(f, 0)))
                    .toList();
        } catch (final IOException e) {
            throw new GenerationException(e);
        }
    }
}
