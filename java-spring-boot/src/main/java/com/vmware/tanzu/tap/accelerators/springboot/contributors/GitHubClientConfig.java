package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@EnableConfigurationProperties(GitHubProperties.class)
@Configuration(proxyBeanMethods = false)
class GitHubClientConfig {
    @Bean
    GitHubClientService gitHubClientService(WebClient.Builder clientBuilder,
                                            @Value("${app.github.api.url}") String githubApiUrl) {
        final var client = clientBuilder.baseUrl(githubApiUrl).build();
        final var factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(GitHubClientService.class);
    }
}
