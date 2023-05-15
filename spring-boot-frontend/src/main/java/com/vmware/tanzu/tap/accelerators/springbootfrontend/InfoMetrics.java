package com.vmware.tanzu.tap.accelerators.springbootfrontend;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
class InfoMetrics {
    private final Info info;
    private final MeterRegistry reg;

    InfoMetrics(Info info, MeterRegistry reg) {
        this.info = info;
        this.reg = reg;
        init();
    }

    private void init() {
        Gauge.builder("app.info", () -> 1)
                .baseUnit("instance")
                .description("App info")
                .tags("groupId", info.getGroupId(),
                        "artifactId", info.getArtifactId(),
                        "version", info.getVersion(),
                        "springBootProfiles", info.getSpringBootProfiles(),
                        "springBootVersion", info.getSpringBootVersion(),
                        "javaVersion", info.getJavaVersion())
                .register(reg);
    }
}
