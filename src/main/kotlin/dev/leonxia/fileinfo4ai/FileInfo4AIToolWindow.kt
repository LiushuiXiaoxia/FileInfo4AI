package dev.leonxia.fileinfo4ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intellij.openapi.application.readAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import kotlinx.coroutines.launch
import org.jetbrains.jewel.bridge.addComposeTab
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import java.awt.datatransfer.StringSelection

class FileInfo4AIToolWindowFactory : ToolWindowFactory {
    override fun shouldBeAvailable(project: Project) = true

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.addComposeTab("FileInfo4AI", focusOnClickInside = true) {
            FileInfo4AIToolWindowContent(project)
        }
    }
}

@Composable
private fun FileInfo4AIToolWindowContent(project: Project) {
    val coroutineScope = rememberCoroutineScope()
    var fileInfoText by remember { mutableStateOf("") }
    var statusText by remember { mutableStateOf("打开一个文件后，点击按钮复制文件信息") }

    Column(
        Modifier.padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DefaultButton(
            onClick = {
                coroutineScope.launch {
                    val virtualFile = readAction {
                        FileEditorManager.getInstance(project).selectedEditor?.file
                    }
                    if (virtualFile != null) {
                        val info = readAction {
                            FileInfoCollector.collectFileInfo(project, virtualFile)
                        }
                        val formatted = FileInfoCollector.formatFileInfo(info)
                        fileInfoText = formatted
                        CopyPasteManager.getInstance()
                            .setContents(StringSelection(formatted))
                        statusText = "✅ 文件信息已复制到剪切板"
                    } else {
                        fileInfoText = ""
                        statusText = "⚠️ 当前没有打开的文件"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("复制文件信息")
        }

        if (fileInfoText.isNotEmpty()) {
            OutlinedButton(
                onClick = {
                    CopyPasteManager.getInstance()
                        .setContents(StringSelection(fileInfoText))
                    statusText = "✅ 文件信息已复制到剪切板"
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("再次复制")
            }
        }

        Text(statusText)

        if (fileInfoText.isNotEmpty()) {
            Text(fileInfoText)
        }
    }
}
