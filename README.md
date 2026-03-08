# FileInfo4AI (IntelliJ Platform Plugin)

[中文版](README-CN.md)

FileInfo4AI is an IntelliJ IDEA / Android Studio plugin designed to quickly collect and format file metadata. It is especially useful when providing context to AI coding assistants or communicating with team members.

## Features

This plugin allows you to quickly copy essential file information into your clipboard with a single click.

### Copied Information Includes
- File Name
- Relative Path (relative to project root)
- Absolute Path
- File Type
- File Size
- Last Modified Time
- Total Lines
- File Encoding
- **Selected Lines** (When using the line-selection feature)

### How to Use

There are three ways to access the feature:

1. **Tool Window**
   - Open a file in your editor.
   - Open the **FileRef** tool window on the right side of the IDE.
   - Click the **"复制文件信息" (Copy File Info)** button.

2. **Floating Code Toolbar**
   - Select a block of text/code in your editor.
   - Click the **"复制选中行文件信息" (Copy Selected Lines Info)** icon appearing in the floating toolbar above the selection.

3. **Editor Context Menu**
   - Select a block of text/code in your editor.
   - Right-click and choose **"复制选中行文件信息" (Copy Selected Lines Info)** from the context menu.

## Example Output

**Without selection:**
```text
文件名: FileInfoCollector.kt
相对路径: /src/main/kotlin/.../FileInfoCollector.kt
绝对路径: /Users/leon/.../FileInfoCollector.kt
文件类型: Kotlin
文件大小: 2.6 KB
最后修改时间: 2026-03-08 14:30:00
行数: 86
文件编码: UTF-8
```

**With text selection (`FileLineSelectInfoAction`):**
```text
@/src/main/kotlin/dev/leonxia/aifileref/FileLineSelectInfoAction.kt#L15-20
```

## Development & Build

This plugin is built using the Gradle IntelliJ Plugin and Compose Multiplatform for the Tool Window UI.

```bash
# Run the plugin in a sandbox IDE
./gradlew runIde

# Build the plugin distribution (generates ZIP in build/distributions)
./gradlew buildPlugin
```

## Compatibility

- **IDE:** IntelliJ IDEA, Android Studio, and other IntelliJ-based IDEs.
- **Minimum Build:** `252.25557` (IntelliJ IDEA 2025.2+)