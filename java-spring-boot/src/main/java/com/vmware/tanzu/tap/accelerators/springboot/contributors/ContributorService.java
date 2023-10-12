package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class ContributorService {
    private final GitHubProperties githubProps;
    private final GitHubClientService githubClient;
    private final ContributorRepository repo;

    ContributorService(GitHubProperties githubProps, GitHubClientService githubClient, ContributorRepository repo) {
        this.githubProps = githubProps;
        this.githubClient = githubClient;
        this.repo = repo;
    }

    @Transactional
    public List<Contributor> getContributors() {
        return repo.findContributors();
    }

    @Transactional
    public int refresh() {
        final var contribs = githubClient.getContributors(githubProps.owner(), githubProps.repo())
                .stream().map(this::toContributor).toList();
        repo.deleteAll();
        repo.saveAll(contribs);
        return contribs.size();
    }

    private Contributor toContributor(GitHubClientService.Contributor c) {
        final var contrib = new Contributor();
        contrib.setLogin(c.login());
        contrib.setAvatar(c.avatar());
        return contrib;
    }
}
