package io.github.quinnandrews.spring.data.geo.us.states.repository;

import org.locationtech.jts.geom.Coordinate;
import io.github.quinnandrews.spring.data.geo.us.states.GeoState;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import io.github.quinnandrews.spring.data.geo.us.states.source.*;
import javax.annotation.processing.Generated;

@Repository
@Generated("io.github.quinnandrews.spring.data.geo.us.generator.states.GeoStateGenerationRunner")
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
		states.add(new GeoState(WestVirginia.NAME, WestVirginia.CODE, "shape.us.state.westvirginia"));
		states.add(new GeoState(Florida.NAME, Florida.CODE, "shape.us.state.florida"));
		states.add(new GeoState(Illinois.NAME, Illinois.CODE, "shape.us.state.illinois"));
		states.add(new GeoState(Minnesota.NAME, Minnesota.CODE, "shape.us.state.minnesota"));
		states.add(new GeoState(Maryland.NAME, Maryland.CODE, "shape.us.state.maryland"));
		states.add(new GeoState(RhodeIsland.NAME, RhodeIsland.CODE, "shape.us.state.rhodeisland"));
		states.add(new GeoState(Idaho.NAME, Idaho.CODE, "shape.us.state.idaho"));
		states.add(new GeoState(NewHampshire.NAME, NewHampshire.CODE, "shape.us.state.newhampshire"));
		states.add(new GeoState(NorthCarolina.NAME, NorthCarolina.CODE, "shape.us.state.northcarolina"));
		states.add(new GeoState(Vermont.NAME, Vermont.CODE, "shape.us.state.vermont"));
		states.add(new GeoState(Connecticut.NAME, Connecticut.CODE, "shape.us.state.connecticut"));
		states.add(new GeoState(Delaware.NAME, Delaware.CODE, "shape.us.state.delaware"));
		states.add(new GeoState(NewMexico.NAME, NewMexico.CODE, "shape.us.state.newmexico"));
		states.add(new GeoState(California.NAME, California.CODE, "shape.us.state.california"));
		states.add(new GeoState(NewJersey.NAME, NewJersey.CODE, "shape.us.state.newjersey"));
		states.add(new GeoState(Wisconsin.NAME, Wisconsin.CODE, "shape.us.state.wisconsin"));
		states.add(new GeoState(Oregon.NAME, Oregon.CODE, "shape.us.state.oregon"));
		states.add(new GeoState(Nebraska.NAME, Nebraska.CODE, "shape.us.state.nebraska"));
		states.add(new GeoState(Pennsylvania.NAME, Pennsylvania.CODE, "shape.us.state.pennsylvania"));
		states.add(new GeoState(Washington.NAME, Washington.CODE, "shape.us.state.washington"));
		states.add(new GeoState(Louisiana.NAME, Louisiana.CODE, "shape.us.state.louisiana"));
		states.add(new GeoState(Georgia.NAME, Georgia.CODE, "shape.us.state.georgia"));
		states.add(new GeoState(Alabama.NAME, Alabama.CODE, "shape.us.state.alabama"));
		states.add(new GeoState(Utah.NAME, Utah.CODE, "shape.us.state.utah"));
		states.add(new GeoState(Ohio.NAME, Ohio.CODE, "shape.us.state.ohio"));
		states.add(new GeoState(Texas.NAME, Texas.CODE, "shape.us.state.texas"));
		states.add(new GeoState(Colorado.NAME, Colorado.CODE, "shape.us.state.colorado"));
		states.add(new GeoState(SouthCarolina.NAME, SouthCarolina.CODE, "shape.us.state.southcarolina"));
		states.add(new GeoState(Oklahoma.NAME, Oklahoma.CODE, "shape.us.state.oklahoma"));
		states.add(new GeoState(Tennessee.NAME, Tennessee.CODE, "shape.us.state.tennessee"));
		states.add(new GeoState(Wyoming.NAME, Wyoming.CODE, "shape.us.state.wyoming"));
		states.add(new GeoState(Hawaii.NAME, Hawaii.CODE, "shape.us.state.hawaii"));
		states.add(new GeoState(NorthDakota.NAME, NorthDakota.CODE, "shape.us.state.northdakota"));
		states.add(new GeoState(Kentucky.NAME, Kentucky.CODE, "shape.us.state.kentucky"));
		states.add(new GeoState(UnitedStatesVirginIslands.NAME, UnitedStatesVirginIslands.CODE,
				"shape.us.state.unitedstatesvirginislands"));
		states.add(
				new GeoState(CommonwealthOfTheNorthernMarianaIslands.NAME, CommonwealthOfTheNorthernMarianaIslands.CODE,
						"shape.us.state.commonwealthofthenorthernmarianaislands"));
		states.add(new GeoState(Guam.NAME, Guam.CODE, "shape.us.state.guam"));
		states.add(new GeoState(Maine.NAME, Maine.CODE, "shape.us.state.maine"));
		states.add(new GeoState(NewYork.NAME, NewYork.CODE, "shape.us.state.newyork"));
		states.add(new GeoState(Nevada.NAME, Nevada.CODE, "shape.us.state.nevada"));
		states.add(new GeoState(Alaska.NAME, Alaska.CODE, "shape.us.state.alaska"));
		states.add(new GeoState(AmericanSamoa.NAME, AmericanSamoa.CODE, "shape.us.state.americansamoa"));
		states.add(new GeoState(Michigan.NAME, Michigan.CODE, "shape.us.state.michigan"));
		states.add(new GeoState(Arkansas.NAME, Arkansas.CODE, "shape.us.state.arkansas"));
		states.add(new GeoState(Mississippi.NAME, Mississippi.CODE, "shape.us.state.mississippi"));
		states.add(new GeoState(Missouri.NAME, Missouri.CODE, "shape.us.state.missouri"));
		states.add(new GeoState(Montana.NAME, Montana.CODE, "shape.us.state.montana"));
		states.add(new GeoState(Kansas.NAME, Kansas.CODE, "shape.us.state.kansas"));
		states.add(new GeoState(Indiana.NAME, Indiana.CODE, "shape.us.state.indiana"));
		states.add(new GeoState(PuertoRico.NAME, PuertoRico.CODE, "shape.us.state.puertorico"));
		states.add(new GeoState(SouthDakota.NAME, SouthDakota.CODE, "shape.us.state.southdakota"));
		states.add(new GeoState(Massachusetts.NAME, Massachusetts.CODE, "shape.us.state.massachusetts"));
		states.add(new GeoState(Virginia.NAME, Virginia.CODE, "shape.us.state.virginia"));
		states.add(new GeoState(DistrictOfColumbia.NAME, DistrictOfColumbia.CODE, "shape.us.state.districtofcolumbia"));
		states.add(new GeoState(Iowa.NAME, Iowa.CODE, "shape.us.state.iowa"));
		states.add(new GeoState(Arizona.NAME, Arizona.CODE, "shape.us.state.arizona"));
	}
}
