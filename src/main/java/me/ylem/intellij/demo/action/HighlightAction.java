package me.ylem.intellij.demo.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;
import java.util.HashMap;
import java.util.Map;
import me.ylem.intellij.demo.constant.CommonKeys;
import me.ylem.intellij.demo.markup.HighlightLineGutterIconRenderer;
import org.jetbrains.annotations.NotNull;

/**
 * HighlightAction
 *
 * @since 2024/09/15
 **/
public class HighlightAction extends AnAction {

    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link DataContext} instance.
     *
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        Caret caret = caretModel.getCurrentCaret();
        LogicalPosition logicalPosition = caret.getLogicalPosition();
        int line = logicalPosition.line;
        TextAttributes textAttributes = new TextAttributes();
        textAttributes.setBackgroundColor(JBColor.YELLOW);
        RangeHighlighter rangeHighlighter = editor.getMarkupModel()
            .addLineHighlighter(line, 3999, textAttributes);
        rangeHighlighter.setGutterIconRenderer(new HighlightLineGutterIconRenderer());
        Map<Integer, RangeHighlighter> highlighterMap = CommonKeys.RANGE_HIGHLIGHTER.get(
            editor, new HashMap<>());
        highlighterMap.put(line, rangeHighlighter);
        CommonKeys.RANGE_HIGHLIGHTER.set(editor, highlighterMap);
    }

}
