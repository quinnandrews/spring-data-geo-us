package io.github.quinnandrews.spring.data.geo.us.examples;

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
