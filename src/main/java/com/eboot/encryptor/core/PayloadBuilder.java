package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.Messages;
import com.eboot.encryptor.utils.Constants.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.eboot.encryptor.utils.Messages.EXT_LENGTH_BYTE_SIZE;
import static com.eboot.encryptor.utils.Messages.MAX_EXTENSION_LENGTH;

public class PayloadBuilder {


    public static byte[] buildPayload(String extension, byte[] encryptedContent) {
        if (extension == null || encryptedContent == null) {
            throw new IllegalArgumentException("Extension and content cannot be null.");
        }

        byte[] extBytes = extension.getBytes(StandardCharsets.UTF_8);

        if (extBytes.length > MAX_EXTENSION_LENGTH) {
            throw new IllegalArgumentException("Extension too long. Max is 255 bytes.");
        }

        ByteBuffer buffer = ByteBuffer.allocate(EXT_LENGTH_BYTE_SIZE + extBytes.length + encryptedContent.length);
        buffer.put((byte) extBytes.length);
        buffer.put(extBytes);
        buffer.put(encryptedContent);

        return buffer.array();
    }

    /**
     * Extracts the extension from a payload.
     *
     * @param payload the payload created by buildPayload
     * @return extracted file extension string
     */
    public static String extractExtension(byte[] payload) {
        if (payload == null || payload.length < EXT_LENGTH_BYTE_SIZE) {
            throw new IllegalArgumentException("Payload too short to contain extension.");
        }

        ByteBuffer buffer = ByteBuffer.wrap(payload);
        int extLength = Byte.toUnsignedInt(buffer.get());

        if (payload.length < EXT_LENGTH_BYTE_SIZE + extLength) {
            throw new IllegalArgumentException("Payload too short to contain extension bytes.");
        }

        byte[] extBytes = new byte[extLength];
        buffer.get(extBytes);
        return new String(extBytes, StandardCharsets.UTF_8);
    }

    /**
     * Extracts the encrypted content from a payload.
     *
     * @param payload the payload created by buildPayload
     * @return extracted content bytes
     */
    public static byte[] extractContent(byte[] payload) {
        if (payload == null || payload.length < EXT_LENGTH_BYTE_SIZE) {
            throw new IllegalArgumentException("Payload too short to contain extension length.");
        }

        ByteBuffer buffer = ByteBuffer.wrap(payload);
        int extLength = Byte.toUnsignedInt(buffer.get());

        if (payload.length < EXT_LENGTH_BYTE_SIZE + extLength) {
            throw new IllegalArgumentException("Payload too short to contain full extension.");
        }

        int contentStart = EXT_LENGTH_BYTE_SIZE + extLength;
        byte[] content = new byte[payload.length - contentStart];

        System.arraycopy(payload, contentStart, content, 0, content.length);
        return content;
    }
}