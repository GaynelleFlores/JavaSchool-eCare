package com.example.application.validation;

import com.example.application.models.ClientsEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientValidationTest {

    private final ClientValidation validation = new ClientValidation();

    @Test
    void validateClientCorrectPassport() {
        ClientsEntity client = new ClientsEntity();
        client.setPassport("22 22");
        assertFalse(validation.validateClient(client));
        client.setPassport("qwer");
        assertFalse(validation.validateClient(client));
        client.setPassport("11111111");
        assertFalse(validation.validateClient(client));
    }

    @Test
    void validateClientIncorrectPassport() {
        ClientsEntity client = new ClientsEntity();
        client.setPassport("2211 2211");
        assertTrue(validation.validateClient(client));
    }
}