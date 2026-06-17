# 支持（Support）

## 如何获取帮助

本项目使用 GitHub Issues 追踪 Bug 和功能请求。在提交新 Issue 之前，请先搜索现有 Issues 避免重复。

### 快速自助参考

| 你的问题 | 推荐文档 |
|---|---|
| 首次安装和运行 | [docs/quickstart.md](./docs/quickstart.md) |
| 配置问题 ID、保存路径 | [docs/configuration.md](./docs/configuration.md) |
| 报错、连接超时、下载失败 | [docs/troubleshooting.md](./docs/troubleshooting.md) |
| 发现安全漏洞 | [SECURITY.md](./SECURITY.md) |
| 贡献代码 | [CONTRIBUTING.md](./CONTRIBUTING.md) |

---

## 提交 Bug 报告

提交 Bug 时，请通过 [GitHub Issue](https://github.com/loulanyue/zhihu-crwaler/issues/new) 提交，并尽量包含：

1. **环境信息**：
   ```bash
   java -version        # Java 版本
   mvn -version         # Maven 版本
   uname -a             # 操作系统（macOS/Linux）
   # 或 Windows:
   systeminfo | findstr "OS"
   ```

2. **复现步骤**：能触发问题的最小配置和操作步骤

3. **期望行为**：你认为应该发生什么

4. **实际行为**：实际发生了什么，包含完整的错误日志

5. **问题 ID**：触发问题的知乎问题 ID（如可提供）

---

## 提交功能请求

欢迎功能建议！请通过 [GitHub Issue](https://github.com/loulanyue/zhihu-crwaler/issues/new) 提交，并描述：

- 该功能要解决的问题或使用场景
- 你设想的解决方案
- 你考虑过的替代方案

> [!NOTE]
> 重大功能（如新增下载类型、修改核心架构）建议先通过 Issue 讨论达成共识，再提交 Pull Request，以避免大量返工。

---

## 项目维护状态

**zhihu-crwaler** 处于基础维护状态（接受安全修复和社区 PR，不再主动开发新功能）。

**响应时效参考**：

| 类型 | 目标响应时间 |
|---|---|
| 安全漏洞 | 3 个工作日内确认 |
| Bug 报告 | 7 个工作日内初步回复 |
| 功能请求 | 尽力而为，无固定 SLA |
| Pull Request | 14 个工作日内初步审查 |

---

## 贡献代码

如果你想直接贡献修复或功能，欢迎提交 Pull Request！请先阅读 [CONTRIBUTING.md](./CONTRIBUTING.md) 了解代码规范和 PR 流程。
