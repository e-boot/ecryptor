package com.eboot.encryptor.core;

import com.eboot.encryptor.core.PayloadBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PayloadBuilderTest {

    @Nested
    @DisplayName("Round-trip tests")
    class RoundTrip {

        @ParameterizedTest(name = "extension: \"{0}\"")
        @CsvSource({
                ".txt, Hello world!",
                ".jpg, \u00FF\u00D8\u00FF",          // Binary JPEG header
                ".data, secret123",
                "., dot only",
                "'', noExtension"
        })
        void testRoundTrip(String extension, String contentStr) {
            byte[] content = contentStr.getBytes(StandardCharsets.UTF_8);
            byte[] payload = PayloadBuilder.buildPayload(extension, content);

            String extractedExtension = PayloadBuilder.extractExtension(payload);
            byte[] extractedContent = PayloadBuilder.extractContent(payload);

            assertEquals(extension, extractedExtension);
            assertArrayEquals(content, extractedContent);
        }

        @Test
        @DisplayName("Handles max extension length (255 bytes)")
        void testMaxExtensionLength() {
            String longExt = "." + "a".repeat(254); // total 255 including dot
            byte[] content = "test".getBytes(StandardCharsets.UTF_8);

            byte[] payload = PayloadBuilder.buildPayload(longExt, content);
            String extractedExt = PayloadBuilder.extractExtension(payload);
            byte[] extractedContent = PayloadBuilder.extractContent(payload);

            assertEquals(longExt, extractedExt);
            assertArrayEquals(content, extractedContent);
        }
    }

    @Nested
    @DisplayName("Invalid cases")
    class InvalidCases {

        @Test
        @DisplayName("Throws if extension is too long")
        void testTooLongExtension() {
            String tooLong = "." + "a".repeat(255); // 256 bytes
            byte[] content = "abc".getBytes(StandardCharsets.UTF_8);

            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> PayloadBuilder.buildPayload(tooLong, content)
            );
            assertTrue(ex.getMessage().toLowerCase().contains("extension"));
        }

        @Test
        @DisplayName("Throws if payload too short to extract extension length")
        void testTooShortPayload() {
            byte[] payload = new byte[0];

            assertThrows(IllegalArgumentException.class, () -> PayloadBuilder.extractExtension(payload));
            assertThrows(IllegalArgumentException.class, () -> PayloadBuilder.extractContent(payload));
        }

        @Test
        @DisplayName("Throws if payload too short to contain extension bytes")
        void testTruncatedPayload() {
            byte[] payload = new byte[] { 10 }; // Says ext is 10 bytes, but none provided
            assertThrows(IllegalArgumentException.class, () -> PayloadBuilder.extractExtension(payload));
        }

        @Test
        @DisplayName("Throws on null inputs")
        void testNullInputs() {
            byte[] content = "abc".getBytes(StandardCharsets.UTF_8);

            assertThrows(IllegalArgumentException.class, () -> PayloadBuilder.buildPayload(null, content));
            assertThrows(IllegalArgumentException.class, () -> PayloadBuilder.buildPayload(".txt", null));
            assertThrows(IllegalArgumentException.class, () -> PayloadBuilder.extractExtension(null));
            assertThrows(IllegalArgumentException.class, () -> PayloadBuilder.extractContent(null));
        }
    }
}
