package me.ylem.intellij.demo.jcef;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefContextMenuParams;
import org.cef.callback.CefMenuModel;
import org.cef.handler.CefContextMenuHandlerAdapter;

/**
 * ContextMenuHandler
 *
 * @since 2025/03/06
 **/
public class ContextMenuHandler extends CefContextMenuHandlerAdapter {

    @Override
    public void onBeforeContextMenu(CefBrowser browser, CefFrame frame, CefContextMenuParams params,
        CefMenuModel model) {
        model.clear();
    }

}
