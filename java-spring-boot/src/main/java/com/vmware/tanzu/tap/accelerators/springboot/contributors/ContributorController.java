package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class ContributorController {
    private final ContributorService contributorService;

    ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @GetMapping(value = "/contributors", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Contributor> getData() {
        return contributorService.getContributors();
    }

    @GetMapping(value = "/contributors/refresh", produces = MediaType.TEXT_PLAIN_VALUE)
    String refreshData() {
        final int count = contributorService.refresh();
        return String.format("Loaded %d contributor(s)", count);
    }
}
