package com.vmware.tanzu.tap.accelerators.springboot.hello;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HelloApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "app.hello.greetings=Test for %s!")
class HelloControllerTest {
    @Autowired
    private TestRestTemplate rest;

    @Test
    void helloStranger() {
        final var resp = rest.getForEntity("/hello", String.class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(resp.getBody()).isEqualTo("Test for stranger!");
    }

    @Test
    void helloJohn() {
        final var resp = rest.getForEntity("/hello?name=John", String.class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(resp.getBody()).isEqualTo("Test for John!");
    }
}
