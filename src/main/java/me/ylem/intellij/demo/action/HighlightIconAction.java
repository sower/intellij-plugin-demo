package me.ylem.intellij.demo.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import me.ylem.intellij.demo.dialog.HighlightDialogWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * HighlightIconAction
 *
 * @since 2024/09/16
 **/
public class HighlightIconAction extends AnAction {

    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link DataContext} instance.
     *
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        new HighlightDialogWrapper(event).show();
    }

}
