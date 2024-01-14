package io.github.quinnandrews.spring.data.geo.us.generator.common;

import org.geotools.data.DataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShapeFileHandler {

    private static final Logger logger = LoggerFactory.getLogger(ShapeFileHandler.class);

    public static List<SimpleFeature> readShapeFile(final String fileClassPath) throws IOException {
        final var data = new HashMap<String, Object>();
        data.put(ShapefileDataStoreFactory.URLP.key, new ClassPathResource(fileClassPath).getURL());
        final var dataStore = DataStoreFinder.getDataStore(data);
        final var featureSource = dataStore.getFeatureSource(dataStore.getTypeNames()[0]);
        final var features = new ArrayList<SimpleFeature>();
        try {
            final var featureCollection = featureSource.getFeatures(Filter.INCLUDE);
            try (final var featureIterator = featureCollection.features()) {
                while (featureIterator.hasNext()) {
                    final var feature = featureIterator.next();
                    logFeatureAttributes(feature);
                    features.add(feature);
                }
            }
            return features;
        } finally {
            dataStore.dispose();
        }
    }

    public static String getFeatureAttributeString(final SimpleFeature feature,
                                                   final Integer index) {
        return feature.getAttribute(index).toString();
    }

    public static void logFeatureAttributes(final SimpleFeature feature) {
        if (logger.isDebugEnabled()) {
            final var i = new AtomicInteger();
            feature.getAttributes()
                    .forEach(a -> {
                        logger.debug("FEATURE INDEX: {} FEATURE VALUE: {}", i, a.toString());
                        i.getAndIncrement();
                    });
        }
    }
}
