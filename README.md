# Spring Data Geo - US

## Description
Generates State and Time Zone data from shape files and provides `@Repository` implementations to query for States and Time Zones with given criteria (e.g. State Code or Lat/Long pairs).  

Data is encapsulated by constant values set in generated Classes (there is a Class for each State and Time Zone), except for shape data, which is contained by properties files because each shape's string representation is too large to set as a string constant.

The data is used by the Repositories when Repository instances are initialized. The data in each State and Time Zone Class is set on instances of GeoState or GeoTimeZone, which are essentially Entities, though not marked as such since they are not JPA Entities. These Entities are what the Repositories query and return.

The constant values in the generated Classes can be accessed directly, which is convenient in cases where you need to use a known State or Time Zone, when writing tests or representing times and dates in particular Time Zones.

All code in the main source of the `lib` module is generated by code in the main source of the `generator` module.

Code can be generated for both States and Time Zones or just one or the other, depending on which `main()` methods are executed. 

Code generation could be performed as part of the build process, but since changes are infrequent, manual generation is more effective.

The `generator` module is not meant for distribution, so it is not packaged into any jar.

## Inception
This project began from a need to find the State and Time Zone containing a given Lat/Long pair, but without getting the data from an external API, a database, or from files loaded into memory every time the Application starts. (It was also an opportunity to explore code generation with Roaster.)

In the given scenario, the Lat/Long pair is User input, so if using an external API, that would mean an additional API call for every request to the initial endpoint to get data that doesn't change very often. 

If querying a database, the situation would be better, but the database would need to be populated, and that may mean using migration scripts, with a product like Flyway, for example. But since the shape data is quite large, creating and maintaining those scripts won't be so easy, and it will slow down the start-up time for local development and integration tests. 

The situation is pretty much the same if loading data from the shape files into memory. It will put more load on Application starts.

As a simple library, the contained data can be plugged into any Application using Spring Framework 6 while avoiding performance hits and additional friction for developers.

## Requirements
### Java 17
https://adoptium.net/temurin/releases/?version=17

## Dependencies
- Spring Framework Core 6.1.2
- Spring Framework Context 6.1.2
- GeoTools 28.5
- Roaster 2.29.0.Final (generator module only)
- SLF4J 2.0.7 (generator module only)
- Apache Commons Lang3 3.13.0 (generator module only)

## Usage
Add this project's artifact to your project as a dependency:

```xml
<dependency>
    <groupId>io.github.quinnandrews</groupId>
    <artifactId>spring-data-geo-us-lib</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
(NOTE: This project's artifact is NOT yet available in Maven Central)

Then, in order to query States or Time Zones, inject `GeoStateRepository` and `GeoTimeZoneRepository` where needed and use the methods they provide: 

```java
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

    public Optional<GeoState> getStateByLocation(final Double latitude, final Double longitude) {
        return geoStateRepository.findByLocation(latitude, longitude);
    }

    public List<GeoState> getAllStates() {
        return geoStateRepository.findAll();
    }

    public Optional<GeoTimeZone> getTimeZoneByZoneId(final ZoneId zoneId) {
        return geoTimeZoneRepository.findByZoneId(zoneId);
    }

    public Optional<GeoTimeZone> getTimeZoneByLocation(final Double latitude, final Double longitude) {
        return geoTimeZoneRepository.findByLocation(latitude, longitude);
    }

    public List<GeoTimeZone> getAllTimeZones() {
        return geoTimeZoneRepository.findAll();
    }
}

```

Alternatively, in cases where you need a particular State Name or Code, or where you need a particular `ZoneId` or `TimeZone`, you can reference the constant values provided:
```java
import io.github.quinnandrews.spring.data.geo.us.states.source.Oregon;
import io.github.quinnandrews.spring.data.geo.us.timezones.source.AmericaLosAngeles;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class CityService {

    public City getDefaultCity() {
        return new City()
                .withName("Portland")
                .withStateCode(Oregon.CODE)
                .withStateName(Oregon.NAME)
                .withLocalDateTime(LocalDateTime.now(AmericaLosAngeles.ZONE_ID))
                .withUtcOffset(AmericaLosAngeles.TIME_ZONE.getOffset(Instant.now().toEpochMilli()));
    }
}
```

## Other Uses
- Populate Dropdowns
- Populate Databases
- Extend the Repositories and add custom queries