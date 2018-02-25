package com.company.service.auth.oauth2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class FacebookOAuth2DataRestorer implements OAuth2DataRestorer {
    @Override
    public String restoreAccessToken(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        return root.get("access_token").asText();
    }

    @Override
    public OAuth2Account restoreAccountData(String json) throws IOException {
        OAuth2Account account = new OAuth2Account();
        JsonNode data = new ObjectMapper().readTree(json);
        account.setId(data.get("id").asLong());
        account.setFirstName(data.get("first_name").asText());
        account.setSecondName(data.get("last_name").asText());
        return account;
    }
}
