package me.ylem.intellij.demo.listener;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * JavaFileDocumentManagerListener
 *
 * @since 2025/01/23
 **/
@Slf4j
public class JavaFileDocumentManagerListener implements FileDocumentManagerListener {

    private final DocumentListener myDocumentListener = new DocContentListener();

    @Override
    public void beforeDocumentSaving(@NotNull Document document) {
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file != null) {
            System.out.println("Document is about to be saved: " + file.getPath());
        }
    }

    @Override
    public void fileContentReloaded(@NotNull VirtualFile file, @NotNull Document document) {
        log.info("File content reloaded: {}", file.getPath());
        FileDocumentManagerListener.super.fileContentReloaded(file, document);
    }

    @Override
    public void fileContentLoaded(@NotNull VirtualFile file, @NotNull Document document) {
        log.info("File content loaded: {}", file.getPath());
        FileDocumentManagerListener.super.fileContentLoaded(file, document);
    }
}