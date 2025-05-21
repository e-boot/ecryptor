package com.eboot.encryptor.utils;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtensionHelperTest {

    @Test
    void testExtractExtension(){
        Path filePath = Paths.get("test.txt");
        String ext = ExtensionHelper.extractExtension(filePath);
        assertEquals("txt",ext);
    }

    @Test
    void testExtractExtension_noExtension(){
        Path filePath = Paths.get("file");
        String ext = ExtensionHelper.extractExtension(filePath);
        assertEquals("",ext);
    }

    @Test
    void testExtractExtension_dotAtEnd(){
        Path filePath = Paths.get("file");
        String ext = ExtensionHelper.extractExtension(filePath);
        assertEquals("",ext);
    }
}
