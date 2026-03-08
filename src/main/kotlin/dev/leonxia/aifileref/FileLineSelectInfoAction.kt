package dev.leonxia.aifileref

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.StringSelection

/**
 * 编辑器浮动工具栏上的"复制选中行文件信息"操作
 */
class FileLineSelectInfoAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return

        val info = FileInfoCollector.collectFileInfo(project, virtualFile)
        val formatted = FileInfoCollector.formatFileInfo(info)

        // 计算选中行
        val selectionModel = editor.selectionModel
        val startLine = editor.document.getLineNumber(selectionModel.selectionStart) + 1
        val endLine = editor.document.getLineNumber(selectionModel.selectionEnd) + 1
        
        // 组装最终文本: @{相对路径}#L{开始行号}-{结束行号}
        val finalFormatted = if (startLine == endLine) {
            "@${info.relativePath}#L$startLine "
        } else {
            "@${info.relativePath}#L$startLine-$endLine "
        }

        CopyPasteManager.getInstance().setContents(StringSelection(finalFormatted))

        // 显示通知
        NotificationGroupManager.getInstance()
            .getNotificationGroup("FileRef.Notification")
            .createNotification("带行号的文件信息已复制到剪切板", NotificationType.INFORMATION)
            .notify(project)
    }

    override fun update(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        val editor = e.getData(CommonDataKeys.EDITOR)
        
        // 只有当有编辑器、有文件，且有选中文本时才显示该 Action
        e.presentation.isEnabledAndVisible = 
            virtualFile != null && e.project != null && 
            editor != null && editor.selectionModel.hasSelection()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
