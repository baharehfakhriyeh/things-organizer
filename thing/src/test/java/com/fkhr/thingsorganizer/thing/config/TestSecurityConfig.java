package com.fkhr.thingsorganizer.thing.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .csrf().disable();
        return http.build();
    }

    /* use this if want to include keycloak in integration test.
    private String getAccessToken(String username, String password) throws IOException
    {
        String authServerUrl = "http://localhost:8080/realms/thingsorganizer/protocol/openid-connect/token";

        String clientId = "thingsorganizer-client";
        String clientSecret = "YOUR_CLIENT_SECRET"; // optional if public client

        String body = "grant_type=password"
                + "&client_id=" + clientId
                + "&username=" + username
                + "&password=" + password
                + "&client_secret=" + clientSecret; // omit if client is public

        HttpURLConnection connection = (HttpURLConnection) new URL(authServerUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.toString());
        return jsonNode.get("access_token").asText();
    }*/
}

