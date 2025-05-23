package com.eboot.encryptor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.eboot.encryptor.service.commands.Command;
import com.eboot.encryptor.service.commands.CommandRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

public class CommandHandlerTest {

    @ParameterizedTest
    @ValueSource(strings = {"encrypt", "decrypt", "help"})
    @DisplayName("Known commands should be executed")
    void knownCommandsExecute(String commandName) throws Exception {
        CommandRegistry mockRegistry = mock(CommandRegistry.class);
        Command mockCommand = mock(Command.class);

        when(mockRegistry.getCommand(commandName)).thenReturn(mockCommand);

        CommandHandler handler = new CommandHandler(mockRegistry);
        String[] args = {commandName, "some", "args"};

        handler.handle(args);

        verify(mockCommand).execute(args);
    }

    @Test
    @DisplayName("No command provided should print message and return")
    void noCommandProvided() throws Exception {
        CommandRegistry mockRegistry = mock(CommandRegistry.class);

        CommandHandler handler = new CommandHandler(mockRegistry);

        // Since your method prints, you can verify no commands are executed
        handler.handle(new String[0]);

        // Ensure registry.getCommand is never called because args length is 0
        verifyNoInteractions(mockRegistry);
    }


    @Test
    @DisplayName("Unknown command triggers help command execution")
    void unknownCommandTriggersHelp() throws Exception {
        CommandRegistry mockRegistry = mock(CommandRegistry.class);
        Command mockHelpCommand = mock(Command.class);

        when(mockRegistry.getCommand("unknownCommand")).thenReturn(null);
        when(mockRegistry.getCommand("help")).thenReturn(mockHelpCommand);

        CommandHandler handler = new CommandHandler(mockRegistry);
        handler.handle(new String[]{"unknownCommand"});

        verify(mockHelpCommand).execute(new String[0]);
    }

    @Test
    @DisplayName("Command execution exception is propagated")
    void commandExecutionThrowsException() throws Exception {
        CommandRegistry mockRegistry = mock(CommandRegistry.class);
        Command mockCommand = mock(Command.class);
        when(mockRegistry.getCommand("fail")).thenReturn(mockCommand);

        doThrow(new RuntimeException("fail")).when(mockCommand).execute(any());

        CommandHandler handler = new CommandHandler(mockRegistry);
        Exception exception = assertThrows(RuntimeException.class, () -> handler.handle(new String[]{"fail"}));

        assertEquals("fail", exception.getMessage());
    }
}
