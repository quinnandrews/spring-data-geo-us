package io.github.quinnandrews.spring.data.geo.us.generator.common;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class FileHandler {

    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);

    public static FileInputStream readFile(final String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (final FileNotFoundException e) {
            throw new GenerationException(e);
        }
    }

    public static void writeClassFile(final JavaClassSource javaClass,
                                      final String directoryPath) {
        try {
            final var directory = new File(directoryPath);
            if (directory.mkdirs()) {
                logger.debug("\n\nDirectory created at {}", directoryPath);
            }
            final var writer = new FileWriter(directory.getAbsolutePath() + "/" +  javaClass.getName() + ".java");
            writer.write(javaClass.toString());
            writer.close();
            logger.debug("\n\nFile written to {}\n\n{}", directory.getAbsolutePath(), javaClass);
        } catch (final IOException e) {
            throw new GenerationException(e);
        }
    }

    public static void writePropertiesFile(final Properties properties,
                                           final String directoryPath,
                                           final String fileName) {
        try {
            final var directory = new File(directoryPath);
            if (directory.mkdirs()) {
                logger.debug("\n\nDirectory created at {}", directoryPath);
            }
            final var filePath = directory.getAbsolutePath() + "/" + fileName;
            properties.store(new FileWriter(filePath), null);
            logger.debug("\n\nFile written to {}", filePath);
        } catch (final IOException e) {
            throw new GenerationException(e);
        }
    }
}
