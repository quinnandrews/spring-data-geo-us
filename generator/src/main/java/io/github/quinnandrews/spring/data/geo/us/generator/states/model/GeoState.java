package io.github.quinnandrews.spring.data.geo.us.generator.states.model;

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
import java.util.Properties;

public class GeoState {

    private static final String STATE_SHAPE_PROPERTIES_FILE = "path/to/file";

    private static final Properties shapeProperties = loadShapeProperties();
    private static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    private static final WKTReader wktReader = new WKTReader(geometryFactory);

    private final String name;
    private final String code;
    private final MultiPolygon shape;

    public GeoState(final String name,
                    final String code) {
        this.name = name;
        this.code = code;
        this.shape = renderShape(getShapeProperty(name));
    }

    public GeoState(final String name,
                    final String code,
                    final String shapePropertyName) {
        this.name = name;
        this.code = code;
        this.shape = renderShape(getShapeProperty(shapePropertyName));
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
            final var input = new FileInputStream(new ClassPathResource(STATE_SHAPE_PROPERTIES_FILE).getFile());
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
        final var state = (GeoState) o;
        return new EqualsBuilder().append(code, state.code).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", name)
                .append("code", code)
                .toString();
    }
}
