package com.vmware.tanzu.tap.accelerators.springboot.info;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration(proxyBeanMethods = false)
class InfoMetrics {
    @Bean
    MeterBinder appInfo(Info info) {
        final var tags = new ArrayList<Tag>(8);
        if (info.groupId() != null) {
            tags.add(Tag.of("groupId", info.groupId()));
        }
        if (info.artifactId() != null) {
            tags.add(Tag.of("artifactId", info.artifactId()));
        }
        if (info.version() != null) {
            tags.add(Tag.of("version", info.version()));
        }
        tags.add(Tag.of("springBootProfiles", info.springBootProfiles()));
        tags.add(Tag.of("springBootVersion", info.springBootVersion()));
        tags.add(Tag.of("javaVersion", info.javaVersion()));

        return registry -> Gauge.builder("app.info", () -> 1)
                .baseUnit("instance")
                .description("App info")
                .tags(tags)
                .register(registry);
    }
}
