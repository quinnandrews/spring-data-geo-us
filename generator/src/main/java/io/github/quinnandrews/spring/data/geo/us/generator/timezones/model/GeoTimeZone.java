package io.github.quinnandrews.spring.data.geo.us.generator.timezones.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Properties;
import java.util.TimeZone;

public class GeoTimeZone {

    private static final String TIME_ZONE_SHAPE_PROPERTIES_FILE = "path/to/file";

    private static final Properties shapeProperties = loadShapeProperties();
    private static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    private static final WKTReader wktReader = new WKTReader(geometryFactory);

    private final ZoneId zoneId;
    private final TimeZone timeZone;
    private final MultiPolygon shape;

    public GeoTimeZone(final ZoneId zoneId,
                       final TimeZone timeZone,
                       final String shapePropertyName) {
        this.zoneId = zoneId;
        this.timeZone = timeZone;
        this.shape = renderShape(getShapeProperty(shapePropertyName));
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public MultiPolygon getShape() {
        return shape;
    }

    public Boolean contains(final Coordinate coordinate) {
        if (coordinate == null) {
            return Boolean.FALSE;
        }
        return getShape().contains(geometryFactory.createPoint(coordinate));
    }

    private static String getShapeProperty(final String shapePropertyName) {
        return shapeProperties.getProperty(shapePropertyName);
    }

    private static MultiPolygon renderShape(final String shape) {
        try {
            return (MultiPolygon) wktReader.read(shape);
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Properties loadShapeProperties() {
        try {
            final var input = new FileInputStream(new ClassPathResource(TIME_ZONE_SHAPE_PROPERTIES_FILE).getFile());
            final var properties = new Properties();
            properties.load(input);
            return properties;
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GeoTimeZone that = (GeoTimeZone) o;
        return new EqualsBuilder().append(zoneId, that.zoneId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(zoneId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("zoneId", zoneId)
                .toString();
    }
}
