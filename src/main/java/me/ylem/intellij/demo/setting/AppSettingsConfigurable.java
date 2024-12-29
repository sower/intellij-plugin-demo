package me.ylem.intellij.demo.setting;

import com.intellij.openapi.options.Configurable;
import java.util.Objects;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

/**
 * Provides controller functionality for application settings.
 */
public final class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because
    // this implementation is registered as an applicationConfigurable

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Code Demo";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettings.State state = Objects.requireNonNull(AppSettings.getInstance().getState());
        return !mySettingsComponent.getUserNameText().equals(state.userId)
            || mySettingsComponent.getIdeaUserStatus() != state.ideaStatus;
    }

    @Override
    public void apply() {
        AppSettings.State state = AppSettings.getInstance().getState();
        state.userId = mySettingsComponent.getUserNameText();
        state.ideaStatus = mySettingsComponent.getIdeaUserStatus();
    }

    @Override
    public void reset() {
        AppSettings.State state = AppSettings.getInstance().getState();
        mySettingsComponent.setUserNameText(state.userId);
        mySettingsComponent.setIdeaUserStatus(state.ideaStatus);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
