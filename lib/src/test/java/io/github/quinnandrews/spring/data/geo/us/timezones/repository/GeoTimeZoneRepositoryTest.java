package io.github.quinnandrews.spring.data.geo.us.timezones.repository;

import io.github.quinnandrews.spring.data.geo.us.timezones.source.AmericaLosAngeles;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class GeoTimeZoneRepositoryTest {

    private static final Double PDX_LAT = 45.5152;
    private static final Double PDX_LNG = -122.6784;
    private static final Double YYZ_LAT = 43.6532;
    private static final Double YYZ_LNG = -79.3832;

    private static final Coordinate PDX_CRD = new Coordinate(PDX_LNG, PDX_LAT);
    private static final Coordinate YYZ_CRD = new Coordinate(YYZ_LNG, YYZ_LAT);

    private static final GeoTimeZoneRepository repository = new GeoTimeZoneRepository();

    @Test
    void getTimeZones() {
        assertEquals(9, repository.findAll().size());
    }

    @Test
    void findTimeZoneWithZoneId() {
        final var optional = repository.findByZoneId(AmericaLosAngeles.ZONE_ID);
        assertTrue(optional.isPresent());
        final var timeZone = optional.get();
        assertEquals(AmericaLosAngeles.ZONE_ID, timeZone.getZoneId());
        assertEquals(ZoneId.of("America/Los_Angeles"), timeZone.getZoneId());
        assertEquals(AmericaLosAngeles.TIME_ZONE, timeZone.getTimeZone());
        assertEquals(AmericaLosAngeles.ZONE_ID, timeZone.getTimeZone().toZoneId());
        assertNotNull(timeZone.getShape());
        assertFalse(timeZone.getShape().isEmpty());
        assertTrue(timeZone.getShape().isValid());
    }

    @Test
    void findTimeZoneWithZoneId_notPresentIfNoMatch() {
        assertFalse(repository.findByZoneId(ZoneId.of("Europe/Berlin")).isPresent());
    }

    @Test
    void findTimeZoneeWithLocation_withLatLong() {
        final var optional = repository.findByLocation(PDX_LAT, PDX_LNG);
        assertTrue(optional.isPresent());
        final var timeZone = optional.get();
        assertEquals(AmericaLosAngeles.ZONE_ID, timeZone.getZoneId());
    }

    @Test
    void findTimeZoneWithLocation_withLatLong_notPresentIfNoMatch() {
        assertFalse(repository.findByLocation(YYZ_LAT, YYZ_LNG).isPresent());
    }

    @Test
    void findTimeZoneWithLocation_withLatLong_notPresentIfEitherArgumentIsNull() {
        assertFalse(repository.findByLocation(PDX_LAT, null).isPresent());
        assertFalse(repository.findByLocation(null, PDX_LNG).isPresent());
        assertFalse(repository.findByLocation(null, null).isPresent());
    }

    @Test
    void findTimeZoneWithLocation_withCoordinate() {
        final var optional = repository.findByLocation(PDX_CRD);
        assertTrue(optional.isPresent());
        final var timeZone = optional.get();
        assertEquals(AmericaLosAngeles.ZONE_ID, timeZone.getZoneId());
    }

    @Test
    void findTimeZoneWithLocation_withCoordinate_notPresentIfNoMatch() {
        assertFalse(repository.findByLocation(YYZ_CRD).isPresent());
    }

    @Test
    void findTimeZoneWithLocation_withCoordinate_notPresentIfArgumentIsNull() {
        assertFalse(repository.findByLocation(null).isPresent());
    }
}