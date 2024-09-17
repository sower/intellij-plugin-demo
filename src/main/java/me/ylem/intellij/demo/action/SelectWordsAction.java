package me.ylem.intellij.demo.action;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class SelectWordsAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Get all the required data from data keys
        Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();

        // Work off of the primary caret to get the selection info
        CaretModel caretModel = editor.getCaretModel();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();
        String format = String.format("==>  (%s)  <==", selectedText);
        System.err.println(format);

        // Replace the selection with a fixed string.
        // Must do this document change in a write action context.
        WriteCommandAction.runWriteCommandAction(project, () ->
            document.replaceString(start, end, format)
        );

        IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(
            PluginId.getId("me.ylem.intellij.demo"));

        Messages.showMessageDialog(project, "Selected text: " + selectedText, plugin.getName(),
            Messages.getInformationIcon());

        // De-select the text range that was just replaced
        // primaryCaret.removeSelection();
    }

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        e.getPresentation().setEnabledAndVisible(caretModel.getCurrentCaret().hasSelection());
    }

}
