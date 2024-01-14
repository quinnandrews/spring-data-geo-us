package io.github.quinnandrews.spring.data.geo.us.timezones.repository;

import org.locationtech.jts.geom.Coordinate;
import io.github.quinnandrews.spring.data.geo.us.timezones.GeoTimeZone;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import io.github.quinnandrews.spring.data.geo.us.timezones.source.*;
import javax.annotation.processing.Generated;

@Repository
@Generated("io.github.quinnandrews.spring.data.geo.us.generator.states.GeoStateGenerationRunner")
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
		timeZones.add(new GeoTimeZone(AmericaAdak.ZONE_ID, AmericaAdak.TIME_ZONE, "shape.us.timezone.americaadak"));
		timeZones.add(new GeoTimeZone(AmericaAnchorage.ZONE_ID, AmericaAnchorage.TIME_ZONE,
				"shape.us.timezone.americaanchorage"));
		timeZones.add(
				new GeoTimeZone(AmericaChicago.ZONE_ID, AmericaChicago.TIME_ZONE, "shape.us.timezone.americachicago"));
		timeZones.add(
				new GeoTimeZone(AmericaDenver.ZONE_ID, AmericaDenver.TIME_ZONE, "shape.us.timezone.americadenver"));
		timeZones.add(new GeoTimeZone(AmericaLosAngeles.ZONE_ID, AmericaLosAngeles.TIME_ZONE,
				"shape.us.timezone.americalosangeles"));
		timeZones.add(
				new GeoTimeZone(AmericaNewYork.ZONE_ID, AmericaNewYork.TIME_ZONE, "shape.us.timezone.americanewyork"));
		timeZones.add(new GeoTimeZone(AmericaPuertoRico.ZONE_ID, AmericaPuertoRico.TIME_ZONE,
				"shape.us.timezone.americapuertorico"));
		timeZones.add(new GeoTimeZone(PacificGuam.ZONE_ID, PacificGuam.TIME_ZONE, "shape.us.timezone.pacificguam"));
		timeZones.add(new GeoTimeZone(PacificPagoPago.ZONE_ID, PacificPagoPago.TIME_ZONE,
				"shape.us.timezone.pacificpagopago"));
	}
}
