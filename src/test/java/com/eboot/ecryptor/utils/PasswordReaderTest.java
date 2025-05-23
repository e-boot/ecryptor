package com.eboot.ecryptor.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.Console;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PasswordReaderTest {

    Console mockConsole;
    Scanner mockScanner;
    PasswordReader passwordReader;

    @BeforeEach
    void setup() {
        mockConsole = mock(Console.class);
        mockScanner = mock(Scanner.class);
    }

    @Test
    void prompt_usesConsole_whenAvailable() {
        when(mockConsole.readPassword("Enter: ")).thenReturn("secret".toCharArray());
        passwordReader = new PasswordReader(mockConsole, mockScanner);

        String result = passwordReader.prompt("Enter: ");
        assertEquals("secret", result);
        verify(mockConsole).readPassword("Enter: ");
        verifyNoInteractions(mockScanner);
    }

    @Test
    void prompt_usesScanner_whenConsoleIsNull() {
        passwordReader = new PasswordReader(null, mockScanner);
        when(mockScanner.nextLine()).thenReturn("typedPassword");

        String result = passwordReader.prompt("Enter: ");
        assertEquals("typedPassword", result);
        verify(mockScanner).nextLine();
    }

    @Test
    void readAndConfirmPassword_passwordsMatchOnFirstTry_returnsPassword() {
        passwordReader = new PasswordReader(mockConsole, mockScanner);
        when(mockConsole.readPassword(Messages.ENTER_PASSWORD))
                .thenReturn("pass123".toCharArray());
        when(mockConsole.readPassword(Messages.CONFIRM_PASSWORD))
                .thenReturn("pass123".toCharArray());

        String result = passwordReader.readAndConfirmPassword();
        assertEquals("pass123", result);
    }

    @Test
    void readAndConfirmPassword_passwordsDontMatch_thenMatch_returnsPassword() {
        passwordReader = new PasswordReader(mockConsole, mockScanner);

        when(mockConsole.readPassword(Messages.ENTER_PASSWORD))
                .thenReturn("wrong".toCharArray(), "correct".toCharArray(), "correct".toCharArray());
        when(mockConsole.readPassword(Messages.CONFIRM_PASSWORD))
                .thenReturn("nope".toCharArray(), "correct".toCharArray(), "correct".toCharArray());

        String result = passwordReader.readAndConfirmPassword();
        assertEquals("correct", result);
    }

    @Test
    void readAndConfirmPassword_returnsNull_afterThreeMismatches() {
        Console mockConsole = mock(Console.class);
        PasswordReader passwordReader = new PasswordReader(mockConsole, null);

        when(mockConsole.readPassword(Messages.ENTER_PASSWORD))
                .thenReturn("wrong1".toCharArray(), "wrong2".toCharArray(), "wrong3".toCharArray());

        when(mockConsole.readPassword(Messages.CONFIRM_PASSWORD))
                .thenReturn("nope1".toCharArray(), "nope2".toCharArray(), "nope3".toCharArray());

        String result = passwordReader.readAndConfirmPassword();

        assertNull(result);
    }

    @Test
    void prompt_trimsPassword() {
        Console mockConsole = mock(Console.class);
        PasswordReader passwordReader = new PasswordReader(mockConsole, null);

        when(mockConsole.readPassword("Enter: ")).thenReturn("  secret  ".toCharArray());

        String result = passwordReader.prompt("Enter: ");

        assertEquals("secret", result);
    }
}