package com.eboot.encryptor.utils;

import java.nio.charset.StandardCharsets;

public class PayloadBuilder {

    public static byte[] buildPayload(String extension, byte[] encryptedContent) throws Exception {
        byte[] extBytes = extension.getBytes(StandardCharsets.UTF_8);
        if (extBytes.length > 255) throw new Exception("Extension too long");
        byte[] payload = new byte[1 + extBytes.length + encryptedContent.length];
        payload[0] = (byte) extBytes.length;
        System.arraycopy(extBytes, 0, payload, 1, extBytes.length);
        System.arraycopy(encryptedContent, 0, payload, 1 + extBytes.length, encryptedContent.length);
        return payload;
    }

    public static String extractExtension(byte[] payload) {
        int extLength = payload[0] & 0xFF;
        return new String(payload, 1, extLength, StandardCharsets.UTF_8);
    }

    public static byte[] extractContent(byte[] payload) {
        int extLength = payload[0] & 0xFF;
        byte[] content = new byte[payload.length - 1 - extLength];
        System.arraycopy(payload, 1 + extLength, content, 0, content.length);
        return content;
    }
}
