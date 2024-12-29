package me.ylem.intellij.demo.tool;

import com.intellij.icons.AllIcons.Toolwindows;
import com.intellij.ide.plugins.CountIcon;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.SizedIcon;
import javax.swing.Icon;
import kotlin.coroutines.Continuation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ButtonToolWindowFactory
 *
 * @since 2024/10/27
 **/
@Slf4j
public class ButtonToolWindowFactory implements ToolWindowFactory {

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        log.info("init ToolWindow");
        toolWindow.setTitle("Counter");
        toolWindow.setIcon(getIcon("0"));
    }

    private Icon getIcon(String text) {
        CountIcon countIcon = new CountIcon();
        countIcon.setIcon(Toolwindows.InfoEvents);
        countIcon.setText(text);
        return new SizedIcon(countIcon, 16, 16);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        log.warn("build content");
        toolWindow.setToHideOnEmptyContent(true);
        toolWindow.setAutoHide(true);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        log.warn("for start up");
        return true;
    }


    @Override
    public @Nullable Object isApplicableAsync(@NotNull Project project,
        @NotNull Continuation<? super Boolean> $completion) {
        log.warn("Conditional Display");
        return ToolWindowFactory.super.isApplicableAsync(project, $completion);
    }
}
