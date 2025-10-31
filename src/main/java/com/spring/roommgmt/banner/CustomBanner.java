package com.spring.roommgmt.banner;

import com.spring.roommgmt.enums.ApplicationBanner;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
@NoArgsConstructor
@Log4j2
public class CustomBanner implements Banner {
    private String bannerText;
    private String buildInfoFilename = "build-info.properties";

    /**
     * Constructor for CustomBanner
     *
     * @param bannerToDisplay The app specific banner to display
     */
    public CustomBanner(ApplicationBanner bannerToDisplay) {
        this.bannerText = bannerToDisplay.getBannerText();
    }

    /**
     * Prints out an app-specific banner + software-version + springboot-version
     *
     * @param environment The environment
     * @param sourceClass The sourceClass
     * @param out         The output PrintStream where the banner + versions are printed
     */
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(bannerText);
        out.printf("Softwareversion:   (%s)%n", getVersionString());
        out.printf("Using Spring Boot: (%s)%n", SpringBootVersion.getVersion());
    }

    private String getVersionString() {
        try {
            String versionProperty = "build.version";
            Properties properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("META-INF/" + buildInfoFilename), StandardCharsets.UTF_8));
            String version = properties.getProperty(versionProperty);
            if (null != version) {
                return version;
            } else {
                log.warn("Softwareversion not detected - No property {} could be found in the file {}!", versionProperty,
                        buildInfoFilename);
                return "";
            }
        } catch (IOException e) {
            log.warn("Software version not detected - The file META-INF/{} could not be found!", buildInfoFilename, e);
            return "";
        }
    }

    /**
     * Setter for buildInfoFilename
     *
     * @param buildInfoFilename The buildInfoFilename
     */
    public void setBuildInfoFilename(String buildInfoFilename) {
        this.buildInfoFilename = buildInfoFilename;
    }

}
