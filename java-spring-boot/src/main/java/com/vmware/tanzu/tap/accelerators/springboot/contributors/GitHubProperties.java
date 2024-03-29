package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.github")
record GitHubProperties(
        String repo,
        String owner
) {
}
