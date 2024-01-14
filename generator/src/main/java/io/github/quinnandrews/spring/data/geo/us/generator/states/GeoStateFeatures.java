package io.github.quinnandrews.spring.data.geo.us.generator.states;

import org.apache.commons.lang3.StringUtils;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GeoStateFeatures {

    private static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    private static final WKTReader wktReader = new WKTReader(geometryFactory);

    private final String name;
    private final String code;
    private final MultiPolygon shape;
    private final String className;
    private final String shapePropertyName;

    public GeoStateFeatures(final String name,
                            final String code,
                            final String shape) {
        this.name = name;
        this.code = code;
        this.shape = renderShape(shape);
        this.className = renderClassName(name);
        this.shapePropertyName = renderShapePropertyName(name);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public MultiPolygon getShape() {
        return shape;
    }

    public String getClassName() {
        return className;
    }

    public String getShapePropertyName() {
        return shapePropertyName;
    }

    private static String renderClassName(final String name) {
        return Arrays.stream(StringUtils.split(name))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining());
    }

    private static String renderShapePropertyName(final String name) {
        return "shape.us.state." + renderClassName(name).toLowerCase();
    }

    private static MultiPolygon renderShape(final String shape) {
        try {
            return (MultiPolygon) wktReader.read(shape);
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
