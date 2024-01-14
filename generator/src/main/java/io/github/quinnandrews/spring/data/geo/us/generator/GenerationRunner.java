package io.github.quinnandrews.spring.data.geo.us.generator;

import io.github.quinnandrews.spring.data.geo.us.generator.states.GeoStateGenerationRunner;
import io.github.quinnandrews.spring.data.geo.us.generator.timezones.GeoTimeZoneGenerationRunner;

public class GenerationRunner {

    public static void main(final String[] args) {
        GeoStateGenerationRunner.main(args);
        GeoTimeZoneGenerationRunner.main(args);
    }
}
