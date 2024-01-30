package com.vmware.tanzu.tap.accelerators.springboot.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration(proxyBeanMethods = false)
class InfoConfig {
    @Nullable
    private static String getDataSourceMetadata(@Nullable DataSource ds) {
        if (ds == null) {
            return null;
        }
        try (final Connection conn = ds.getConnection()) {
            final var meta = conn.getMetaData();
            return meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion();
        } catch (SQLException e) {
            return null;
        }
    }

    @Bean
    Info info(Environment env,
              @Autowired(required = false) @Nullable BuildProperties buildProperties,
              @Autowired(required = false) @Nullable DataSource dataSource) throws UnknownHostException {
        final var springBootProfiles = env.getActiveProfiles().length == 0
                ? "default"
                : String.join(",", env.getActiveProfiles());

        return new Info(
                buildProperties != null ? buildProperties.getGroup() : null,
                buildProperties != null ? buildProperties.getArtifact() : null,
                buildProperties != null ? buildProperties.getVersion() : null,
                System.getProperty("java.version"),
                SpringBootVersion.getVersion(),
                springBootProfiles,
                InetAddress.getLocalHost().getHostAddress(),
                CloudPlatform.KUBERNETES.isActive(env),
                getDataSourceMetadata(dataSource)
        );
    }
}
