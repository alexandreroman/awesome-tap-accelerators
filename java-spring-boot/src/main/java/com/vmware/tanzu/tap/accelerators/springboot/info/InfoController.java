package com.vmware.tanzu.tap.accelerators.springboot.info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class InfoController {
    private final boolean runningInKubernetes;
    private final Info info;

    InfoController(Info info, @Value("${app.info.running-in-kubernetes}") boolean runningInKubernetes) {
        this.runningInKubernetes = runningInKubernetes;
        this.info = info;
    }

    @GetMapping("/info")
    String index(Model model) {
        model.addAttribute("runningInKubernetes", runningInKubernetes);
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
