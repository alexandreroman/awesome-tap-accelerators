package com.vmware.tanzu.tap.accelerators.springboot.info;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class InfoController {
    private final Info info;

    InfoController(Info info) {
        this.info = info;
    }

    @GetMapping("/info")
    String index() {
        return "index";
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Info infoJson() {
        return info;
    }

    @ModelAttribute("app")
    Info info() {
        return info;
    }
}
