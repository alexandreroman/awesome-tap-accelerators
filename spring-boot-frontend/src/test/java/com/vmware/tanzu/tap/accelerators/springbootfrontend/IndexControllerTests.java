package com.vmware.tanzu.tap.accelerators.springbootfrontend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTests {
    @Autowired
    private TestRestTemplate rest;

    @Test
    void contextLoads() {
        final var resp = rest.getForEntity("/", String.class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
