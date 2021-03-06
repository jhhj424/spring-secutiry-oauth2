package com.example.oauth2.clientserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final WebClient webClient;

    @GetMapping(value = {"/samples", "/", ""})
    public String[] getSamples(
      @RegisteredOAuth2AuthorizedClient("samples-client-authorization-code") OAuth2AuthorizedClient authorizedClient
    ) {
        return this.webClient
          .get()
          .uri("http://localhost:8090/samples")
          .attributes(oauth2AuthorizedClient(authorizedClient))
          .retrieve()
          .bodyToMono(String[].class)
          .block();
    }
}