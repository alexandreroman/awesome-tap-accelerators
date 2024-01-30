package com.vmware.tanzu.tap.accelerators.springboot.hello;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNullElse;

@RestController
class HelloController {
    private final HelloProperties props;

    HelloController(HelloProperties props) {
        this.props = props;
    }

    @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    String hello(@RequestParam(required = false) String name) {
        return String.format(props.greetings(), requireNonNullElse(name, "stranger"));
    }
}
