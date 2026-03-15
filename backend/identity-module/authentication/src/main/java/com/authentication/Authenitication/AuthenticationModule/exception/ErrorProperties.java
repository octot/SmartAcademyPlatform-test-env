package com.authentication.Authenitication.AuthenticationModule.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class ErrorProperties {
    private Map<String, ErrorDetails> errors;

    @PostConstruct
    public void loadErrors() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream =
                new ClassPathResource("error-codes.json").getInputStream();

        errors = mapper.readValue(inputStream,
                new TypeReference<Map<String, ErrorDetails>>() {});

    }
    public ErrorDetails getError(String code) {
        return errors.get(code);
    }

}
