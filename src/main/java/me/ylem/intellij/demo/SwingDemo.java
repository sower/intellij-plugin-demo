package me.ylem.intellij.demo;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBList;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.JFrame;
import me.ylem.intellij.demo.constant.Bracket;

/**
 * FloodingAction
 *
 * @since 2024/09/17
 **/
public class SwingDemo {

    static JBList<JBCheckBox> list;

    static void init() {
        JBCheckBox[] brackets = Arrays.stream(Bracket.values()).map(Bracket::getStyle)
            .map(JBCheckBox::new).toArray(JBCheckBox[]::new);
        list = new JBList<>(brackets);

    }

    public static void main(String[] args) {
        Frame frame = new JFrame("窗口标题");
        frame.setSize(500, 300);

        // 设置窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  // 获取到屏幕尺寸
        int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        // 普通的文本内容
        Label label = new Label("文本标签");
        label.setLocation(20, 50);
        label.setSize(100, 20);
        frame.add(label);
        init();
        frame.add(list);

        frame.setVisible(true); // 默认情况下窗体是不可见的
    }

}
