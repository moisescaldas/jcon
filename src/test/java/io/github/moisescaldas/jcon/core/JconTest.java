package io.github.moisescaldas.jcon.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

class JconTest {

    @Test
    void instaceTest() {
        // setup
        String address = "127.0.0.1";
        int port = 25575;
        String password = "test";

        // act & assert
        assertDoesNotThrow(() -> new Jcon(address, port, password));
    }

    @Test
    void sendCommandAndReadTest() throws IOException {
        // setup
        String address = "127.0.0.1";
        int port = 25575;
        String password = "test";
        String command = "list";

        try (Jcon console = new Jcon(address, port, password)) {
            // act

            String response = console.sendCommand(command);

            // assert
            System.out.println(response);
            assertTrue(StringUtils.isNotBlank(response));
        }
    }
}
