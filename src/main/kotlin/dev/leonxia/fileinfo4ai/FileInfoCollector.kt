package dev.leonxia.fileinfo4ai

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 文件信息数据类
 */
data class FileInfo(
    val fileName: String,
    val relativePath: String,
    val absolutePath: String,
    val fileType: String,
    val fileSize: Long,
    val lastModified: Long,
    val lineCount: Int,
    val charset: String,
)

/**
 * 文件信息收集和格式化工具类
 */
object FileInfoCollector {

    /**
     * 从 VirtualFile 中收集文件信息
     */
    fun collectFileInfo(project: Project, file: VirtualFile): FileInfo {
        val basePath = project.basePath ?: ""
        val fullPath = file.path
        val relativePath = if (basePath.isNotEmpty() && fullPath.startsWith(basePath)) {
            fullPath.removePrefix(basePath).removePrefix("/")
        } else {
            fullPath
        }

        val content = try {
            String(file.contentsToByteArray(), charset(file.charset.name()))
        } catch (_: Exception) {
            null
        }
        val lineCount = content?.lines()?.size ?: 0

        return FileInfo(
            fileName = file.name,
            relativePath = relativePath,
            absolutePath = fullPath,
            fileType = file.fileType.name,
            fileSize = file.length,
            lastModified = file.timeStamp,
            lineCount = lineCount,
            charset = file.charset.name(),
        )
    }

    /**
     * 将 FileInfo 格式化为可读文本
     */
    fun formatFileInfo(info: FileInfo): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val modifiedDate = dateFormat.format(Date(info.lastModified))
        val sizeText = formatFileSize(info.fileSize)

        return buildString {
            appendLine("文件名: ${info.fileName}")
            appendLine("相对路径: ${info.relativePath}")
            appendLine("绝对路径: ${info.absolutePath}")
            appendLine("文件类型: ${info.fileType}")
            appendLine("文件大小: $sizeText")
            appendLine("最后修改时间: $modifiedDate")
            appendLine("行数: ${info.lineCount}")
            append("文件编码: ${info.charset}")
        }
    }

    private fun formatFileSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> String.format("%.1f KB", bytes / 1024.0)
            bytes < 1024 * 1024 * 1024 -> String.format("%.1f MB", bytes / (1024.0 * 1024))
            else -> String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024))
        }
    }
}
