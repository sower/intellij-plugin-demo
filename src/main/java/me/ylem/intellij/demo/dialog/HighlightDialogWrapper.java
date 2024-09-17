package me.ylem.intellij.demo.dialog;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.ui.DialogWrapper;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import me.ylem.intellij.demo.constant.CommonKeys;
import org.jetbrains.annotations.Nullable;

/**
 * HighlightDialogWrapper
 *
 * @since 2024/09/16
 **/
public class HighlightDialogWrapper extends DialogWrapper {

    private JPanel contentPane;

    private String title;

    private String content;

    private Editor editor;


    /**
     * Creates modal {@code DialogWrapper}. The currently active window will be the dialog's
     * parent.
     *
     * @throws IllegalStateException if the dialog is invoked not on the event dispatch thread
     */
    public HighlightDialogWrapper(AnActionEvent event) {
        super(true);
        this.editor = event.getRequiredData(CommonDataKeys.EDITOR);
        this.title = editor.getVirtualFile().getName();
        setTitle(title);
        this.content = editor.getSelectionModel().getSelectedText();
        this.contentPane = new HighlightDialogPanel(title, content);
        init();
    }


    /**
     * Factory method. It creates panel with dialog options. Options panel is located at the center
     * of the dialog's content pane. The implementation can return {@code null} value. In this case
     * there will be no options panel.
     */
    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }

    /**
     * Dispose the wrapped and releases all resources allocated be the wrapper to help more
     * efficient garbage collection. You should never invoke this method twice or invoke any method
     * of the wrapper after invocation of {@code dispose}.
     *
     * @throws IllegalStateException if the dialog is disposed not on the event dispatch thread
     */
    @Override
    protected void dispose() {
        super.dispose();
        removeHighlightLine();
    }

    /**
     * This method is invoked by default implementation of "Cancel" action. It just closes dialog
     * with {@code CANCEL_EXIT_CODE}. This is convenient place to override functionality of "Cancel"
     * action. Note that the method does nothing if "Cancel" action isn't enabled.
     */
    @Override
    public void doCancelAction() {
        super.doCancelAction();
        System.out.println("点击了取消");
    }

    private void removeHighlightLine() {
        CaretModel caretModel = editor.getCaretModel();
        Caret caret = caretModel.getCurrentCaret();
        LogicalPosition logicalPosition = caret.getLogicalPosition();
        int line = logicalPosition.line;
        Map<Integer, RangeHighlighter> highlighterMap = CommonKeys.RANGE_HIGHLIGHTER.get(editor);
        RangeHighlighter highlighter = highlighterMap.remove(line);
        HintManager.getInstance().showInformationHint(editor, "Clear highlight");
        if (highlighter != null) {
            editor.getMarkupModel().removeHighlighter(highlighter);
            Notifications.Bus.notify(new Notification(
                "Custom Notification Group", "Highlight", "移除 Highlighter",
                NotificationType.INFORMATION
            ));
        }
    }
}
