package me.ylem.intellij.demo.jcef;

import org.apache.commons.lang3.EnumUtils;

/**
 * 路由指令
 *
 * @since 2024/11/05
 **/
public enum RouterCommand {

    RELOAD,

    OPEN_URL;


    public static RouterCommand of(String command) {
        return EnumUtils.getEnumIgnoreCase(RouterCommand.class, command);
    }

}
