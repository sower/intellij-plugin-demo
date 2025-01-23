package me.ylem.intellij.demo.listener;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * EditorListener
 *
 * @since 2025/01/23
 **/
public class EditorListener implements EditorFactoryListener {

    DocContentListener docContentListener = new DocContentListener();

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        if (project != null) {
            Document document = editor.getDocument();
            document.addDocumentListener(docContentListener);
        }
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        Document document = editor.getDocument();
        document.removeDocumentListener(docContentListener);
    }
}
