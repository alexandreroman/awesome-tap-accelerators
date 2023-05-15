package com.vmware.tanzu.tap.accelerators.springbootfrontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {
    private final Info info;

    IndexController(Info info) {
        this.info = info;
    }

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("app", info);
        return "index";
    }
}
