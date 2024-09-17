package me.ylem.intellij.demo.constant;

import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.util.Key;
import java.util.Map;

/**
 * CommonKeys
 *
 * @since 2024/09/17
 **/
public interface CommonKeys {

    Key<Map<Integer, RangeHighlighter>> RANGE_HIGHLIGHTER = Key.create("RANGE_HIGHLIGHTER");

}
