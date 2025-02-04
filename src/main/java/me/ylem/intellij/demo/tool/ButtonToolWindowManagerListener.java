package me.ylem.intellij.demo.tool;

import com.intellij.ide.plugins.CountIcon;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.SizedIcon;
import me.ylem.intellij.demo.ConfigBundle;
import me.ylem.intellij.demo.notice.PluginNotifier;
import org.jetbrains.annotations.NotNull;

/**
 * ButtonToolWindowManagerListener
 *
 * @since 2024/10/27
 **/
public class ButtonToolWindowManagerListener implements ToolWindowManagerListener {

    final String ID = ConfigBundle.message("button.tool.windows");

    /**
     * Invoked when tool window is shown.
     *
     * @param toolWindow shown tool window
     */
    @Override
    public void toolWindowShown(@NotNull ToolWindow toolWindow) {
        if (!ID.equals(toolWindow.getId())) {
            ToolWindowManagerListener.super.toolWindowShown(toolWindow);
            return;
        }

        toolWindow.hide();
        SizedIcon icon = (SizedIcon) toolWindow.getIcon();
        CountIcon countIcon = (CountIcon) icon.retrieveIcon();
        int count = Integer.parseInt(countIcon.getText());
        countIcon.setText(String.valueOf(++count));
        toolWindow.setTitle("Counter " + count);
        PluginNotifier.warning(toolWindow.getProject(), "工具窗按钮触发通知");
    }
}
