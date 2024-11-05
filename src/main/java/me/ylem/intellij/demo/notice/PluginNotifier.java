package me.ylem.intellij.demo.notice;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * PluginNoticer
 *
 * @since 2024/10/28
 **/
public class PluginNotifier {

    public static void warning(Project project, String content) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Demo Notification Group")
            .createNotification(content, NotificationType.WARNING)
            .notify(project);
    }
}
