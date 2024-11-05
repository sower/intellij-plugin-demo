package me.ylem.intellij.demo.constant;

import lombok.Getter;

/**
 * 括号
 *
 * @since 2024/10/04
 **/
@Getter
public enum Bracket {

    /**
     * 小/圆括号
     */
    PARENTHESES("()"),

    /**
     * 中/方括号
     */
    BRACKETS("[]"),

    /**
     * 大/花括号
     */
    BRACES("{}"),

    /**
     * 角/尖括号
     */
    CHEVRONS("<>");

    private final String style;

    Bracket(String style) {
        this.style = style;
    }
}
