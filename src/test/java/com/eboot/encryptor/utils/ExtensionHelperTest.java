package com.eboot.encryptor.utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionHelperTest {

@ParameterizedTest ( name = "Encrypting {0} -> should become {1}")
@CsvSource({
        "document.txt, document.enc",
        "document.pdf, document.enc",
        "image.jpg, image.enc",
        "zipped.rar, zipped.enc",
        "README, README.enc",
        "archive.tar.gz, archive.tar.enc"
        })
    void testEncryptedPath(String inputFileName, String expectedFileName){
    Path input = Path.of(inputFileName);
    Path expected = Path.of(expectedFileName);

    assertEquals(expected,ExtensionHelper.getEncryptedPath(input));
    }

    @ParameterizedTest (name = "Decrypting {0} wih ext {1} -> should become {2}")
    @CsvSource({
        "image.enc, .jpg, image.jpg",
        "archive.enc, .tar.gz, archive.tar.gz",
        "README.enc, '', README",
        "song.enc, .mp4, song.mp4",
    })
    void testGetDecryptedPath(String inputFileName, String originalExt, String expectedName){
        Path encrypted = Path.of(inputFileName);
        Path expected = Path.of(expectedName);

        assertEquals(expected, ExtensionHelper.getDecryptedPath(encrypted,originalExt));
    }


 @Test
 void testNulInputEncryptionThrows(){
    assertThrows(NullPointerException.class, () -> ExtensionHelper.getEncryptedPath(null));
}

@Test
    void testNullInputDecryptThrows() {
        assertThrows(NullPointerException.class, () -> ExtensionHelper.getDecryptedPath(null, ".txt"));
        assertThrows(NullPointerException.class, () -> ExtensionHelper.getDecryptedPath(Path.of("file.enc"), null));
    }

}