package dev.leonxia.fileinfo4ai

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import java.awt.datatransfer.StringSelection

/**
 * 编辑器浮动工具栏上的"复制文件信息"操作
 */
class CopyFileInfoAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val info = FileInfoCollector.collectFileInfo(project, virtualFile)
        val formatted = FileInfoCollector.formatFileInfo(info)

        CopyPasteManager.getInstance().setContents(StringSelection(formatted))

        // 显示通知
        NotificationGroupManager.getInstance()
            .getNotificationGroup("FileRef.Notification")
            .createNotification("文件信息已复制到剪切板", NotificationType.INFORMATION)
            .notify(project)
    }

    override fun update(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = virtualFile != null && e.project != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
