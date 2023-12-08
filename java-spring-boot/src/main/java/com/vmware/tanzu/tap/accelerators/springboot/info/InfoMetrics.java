package com.vmware.tanzu.tap.accelerators.springboot.info;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class InfoMetrics {
    @Bean
    MeterBinder appInfo(Info info) {
        return registry -> Gauge.builder("app.info", () -> 1)
                .baseUnit("instance")
                .description("App info")
                .tags("groupId", info.groupId(),
                        "artifactId", info.artifactId(),
                        "version", info.version(),
                        "springBootProfiles", info.springBootProfiles(),
                        "springBootVersion", info.springBootVersion(),
                        "javaVersion", info.javaVersion())
                .register(registry);
    }
}
