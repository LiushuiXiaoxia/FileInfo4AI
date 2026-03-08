# FileInfo4AI (IntelliJ Platform 插件)

[English Version](README.md)

FileInfo4AI 是一个为 IntelliJ IDEA 和 Android Studio 等 JetBrains 系列 IDE 开发的插件，旨在快速提取和格式化文件的元数据和路径信息。当你在向 AI 编程助手提供上下文代码，或是与团队成员交流代码位置时，这个工具能帮你省去手动复制和编写文件来源的时间。

## 核心功能

一键提取当前文件的关键信息或选中代码片段的精确定位，并自动复制到系统剪切板。

### 收集的信息包含
- 文件名
- 相对路径（基于当前工程根目录）
- 全局绝对路径
- 文件类型
- 文件大小
- 最后修改时间
- 总行数
- 文件编码格式
- **选中的代码行号**（仅通过选中特定行触发时）

### 使用方式

插件提供了三种触发方式：

1. **工具窗口 (Tool Window)**
   - 在编辑器中打开任意文件。
   - 点击 IDE 侧边栏的 **FileRef** 工具窗口。
   - 点击 **"复制文件信息"** 按钮即可复制当前文件的整体状态。

2. **代码浮动工具栏 (Floating Code Toolbar)**
   - 在编辑器中选中你想要引用的某段代码。
   - 在弹出的悬浮工具栏中，点击复制图标（光标悬停显示：**"复制选中行文件信息"**）。

3. **编辑器右键菜单 (Editor Context Menu)**
   - 在编辑器中选中你想要引用的某段代码。
   - 右键点击，在弹出的菜单中选择 **"复制选中行文件信息"**。

## 输出格式示例

**全局文件信息（通过工具窗口复制）:**
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

**精准的代码位置（通过选中代码复制）:**
格式为：`@{相对路径}#L{开始行号}-{结束行号}`
```text
@/src/main/kotlin/dev/leonxia/aifileref/FileLineSelectInfoAction.kt#L15-20
```

## 本地开发与构建

项目基于 Kotlin、Gradle 构建，UI 层面采用 Compose Multiplatform (Jewel) 实现。

```bash
# 在沙盒 IDE 中运行和测试插件
./gradlew runIde

# 构建插件压缩包 (将在 build/distributions 目录下生成 ZIP)
./gradlew buildPlugin
```

## 平台兼容性

- **支持产品:** IntelliJ IDEA, Android Studio 及其他基于 IntelliJ 平台的 IDE。
- **最低版本要求:** `252.25557` (IntelliJ IDEA 2025.2 及以上)
