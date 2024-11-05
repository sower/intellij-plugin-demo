package me.ylem.intellij.demo.statusbar;

import com.intellij.icons.AllIcons.Actions;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBarWidget.IconPresentation;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import com.intellij.util.Consumer;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import me.ylem.intellij.demo.BrowserFrame;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * StatusBar
 *
 * @since 2024/10/22
 **/
public class StatusBar extends EditorBasedWidget implements IconPresentation {

    protected StatusBar(@NotNull Project myProject) {
        super(myProject);
    }

    @Override
    public @NotNull String ID() {
        return "StatusBar";
    }

    @Override
    public @Nullable Consumer<MouseEvent> getClickConsumer() {
        System.err.println("StatusBar.getClickConsumer");
        return e -> new BrowserFrame();
    }

    @Override
    public @Nullable WidgetPresentation getPresentation() {
        return this;
    }

    @Override
    public @Nullable Icon getIcon() {
        return Actions.Help;
    }

    @Override
    public @Nullable String getTooltipText() {
        @Language("HTML")
        String html = "<html><body><b>StatusBar</b></body></html>";
        return html;
    }
}
