package io.github.quinnandrews.spring.data.geo.us.generator.timezones.model.repository;

import org.locationtech.jts.geom.Coordinate;
import io.github.quinnandrews.spring.data.geo.us.generator.timezones.model.GeoTimeZone;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GeoTimeZoneRepository {

    private final Set<GeoTimeZone> timeZones = new HashSet<>();

    public GeoTimeZoneRepository() {
        init();
    }

    public List<GeoTimeZone> findAll() {
        return timeZones.stream().toList();
    }

    public Optional<GeoTimeZone> findByZoneId(final ZoneId zoneId) {
        return timeZones.stream().filter(tz -> tz.getZoneId().equals(zoneId)).findFirst();
    }

    public Optional<GeoTimeZone> findByLocation(final Double latitude, final Double longitude) {
        if (latitude == null || longitude == null) {
            return Optional.empty();
        }
        return findByLocation(new Coordinate(longitude, latitude));
    }

    public Optional<GeoTimeZone> findByLocation(final Coordinate coordinate) {
        return timeZones.stream().filter(tz -> tz.contains(coordinate)).findFirst();
    }

    private void init() {
    }
}
