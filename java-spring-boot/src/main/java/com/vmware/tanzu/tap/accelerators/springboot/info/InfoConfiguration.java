package com.vmware.tanzu.tap.accelerators.springboot.info;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration(proxyBeanMethods = false)
class InfoConfiguration {
    @Bean
    Info info(BuildProperties buildProperties, Environment env) throws UnknownHostException {
        final var springBootProfiles = env.getActiveProfiles().length == 0
                ? "default"
                : String.join(",", env.getActiveProfiles());

        return new Info(
                buildProperties.getGroup(),
                buildProperties.getArtifact(),
                buildProperties.getVersion(),
                System.getProperty("java.version"),
                SpringBootVersion.getVersion(),
                springBootProfiles,
                InetAddress.getLocalHost().getHostAddress()
        );
    }
}
