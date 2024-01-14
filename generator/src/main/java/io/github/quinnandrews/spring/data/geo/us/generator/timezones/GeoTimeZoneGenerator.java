package io.github.quinnandrews.spring.data.geo.us.generator.timezones;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import io.github.quinnandrews.spring.data.geo.us.generator.common.FileHandler;
import io.github.quinnandrews.spring.data.geo.us.generator.common.GenerationException;
import io.github.quinnandrews.spring.data.geo.us.generator.common.ShapeFileHandler;
import io.github.quinnandrews.spring.data.geo.us.generator.states.GeoStateGenerationRunner;
import io.github.quinnandrews.spring.data.geo.us.generator.timezones.model.GeoTimeZone;

import javax.annotation.processing.Generated;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class GeoTimeZoneGenerator {

    private static final String TIME_ZONE_SHAPE_FILE_PATH
            = "io/github/quinnandrews/spring/data/geo/us/generator/timezones/source/combined-shapefile.shp";

    private static final String TIME_ZONE_OUTPUT_PACKAGE
            = "io.github.quinnandrews.spring.data.geo.us.timezones";
    private static final String TIME_ZONE_SOURCE_OUTPUT_PACKAGE
            = TIME_ZONE_OUTPUT_PACKAGE + ".source";
    private static final String TIME_ZONE_REPOSITORY_OUTPUT_PACKAGE
            = TIME_ZONE_OUTPUT_PACKAGE + ".repository";

    private static final String TIME_ZONE_OUTPUT_DIRECTORY
            = "lib/src/main/java/io/github/quinnandrews/spring/data/geo/us/timezones";
    private static final String TIME_ZONE_SOURCE_OUTPUT_DIRECTORY
            = TIME_ZONE_OUTPUT_DIRECTORY + "/source";
    private static final String TIME_ZONE_REPOSITORY_OUTPUT_DIRECTORY
            = TIME_ZONE_OUTPUT_DIRECTORY + "/repository";

    private static final String TIME_ZONE_STUB_FILE_DIRECTORY
            = "generator/src/main/java/io/github/quinnandrews/spring/data/geo/us/generator/timezones/model";
    private static final String TIME_ZONE_STUB_FILE_PATH
            = TIME_ZONE_STUB_FILE_DIRECTORY + "/GeoTimeZone.java";
    private static final String TIME_ZONE_REPOSITORY_STUB_FILE_PATH
            = TIME_ZONE_STUB_FILE_DIRECTORY + "/repository/GeoTimeZoneRepository.java";

    private static final String TIME_ZONE_SHAPE_PROPERTIES_FILE_CLASS_PATH
            = "io/github/quinnandrews/spring/data/geo/us/timezones/source";
    private static final String TIME_ZONE_SHAPE_PROPERTIES_FILE_PATH
            = "lib/src/main/resources/" + TIME_ZONE_SHAPE_PROPERTIES_FILE_CLASS_PATH;
    private static final String TIME_ZONE_SHAPE_PROPERTIES_FILE_NAME
            = "timezone-shape.properties";

    private static final Set<String> usZones = Set.of(
            "America/Puerto_Rico",
            "America/New_York",
            "America/Chicago",
            "America/Denver",
            "America/Los_Angeles",
            "America/Anchorage",
            "America/Adak",
            "Pacific/Pago_Pago",
            "Pacific/Guam"
    );

    public static void generate() {
        final var timeZones = getTimeZones();
        renderTimeZoneClass();
        renderTimeZoneSourceClasses(timeZones);
        renderTimeZoneShapePropertiesFile(timeZones);
        renderTimeZoneRepositoryClass(timeZones);
    }

    private static void renderTimeZoneClass() {
        final var stubSource = FileHandler.readFile(TIME_ZONE_STUB_FILE_PATH);
        final var classSource = Roaster.parse(JavaClassSource.class, stubSource);
        classSource.setPackage(TIME_ZONE_OUTPUT_PACKAGE)
                .addAnnotation(Generated.class)
                .setStringValue(GeoTimeZoneGenerator.class.getCanonicalName());
        classSource.getField("TIME_ZONE_SHAPE_PROPERTIES_FILE")
                .setStringInitializer(TIME_ZONE_SHAPE_PROPERTIES_FILE_CLASS_PATH + "/" + TIME_ZONE_SHAPE_PROPERTIES_FILE_NAME);
        FileHandler.writeClassFile(classSource, TIME_ZONE_OUTPUT_DIRECTORY);
    }

    private static void renderTimeZoneSourceClasses(final List<GeoTimeZoneFeatures> timeZones) {
        timeZones.forEach(tz -> {
            final var classSource = Roaster.create(JavaClassSource.class);
            classSource.setPackage(TIME_ZONE_SOURCE_OUTPUT_PACKAGE)
                    .setPublic()
                    .setName(tz.getClassName())
                    .addAnnotation(Generated.class)
                    .setStringValue(GeoTimeZoneGenerationRunner.class.getCanonicalName());
            classSource.addImport(ZoneId.class);
            classSource.addImport(TimeZone.class);
            classSource.addField()
                    .setPublic()
                    .setStatic(Boolean.TRUE)
                    .setFinal(Boolean.TRUE)
                    .setType(ZoneId.class)
                    .setName("ZONE_ID")
                    .setLiteralInitializer("ZoneId.of(\"" + tz.getZoneId().toString() + "\");");
            classSource.addField()
                    .setPublic()
                    .setStatic(Boolean.TRUE)
                    .setFinal(Boolean.TRUE)
                    .setType(TimeZone.class)
                    .setName("TIME_ZONE")
                    .setLiteralInitializer("TimeZone.getTimeZone(ZONE_ID);");
            FileHandler.writeClassFile(classSource, TIME_ZONE_SOURCE_OUTPUT_DIRECTORY);
        });
    }

    private static void renderTimeZoneShapePropertiesFile(final List<GeoTimeZoneFeatures> timeZones) {
        final var properties = new Properties();
        timeZones.forEach(tz -> properties.setProperty(tz.getShapePropertyName(), tz.getShape().toText()));
        FileHandler.writePropertiesFile(properties, TIME_ZONE_SHAPE_PROPERTIES_FILE_PATH, TIME_ZONE_SHAPE_PROPERTIES_FILE_NAME);
    }

    private static void renderTimeZoneRepositoryClass(final List<GeoTimeZoneFeatures> timeZones) {
        final var stubSource = FileHandler.readFile(TIME_ZONE_REPOSITORY_STUB_FILE_PATH);
        final var classSource = Roaster.parse(JavaClassSource.class, stubSource);
        classSource.setPackage(TIME_ZONE_REPOSITORY_OUTPUT_PACKAGE)
                .getImport(GeoTimeZone.class)
                .setName(TIME_ZONE_OUTPUT_PACKAGE + "." + GeoTimeZone.class.getSimpleName());
        classSource.addImport(TIME_ZONE_SOURCE_OUTPUT_PACKAGE + ".*");
        classSource.addAnnotation(Generated.class)
                .setStringValue(GeoStateGenerationRunner.class.getCanonicalName());
        classSource.getMethod("init")
                .setBody(timeZones.stream()
                        .map(tz -> "timeZones.add(new GeoTimeZone(" +
                                tz.getClassName() + ".ZONE_ID, " +
                                tz.getClassName() + ".TIME_ZONE, " +
                                "\"" + tz.getShapePropertyName() + "\"" +
                                "));")
                        .collect(Collectors.joining()));
        FileHandler.writeClassFile(classSource, TIME_ZONE_REPOSITORY_OUTPUT_DIRECTORY);
    }

    private static List<GeoTimeZoneFeatures> getTimeZones() {
        try {
            return ShapeFileHandler.readShapeFile(TIME_ZONE_SHAPE_FILE_PATH).stream()
                    .filter(f -> usZones.contains(ShapeFileHandler.getFeatureAttributeString(f, 1)))
                    .map(f -> new GeoTimeZoneFeatures(
                            ShapeFileHandler.getFeatureAttributeString(f, 1),
                            ShapeFileHandler.getFeatureAttributeString(f, 0)))
                    .toList();
        } catch (final IOException e) {
            throw new GenerationException(e);
        }
    }
}
