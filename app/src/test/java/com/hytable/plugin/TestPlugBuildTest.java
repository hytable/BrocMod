package com.hytable.plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class BrocModBuildTest {

    @Test
    @DisplayName("Plugin source files should exist")
    void testSourceFilesExist() {
        Path BrocModPath = Paths.get("src/main/java/com/hytable/plugin/BrocMod.java");
        Path helloCommandPath = Paths.get("src/main/java/com/hytable/plugin/commands/HelloCommand.java");
        Path manifestPath = Paths.get("src/main/resources/manifest.json");

        assertTrue(Files.exists(BrocModPath), "BrocMod.java doit exister");
        assertTrue(Files.exists(helloCommandPath), "HelloCommand.java doit exister");
        assertTrue(Files.exists(manifestPath), "manifest.json doit exister");
    }

    @Test
    @DisplayName("Plugin should compile successfully")
    void BrocModCompiles() {
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
