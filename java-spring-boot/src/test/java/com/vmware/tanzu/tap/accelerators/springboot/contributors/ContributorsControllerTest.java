package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ContributorsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "app.github.api.url=http://localhost:${wiremock.server.port}")
@Testcontainers
@AutoConfigureWireMock(port = 0)
class ContributorsControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> pgsql = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private TestRestTemplate rest;

    private static Contributor newContrib(String login, String avatar) {
        final var c = new Contributor();
        c.setLogin(login);
        c.setAvatar(avatar);
        return c;
    }

    @Test
    void getContributors() {
        stubFor(get(urlEqualTo("/repos/spring-projects/spring-boot/contributors")).willReturn(okJson("""
                [
                  {
                    "login": "wilkinsona",
                    "id": 914682,
                    "node_id": "MDQ6VXNlcjkxNDY4Mg==",
                    "avatar_url": "https://avatars.githubusercontent.com/u/914682?v=4",
                    "gravatar_id": "",
                    "url": "https://api.github.com/users/wilkinsona",
                    "html_url": "https://github.com/wilkinsona",
                    "followers_url": "https://api.github.com/users/wilkinsona/followers",
                    "following_url": "https://api.github.com/users/wilkinsona/following{/other_user}",
                    "gists_url": "https://api.github.com/users/wilkinsona/gists{/gist_id}",
                    "starred_url": "https://api.github.com/users/wilkinsona/starred{/owner}{/repo}",
                    "subscriptions_url": "https://api.github.com/users/wilkinsona/subscriptions",
                    "organizations_url": "https://api.github.com/users/wilkinsona/orgs",
                    "repos_url": "https://api.github.com/users/wilkinsona/repos",
                    "events_url": "https://api.github.com/users/wilkinsona/events{/privacy}",
                    "received_events_url": "https://api.github.com/users/wilkinsona/received_events",
                    "type": "User",
                    "site_admin": false,
                    "contributions": 15021
                  },
                  {
                    "login": "snicoll",
                    "id": 490484,
                    "node_id": "MDQ6VXNlcjQ5MDQ4NA==",
                    "avatar_url": "https://avatars.githubusercontent.com/u/490484?v=4",
                    "gravatar_id": "",
                    "url": "https://api.github.com/users/snicoll",
                    "html_url": "https://github.com/snicoll",
                    "followers_url": "https://api.github.com/users/snicoll/followers",
                    "following_url": "https://api.github.com/users/snicoll/following{/other_user}",
                    "gists_url": "https://api.github.com/users/snicoll/gists{/gist_id}",
                    "starred_url": "https://api.github.com/users/snicoll/starred{/owner}{/repo}",
                    "subscriptions_url": "https://api.github.com/users/snicoll/subscriptions",
                    "organizations_url": "https://api.github.com/users/snicoll/orgs",
                    "repos_url": "https://api.github.com/users/snicoll/repos",
                    "events_url": "https://api.github.com/users/snicoll/events{/privacy}",
                    "received_events_url": "https://api.github.com/users/snicoll/received_events",
                    "type": "User",
                    "site_admin": false,
                    "contributions": 14115
                  }
                ]""")));

        final var noContribs = rest.getForEntity("/contributors", Contributor[].class).getBody();
        assertThat(noContribs).isEmpty();

        final var out = rest.getForEntity("/contributors/refresh", String.class).getBody();
        assertThat(out).isEqualTo("Loaded 2 contributor(s)");

        final var contribs = rest.getForEntity("/contributors", Contributor[].class).getBody();
        assertThat(contribs).hasSize(2);
        assertThat(contribs).containsExactly(
                newContrib("snicoll", "https://avatars.githubusercontent.com/u/490484?v=4"),
                newContrib("wilkinsona", "https://avatars.githubusercontent.com/u/914682?v=4")
        );
    }
}
