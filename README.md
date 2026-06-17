# zhihu-crwaler

> 使用 Java 爬取指定知乎问题下的所有图片（无框架，纯 httpclient 实现）

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-1.7%2B-orange.svg)](https://www.java.com)
[![Maven](https://img.shields.io/badge/build-Maven-red.svg)](https://maven.apache.org)

---

## 目录

- [功能特性](#功能特性)
- [技术选型](#技术选型)
- [快速开始](#快速开始)
- [配置说明](#配置说明)
- [项目结构](#项目结构)
- [常见问题](#常见问题)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

---

## 功能特性

- 🕷️ **按问题 ID 爬取**：指定知乎问题 ID，自动爬取该问题下所有回答中的图片
- 📁 **按点赞数排序**：图片按回答点赞数自动分文件夹保存，便于浏览高质量内容
- ⚡ **线程池加速**：内置线程池并发下载，提升下载效率
- 🔐 **HMAC-SHA1 签名**：实现知乎 API 请求签名，模拟合法客户端请求
- 🍪 **Cookie 管理**：自动维护登录态，支持持久化 Cookie

---

## 技术选型

| 组件 | 技术 | 说明 |
|---|---|---|
| HTTP 请求 | Apache HttpClient 4.5.13 | 处理知乎 API 请求及图片下载 |
| 异步 HTTP | HttpAsyncClient 4.1.3 | 并发图片下载 |
| JSON 解析 | FastJSON 1.2.83 | 解析知乎 API 返回的 JSON 数据 |
| 日志 | Log4j2 2.25.3 | 记录爬取过程日志 |
| 构建工具 | Maven | 依赖管理与项目构建 |

---

## 快速开始

### 前置要求

- Java 1.7+
- Maven 3.x
- 知乎账号（需要登录态）

### 安装与运行

```bash
# 1. 克隆仓库
git clone https://github.com/loulanyue/zhihu-crwaler.git
cd zhihu-crwaler

# 2. 编译构建
mvn clean package

# 3. 配置保存路径（编辑 MyCrawler.java）
# 修改 base 字段为本地路径，如：
# public static String base = "/Users/yourname/zhihu-images";

# 4. 配置知乎问题 ID（编辑 MyCrawler.java）
# ZhihuUrl url = new ZhihuUrl("你的问题ID", 20, 0);

# 5. 运行
mvn exec:java -Dexec.mainClass="crwaler.MyCrawler"
```

详细步骤请参阅 [docs/quickstart.md](./docs/quickstart.md)。

---

## 配置说明

在 `MyCrawler.java` 中修改以下配置：

```java
// 图片保存根目录（必须修改为本地路径）
public static String base = "D:/zhihu";

// 知乎问题 ID（URL 中的数字，如 https://www.zhihu.com/question/271680368）
ZhihuUrl url = new ZhihuUrl("271680368", 20, 0);
//                                        ↑     ↑
//                               每页回答数   起始偏移量
```

更多配置说明请参阅 [docs/configuration.md](./docs/configuration.md)。

---

## 项目结构

```
zhihu-crwaler/
├── src/
│   └── main/
│       ├── java/crwaler/
│       │   ├── MyCrawler.java          # 主入口，爬虫启动逻辑
│       │   ├── ZhihuLogin.java         # 知乎登录与 Cookie 管理
│       │   ├── HMACSHA1.java           # HMAC-SHA1 请求签名
│       │   ├── bean/
│       │   │   ├── AnswerContent.java  # 回答内容数据模型
│       │   │   ├── HeaderBean.java     # HTTP 请求头封装
│       │   │   ├── LoginBean.java      # 登录信息数据模型
│       │   │   └── ZhihuUrl.java       # 知乎 API URL 构造器
│       │   ├── download/
│       │   │   └── JsonAnalysis.java   # JSON 解析与图片下载
│       │   └── http/
│       │       ├── CookieUtil.java     # Cookie 工具类
│       │       └── HttpService.java    # HTTP 请求封装服务
│       ├── resources/
│       │   └── log4j.properties        # 日志配置
│       └── webapp/
│           └── WEB-INF/web.xml
├── docs/
│   ├── quickstart.md                   # 快速开始指南
│   ├── configuration.md                # 详细配置说明
│   └── troubleshooting.md              # 常见问题排查
├── CHANGELOG.md                        # 版本变更记录
├── CONTRIBUTING.md                     # 贡献指南
├── SECURITY.md                         # 安全政策
├── SUPPORT.md                          # 获取支持
├── Makefile                            # 常用命令封装
├── pom.xml                             # Maven 项目配置
└── README.md
```

---

## 常见问题

快速参考：

| 问题 | 参考文档 |
|---|---|
| 首次安装和运行 | [docs/quickstart.md](./docs/quickstart.md) |
| 配置问题 ID、路径 | [docs/configuration.md](./docs/configuration.md) |
| 报错排查 | [docs/troubleshooting.md](./docs/troubleshooting.md) |
| 发现安全漏洞 | [SECURITY.md](./SECURITY.md) |

---

## 贡献指南

欢迎提交 Issue 和 Pull Request！请先阅读 [CONTRIBUTING.md](./CONTRIBUTING.md) 了解贡献规范。

---

## 许可证

本项目基于 [MIT License](./LICENSE) 开源。
