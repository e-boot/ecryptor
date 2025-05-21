package com.eboot.encryptor.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to build and parse payloads consisting of:
 * [1 byte: extension length] + [N bytes: file extension (UTF-8)] + [remaining bytes: encrypted content]
 */
public class PayloadBuilder {


    /**
     * Builds a payload using ByteBuffer, combining extension and encrypted content.
     *
     * @param extension        File extension (e.g. ".txt", ".jpg")
     * @param encryptedContent Encrypted file content
     * @return Byte array payload: [1-byte ext length][ext][content]
     * @throws IllegalArgumentException if the extension is too long to encode
     */
    public static byte[] buildPayload(String extension, byte[] encryptedContent) {
        byte[] extBytes = extension.getBytes(StandardCharsets.UTF_8);

        if (extBytes.length > 255) throw new IllegalArgumentException("Extension too long. Max supported length is 255 bytes.");

        ByteBuffer buffer = ByteBuffer.allocate(1 + extBytes.length + encryptedContent.length);
        buffer.put ((byte) extBytes.length);
        buffer.put(extBytes);
        buffer.put(encryptedContent);
        return buffer.array();
    }


    /**
     * Extracts the extension using ByteBuffer.
     *
     * @param payload Payload created by buildPayload
     * @return Extracted extension as string
     * @throws IllegalArgumentException if payload is too short or malformed
     */
    public static String extractExtension(byte[] payload) {

        if(payload.length < 1){
            throw new IllegalArgumentException("Payload too short to extract extension length.");
        }

       ByteBuffer buffer = ByteBuffer.wrap(payload);
        int exLength = Byte.toUnsignedInt(buffer.get());

        if(payload.length < 1 + exLength) throw new IllegalArgumentException("Payload too short to contain extension.");

        byte[] exBytes = new byte[exLength];
        buffer.get(exBytes);
        return new String(exBytes, StandardCharsets.UTF_8);
    }


    /**
     * Extracts the encrypted content from the payload.
     *
     * @param payload Payload created by buildPayload
     * @return Extracted encrypted content
     * @throws IllegalArgumentException if payload is malformed
     */
    public static byte[] extractContent(byte[] payload) {
        if(payload.length < 1) throw new IllegalArgumentException("Payload too short to extract extension length.");

       ByteBuffer buffer = ByteBuffer.wrap(payload);
       int exLength = Byte.toUnsignedInt(buffer.get());

        if(payload.length < exLength) throw new IllegalArgumentException("Payload too short to contain content. ");


        buffer.position(1 + exLength);
        byte[] content = new byte[buffer.remaining()];
        buffer.get(content);
        return content;
    }
}
