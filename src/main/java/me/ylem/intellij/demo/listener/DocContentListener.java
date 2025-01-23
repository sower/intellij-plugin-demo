package me.ylem.intellij.demo.listener;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.util.Pair;
import lombok.extern.slf4j.Slf4j;
import me.ylem.intellij.demo.psi.PsiUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 文档内容监听器
 *
 * @since 2025/01/23
 **/
@Slf4j
public class DocContentListener implements DocumentListener {

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {
        Document document = event.getDocument();
        // VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        // if (file == null) {
        //     return;
        // }

        // 获取当前Editor
        Editor[] editors = EditorFactory.getInstance().getEditors(document);
        if (editors.length > 0) {
            Editor editor = editors[0];
            Pair<Integer, Integer> pair = PsiUtils.findMethodBodyLineNumbers(editor);
            log.warn("Method body line numbers: {}", pair);
        }

    }
}