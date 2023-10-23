package com.vmware.tanzu.tap.accelerators.springboot.info;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = InfoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InfoControllerTests {
    @Autowired
    private TestRestTemplate rest;

    @Test
    void info() {
        final var resp = rest.getForEntity("/info", String.class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void infoJson() {
        final var resp = rest.getForEntity("/info", Info.class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
        final var info = resp.getBody();
        assertThat(info).isNotNull();
        assertThat(info.groupId()).isNotNull();
        assertThat(info.artifactId()).isNotNull();
    }
}
