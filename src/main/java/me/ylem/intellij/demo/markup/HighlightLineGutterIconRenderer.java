package me.ylem.intellij.demo.markup;

import com.intellij.collaboration.messages.CollaborationToolsBundle;
import com.intellij.icons.AllIcons.Actions;
import com.intellij.ide.HelpTooltip;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.util.text.HtmlChunk;
import java.time.LocalDateTime;
import javax.swing.Icon;
import me.ylem.intellij.demo.action.HighlightIconAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * HighlightLine
 *
 * @since 2024/09/16
 **/
public class HighlightLineGutterIconRenderer extends GutterIconRenderer {

    String content;

    public HighlightLineGutterIconRenderer() {
        this.content = LocalDateTime.now().toString();
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    /**
     * Returns the text of the tooltip displayed when the mouse hovers over the icon.
     *
     * @return the tooltip text, or null if no tooltip is needed
     */
    @Override
    public @Nullable String getTooltipText() {
        return HtmlChunk.html()
            .addText(CollaborationToolsBundle.message("diff.add.comment.icon.tooltip"))
            .child(HtmlChunk.br())
            .addText(content).bold()
            .addRaw(HelpTooltip.getShortcutAsHtml(
                KeymapUtil.
                    getFirstKeyboardShortcutText(getShortcut())))
            .toString();
    }

    public String getShortcut() {
        return "control H";
    }

    /**
     * Returns the action executed when the icon is left-clicked.
     *
     * @return the action instance, or null if there is no left-click action
     */
    @Override
    public @Nullable AnAction getClickAction() {
        return new HighlightIconAction();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Returns the icon that is drawn in the gutter.
     */
    @Override
    public @NotNull Icon getIcon() {
        return Actions.Highlighting;
    }
}
