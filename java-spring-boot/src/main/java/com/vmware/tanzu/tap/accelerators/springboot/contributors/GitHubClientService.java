package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

interface GitHubClientService {
    @GetExchange("/repos/{owner}/{repo}/contributors")
    List<Contributor> getContributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo);

    record Contributor(
            String login,
            @JsonProperty("avatar_url")
            String avatar) {
    }
}
