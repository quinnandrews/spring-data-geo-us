package io.github.quinnandrews.spring.data.geo.us.generator.states.model.repository;

import org.locationtech.jts.geom.Coordinate;
import io.github.quinnandrews.spring.data.geo.us.generator.states.model.GeoState;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GeoStateRepository {

    private final Set<GeoState> states = new HashSet<>();

    public GeoStateRepository() {
        init();
    }

    public List<GeoState> findAll() {
        return states.stream().toList();
    }

    public Optional<GeoState> findByCode(final String code) {
        return states.stream().filter(s -> s.getCode().equals(code)).findFirst();
    }

    public Optional<GeoState> findByLocation(final Double latitude, final Double longitude) {
        if (latitude == null || longitude == null) {
            return Optional.empty();
        }
        return findByLocation(new Coordinate(longitude, latitude));
    }

    public Optional<GeoState> findByLocation(final Coordinate coordinate) {
        return states.stream().filter(s -> s.contains(coordinate)).findFirst();
    }

    private void init() {
    }
}
