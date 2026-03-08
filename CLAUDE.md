# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 在此代码库中工作时提供指导。

## 项目概述

FileInfo4AI 是一个 IntelliJ 平台插件，用于收集和格式化文件元数据供 AI 编程助手使用。插件使用 Kotlin 和 Compose Multiplatform 构建 UI。

**核心架构：**
- 插件描述文件：`src/main/resources/META-INF/plugin.xml`
- 主包：`dev.leonxia.fileinfo4ai`
- UI：Compose Multiplatform with Jewel UI 组件
- 三个入口：工具窗口 (FileRefToolWindow)、浮动工具栏操作、编辑器右键菜单操作

## 开发命令

```bash
# 在沙盒 IDE 中运行插件用于开发/测试
./gradlew runIde

# 构建插件分发版本（输出到 build/distributions/）
./gradlew buildPlugin

# 验证插件兼容性
./gradlew verifyPlugin

# 清理构建
./gradlew clean build
```

## 版本管理

插件版本在 `gradle.properties` 中定义为 `PLUGIN-VERSION`。创建发布版本时：
1. 更新 gradle.properties 中的 `PLUGIN-VERSION`
2. 创建并推送匹配 `v*` 的 git 标签（例如 `v0.0.3`）
3. GitHub Release 工作流将自动构建并发布

## 核心组件

- **FileInfoCollector** (FileInfoCollector.kt)：核心工具类，从 VirtualFile 收集文件元数据并格式化输出
- **FileLineSelectInfoAction** (FileLineSelectInfoAction.kt)：浮动工具栏/右键菜单操作，复制带选中行号的文件信息，格式为 `@{相对路径}#L{起始行}-{结束行}`
- **FileInfo4AIToolWindowFactory** (FileInfo4AIToolWindow.kt)：带 Compose UI 的工具窗口，显示完整文件元数据

## CI/CD

- **CI 工作流** (`.github/workflows/ci.yml`)：在 push/PR 到 main/master 分支时运行，执行 `buildPlugin` 和 `verifyPlugin`
- **Release 工作流** (`.github/workflows/release.yml`)：由 `v*` 标签触发，创建 GitHub Release 并包含构建产物

## 平台兼容性

- 最低 IDE 版本：IntelliJ IDEA 2025.2+ (sinceBuild = "252.25557")
- JVM 目标：Java 21
- Kotlin 版本：2.1.20
- IntelliJ Platform Gradle Plugin：2.10.2
