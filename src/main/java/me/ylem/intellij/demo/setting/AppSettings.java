package me.ylem.intellij.demo.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * AppSettings
 *
 * @since 2024/08/25
 **/
@State(
    name = "me.ylem.intellij.demo.setting.AppSettings",
    storages = @Storage("DemoPlugin.xml")
)
final class AppSettings
    implements PersistentStateComponent<AppSettings.State> {

    static class State {

        @NonNls
        public String userId = "John Smith";
        public boolean ideaStatus = false;
    }

    private State myState = new State();

    static AppSettings getInstance() {
        return ApplicationManager.getApplication()
            .getService(AppSettings.class);
    }

    @Override
    public @NotNull State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

}
