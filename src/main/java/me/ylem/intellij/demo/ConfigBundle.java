package me.ylem.intellij.demo;

import com.intellij.DynamicBundle;
import java.util.function.Supplier;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * MessageBundle
 *
 * @since 2024/09/17
 **/
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ConfigBundle {

    @NonNls
    private static final String BUNDLE = "messages.ConfigBundle";
    private static final DynamicBundle INSTANCE =
        new DynamicBundle(ConfigBundle.class, BUNDLE);

    public static @NotNull @Nls String message(
        @NotNull @PropertyKey(resourceBundle = BUNDLE) String key,
        Object @NotNull ... params
    ) {
        return INSTANCE.getMessage(key, params);
    }

    public static Supplier<@Nls String> lazyMessage(
        @NotNull @PropertyKey(resourceBundle = BUNDLE) String key,
        Object @NotNull ... params
    ) {
        return INSTANCE.getLazyMessage(key, params);
    }
}
