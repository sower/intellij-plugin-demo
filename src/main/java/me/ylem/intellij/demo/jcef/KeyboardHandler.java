package me.ylem.intellij.demo.jcef;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefKeyboardHandler.CefKeyEvent.EventType;
import org.cef.handler.CefKeyboardHandlerAdapter;

/**
 * KeyboardHandler
 *
 * @since 2024/11/02
 **/
public class KeyboardHandler extends CefKeyboardHandlerAdapter {

    private DevToolsDialog devToolsDialog;

    @Override
    public boolean onKeyEvent(CefBrowser browser, CefKeyEvent event) {
        if (event.type == EventType.KEYEVENT_CHAR) {
            return false;
        }

        if (event.type == CefKeyEvent.EventType.KEYEVENT_KEYUP) {
            switch (event.windows_key_code) {
                case KeyEvent.VK_F5:
                    // 检测是否按下了 Ctrl 键
                    // if ((event.modifiers & CefKeyEvent.ModifierBit.MODIFIERCTRL) != 0) {
                        browser.reload();
                    // }
                    break;
                // F12 开发者工具
                case KeyEvent.VK_F12:
                    devToolsShow(browser);
                    break;
                default:
                    return false;
            }
        }
        return super.onKeyEvent(browser, event);
    }

    /**
     * 开发者工具显示或隐藏
     *
     * @param cefBrowser 显示开发者工具的浏览器
     */
    private void devToolsShow(CefBrowser cefBrowser) {
        if (devToolsDialog != null) {
            devToolsDialog.setVisible(!devToolsDialog.isActive());
        } else {
            devToolsDialog = new DevToolsDialog(new JFrame(), "开发者工具", cefBrowser);
            devToolsDialog.setVisible(true);
        }
    }
}
