package me.ylem.intellij.demo.util;

import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffManager;
import com.intellij.diff.contents.DocumentContent;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.application.ApplicationManager;

/**
 * DiffViews
 *
 * @since 2024/11/19
 **/
public class DiffViews {


    /**
     * 显示差异视图
     *
     * @param before 之前的文本
     * @param after  之后的文本
     */
    public static void showDiffView(String before, String after) {
        DiffContentFactory diffContentFactory = DiffContentFactory.getInstance();
        DocumentContent beforeContent = diffContentFactory.create(before);
        DocumentContent afterContent = diffContentFactory.create(after);
        SimpleDiffRequest diffRequest = new SimpleDiffRequest("Text Compare", beforeContent,
            afterContent, "Before", "After");
        DiffManager.getInstance().showDiff(null, diffRequest);
    }

    public static void asyncShowDiffView(String before, String after) {
        ApplicationManager.getApplication().invokeLater(() -> showDiffView(before, after));
    }
}
