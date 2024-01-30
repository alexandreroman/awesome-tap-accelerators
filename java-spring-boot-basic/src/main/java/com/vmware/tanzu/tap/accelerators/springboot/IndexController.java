package com.vmware.tanzu.tap.accelerators.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {
    @GetMapping("/")
    String index() {
        return "redirect:/info";
    }
}
