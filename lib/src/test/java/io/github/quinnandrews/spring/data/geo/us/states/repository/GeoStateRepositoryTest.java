package io.github.quinnandrews.spring.data.geo.us.states.repository;

import io.github.quinnandrews.spring.data.geo.us.states.source.Oregon;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class GeoStateRepositoryTest {

    private static final Double PDX_LAT = 45.5152;
    private static final Double PDX_LNG = -122.6784;
    private static final Double YYZ_LAT = 43.6532;
    private static final Double YYZ_LNG = -79.3832;

    private static final Coordinate PDX_CRD = new Coordinate(PDX_LNG, PDX_LAT);
    private static final Coordinate YYZ_CRD = new Coordinate(YYZ_LNG, YYZ_LAT);

    private static final GeoStateRepository repository = new GeoStateRepository();

    @Test
    void getStates() {
        assertEquals(56, repository.findAll().size());
    }

    @Test
    void findStateWithCode() {
        final var optional = repository.findByCode(Oregon.CODE);
        assertTrue(optional.isPresent());
        final var state = optional.get();
        assertEquals(Oregon.NAME, state.getName());
        assertEquals("Oregon", state.getName());
        assertEquals(Oregon.CODE, state.getCode());
        assertEquals("OR", state.getCode());
        assertNotNull(state.getShape());
        assertFalse(state.getShape().isEmpty());
        assertTrue(state.getShape().isValid());
    }

    @Test
    void findStateWithCode_notPresentIfNoMatch() {
        assertFalse(repository.findByCode("K9").isPresent());
    }

    @Test
    void findStateWithLocation_withLatLong() {
        final var optional = repository.findByLocation(PDX_LAT, PDX_LNG);
        assertTrue(optional.isPresent());
        final var state = optional.get();
        assertEquals(Oregon.NAME, state.getName());
        assertEquals(Oregon.CODE, state.getCode());
    }

    @Test
    void findStateWithLocation_withLatLong_notPresentIfNoMatch() {
        assertFalse(repository.findByLocation(YYZ_LAT, YYZ_LNG).isPresent());
    }

    @Test
    void findStateWithLocation_withLatLong_notPresentIfEitherArgumentIsNull() {
        assertFalse(repository.findByLocation(PDX_LAT, null).isPresent());
        assertFalse(repository.findByLocation(null, PDX_LNG).isPresent());
        assertFalse(repository.findByLocation(null, null).isPresent());
    }

    @Test
    void findStateWithLocation_withCoordinate() {
        final var optional = repository.findByLocation(PDX_CRD);
        assertTrue(optional.isPresent());
        final var state = optional.get();
        assertEquals(Oregon.NAME, state.getName());
        assertEquals(Oregon.CODE, state.getCode());
    }

    @Test
    void findStateWithLocation_withCoordinate_notPresentIfNoMatch() {
        assertFalse(repository.findByLocation(YYZ_CRD).isPresent());
    }

    @Test
    void findStateWithLocation_withCoordinate_notPresentIfArgumentIsNull() {
        assertFalse(repository.findByLocation(null).isPresent());
    }
}