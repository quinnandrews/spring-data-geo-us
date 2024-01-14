package io.github.quinnandrews.spring.data.geo.us.generator.timezones;

import org.apache.commons.lang3.StringUtils;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.time.ZoneId;

public class GeoTimeZoneFeatures {

    private static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    private static final WKTReader wktReader = new WKTReader(geometryFactory);

    private final ZoneId zoneId;
    private final MultiPolygon shape;
    private final String className;
    private final String shapePropertyName;

    public GeoTimeZoneFeatures(final String id,
                               final String shape) {
        this.zoneId = ZoneId.of(id);
        this.shape = renderShape(shape);
        this.className = renderClassName(id);
        this.shapePropertyName = renderShapePropertyName(id);
    }

    public ZoneId getZoneId() {
        return zoneId;
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
        return StringUtils.replaceEach(name, new String[]{"/","_"}, new String[]{"",""});
    }

    private static String renderShapePropertyName(final String name) {
        return "shape.us.timezone." + renderClassName(name).toLowerCase();
    }

    public static MultiPolygon renderShape(final String shape) {
        try {
            return (MultiPolygon) wktReader.read(shape);
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
