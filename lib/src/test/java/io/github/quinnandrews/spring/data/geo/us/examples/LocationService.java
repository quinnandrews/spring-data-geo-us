package io.github.quinnandrews.spring.data.geo.us.examples;

import io.github.quinnandrews.spring.data.geo.us.states.GeoState;
import io.github.quinnandrews.spring.data.geo.us.states.repository.GeoStateRepository;
import io.github.quinnandrews.spring.data.geo.us.timezones.GeoTimeZone;
import io.github.quinnandrews.spring.data.geo.us.timezones.repository.GeoTimeZoneRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final GeoStateRepository geoStateRepository;
    private final GeoTimeZoneRepository geoTimeZoneRepository;

    public LocationService(final GeoStateRepository geoStateRepository,
                           final GeoTimeZoneRepository geoTimeZoneRepository) {
        this.geoStateRepository = geoStateRepository;
        this.geoTimeZoneRepository = geoTimeZoneRepository;
    }

    public Optional<GeoState> getStateByCode(final String twoLetterCode) {
        return geoStateRepository.findByCode(twoLetterCode);
    }

    public Optional<GeoState> getStateByLocation(final Double latitude,
                                                 final Double longitude) {
        return geoStateRepository.findByLocation(latitude, longitude);
    }

    public List<GeoState> getAllStates() {
        return geoStateRepository.findAll();
    }

    public Optional<GeoTimeZone> getTimeZoneByZoneId(final ZoneId zoneId) {
        return geoTimeZoneRepository.findByZoneId(zoneId);
    }

    public Optional<GeoTimeZone> getTimeZoneByLocation(final Double latitude,
                                                       final Double longitude) {
        return geoTimeZoneRepository.findByLocation(latitude, longitude);
    }

    public List<GeoTimeZone> getAllTimeZones() {
        return geoTimeZoneRepository.findAll();
    }
}
