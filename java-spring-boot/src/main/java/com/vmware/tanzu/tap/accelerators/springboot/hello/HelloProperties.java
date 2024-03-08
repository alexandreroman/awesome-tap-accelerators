package com.vmware.tanzu.tap.accelerators.springboot.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.hello")
record HelloProperties(
        String greetings
) {
}
