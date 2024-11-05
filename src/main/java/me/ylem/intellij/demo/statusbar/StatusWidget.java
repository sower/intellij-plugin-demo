package me.ylem.intellij.demo.statusbar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts.ConfigurableName;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * StatusWidget
 *
 * @since 2024/10/22
 **/
public class StatusWidget implements StatusBarWidgetFactory {

    /**
     * @return Widget identifier, must match extension {@code id} in {@code plugin.xml}. Used to
     * store visibility settings.
     **/
    @Override
    public @NotNull @NonNls String getId() {
        return "StatusWidget";
    }

    /**
     * @return Widget's display name. Used to refer a widget in UI, e.g. for "Enable/disable
     * &lt;display name>" action names or for checkbox texts in settings.
     */
    @Override
    public @NotNull @ConfigurableName String getDisplayName() {
        return "StatusWidget";
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        return new StatusBar(project);
    }
}
