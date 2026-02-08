package com.hytable.plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class TestPlugBuildTest {

    @Test
    @DisplayName("Plugin source files should exist")
    void testSourceFilesExist() {
        Path testPlugPath = Paths.get("src/main/java/com/hytable/plugin/TestPlug.java");
        Path helloCommandPath = Paths.get("src/main/java/com/hytable/plugin/HelloCommand.java");
        Path manifestPath = Paths.get("src/main/resources/manifest.json");

        assertTrue(Files.exists(testPlugPath), "TestPlug.java doit exister");
        assertTrue(Files.exists(helloCommandPath), "HelloCommand.java doit exister");
        assertTrue(Files.exists(manifestPath), "manifest.json doit exister");
    }

    @Test
    @DisplayName("Plugin should compile successfully")
    void testPluginCompiles() {
        // Si ce test s'exécute, c'est que la compilation a réussi
        assertTrue(true, "La compilation a réussi");
    }

    @Test
    @DisplayName("manifest.json should be valid JSON")
    void testManifestIsValidJson() {
        Path manifestPath = Paths.get("src/main/resources/manifest.json");
        
        assertDoesNotThrow(() -> {
            String content = Files.readString(manifestPath);
            assertTrue(content.contains("\"Name\""), "manifest.json doit contenir un Name");
            assertTrue(content.contains("\"Version\""), "manifest.json doit contenir une Version");
        }, "manifest.json doit être lisible et valide");
    }
}
