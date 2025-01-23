package me.ylem.intellij.demo.listener;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import me.ylem.intellij.demo.psi.PsiUtils;
import org.jetbrains.annotations.NotNull;

/**
 * TypeListener
 *
 * @since 2025/01/23
 **/
public class TypeListener extends TypedHandlerDelegate {

    @Override
    public @NotNull Result charTyped(char c, @NotNull Project project, @NotNull Editor editor,
        @NotNull PsiFile file) {
        if (c == '{') {
            if (PsiUtils.findMethodBodyLineNumbers(editor) != null) {
                editor.getDocument().insertString(editor.getCaretModel().getOffset(), "\n");
            }
        }

        return Result.CONTINUE;
    }

}
