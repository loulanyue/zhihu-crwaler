# Changelog

变更记录遵循 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/) 格式，
版本号遵循 [语义化版本](https://semver.org/lang/zh-CN/) 规范。

## 版本策略

- `PATCH`：不破坏兼容性的 Bug 修复、文档更新、依赖升级
- `MINOR`：向后兼容的新功能、新配置项
- `MAJOR`：破坏性变更，需要用户迁移配置或代码

---

## [Unreleased]

### Added

- 完整项目文档结构（CHANGELOG、CONTRIBUTING、SECURITY、SUPPORT、docs/）
- `docs/quickstart.md`：快速开始指南
- `docs/configuration.md`：详细配置说明
- `docs/troubleshooting.md`：常见问题排查
- `Makefile`：常用 Maven 命令封装
- 规范化所有历史 git commit 消息为 Conventional Commits 格式

### Changed

- `README.md`：扩充为完整项目文档，包含功能特性、技术选型、项目结构

---

## [1.0.0] - 2022-08-01

> 初始稳定版本，完整实现知乎图片爬取核心功能。

### Added

- **核心爬虫**（`MyCrawler.java`）：按知乎问题 ID 爬取所有回答下的图片，支持分页遍历
- **图片下载**（`JsonAnalysis.java`）：并发下载图片，按回答点赞数分文件夹保存
- **HMAC-SHA1 签名**（`HMACSHA1.java`）：实现知乎 API 请求签名验证
- **Cookie 管理**（`CookieUtil.java`）：自动维护登录态 Cookie，支持持久化
- **HTTP 封装**（`HttpService.java`）：统一 HTTP 请求服务，复用连接池
- **数据模型**：`AnswerContent`、`HeaderBean`、`LoginBean`、`ZhihuUrl` Bean 类
- **登录模块**（`ZhihuLogin.java`）：模拟知乎客户端登录流程
- Log4j2 日志配置，记录下载进度与错误信息
- Maven 项目构建（`pom.xml`）

### Fixed

- 升级 `httpclient` 从 `4.5.5` 到 `4.5.13`（安全漏洞修复）
- 升级 `fastjson` 从 `1.2.79` 到 `1.2.83`（安全漏洞修复）
- 升级 `log4j-core` 从 `2.17.1` 到 `2.25.3`（Log4Shell 及后续漏洞修复）

---

## [0.1.0] - 2019-01-01

> 初始版本，基本功能验证。

### Added

- 初始项目骨架，Maven 构建配置
- 基本 HTTP 爬取逻辑，支持单问题图片下载
- 移除内联图片预览，改为本地文件夹保存

[Unreleased]: https://github.com/loulanyue/zhihu-crwaler/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/loulanyue/zhihu-crwaler/releases/tag/v1.0.0
[0.1.0]: https://github.com/loulanyue/zhihu-crwaler/releases/tag/v0.1.0
