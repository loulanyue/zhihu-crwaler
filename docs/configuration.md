# 配置说明

本文档详细介绍 zhihu-crwaler 的所有可配置项。

---

## MyCrawler.java — 主配置

### `base` — 图片保存根目录

```java
public static String base = "D:/zhihu";
```

| 参数 | 说明 |
|---|---|
| 类型 | `String` |
| 默认值 | `"D:/zhihu"`（Windows 路径） |
| 说明 | 爬取的图片将保存在此目录下，目录不存在时会自动创建 |

**macOS/Linux 示例：**
```java
public static String base = "/Users/yourname/Downloads/zhihu-images";
```

**Windows 示例：**
```java
public static String base = "C:/Users/yourname/Desktop/zhihu";
```

---

### `ZhihuUrl` — 爬取目标配置

```java
ZhihuUrl url = new ZhihuUrl("271680368", 20, 0);
```

| 参数位置 | 参数名 | 类型 | 说明 |
|---|---|---|---|
| 第 1 个 | `questionId` | `String` | 知乎问题 ID，从问题 URL 中获取 |
| 第 2 个 | `limit` | `int` | 每次请求获取的回答数量（建议 20，最大 20） |
| 第 3 个 | `offset` | `int` | 起始偏移量，通常填 `0` |

**如何获取问题 ID：**

打开知乎问题页面，URL 格式为：
```
https://www.zhihu.com/question/[问题ID]
```
复制方括号中的数字即可。

---

### `totals` — 总回答数上限

```java
static Integer totals = 50;
```

| 参数 | 说明 |
|---|---|
| 类型 | `Integer` |
| 默认值 | `50` |
| 说明 | 程序启动时会自动从 API 获取实际总数并覆盖此默认值 |

---

## ZhihuUrl.java — URL 构造配置

`ZhihuUrl` 类负责构造知乎 API 请求地址，内部基于官方 API 端点：

```
https://www.zhihu.com/api/v4/questions/{questionId}/answers
```

请求参数：

| 参数 | 说明 |
|---|---|
| `limit` | 每页回答数 |
| `offset` | 分页偏移量 |
| `sort_by` | 排序方式（默认按点赞数 `default`） |
| `include` | 返回字段（包含图片内容） |

---

## log4j.properties — 日志配置

日志配置文件位于 `src/main/resources/log4j.properties`。

**日志级别说明：**

| 级别 | 说明 |
|---|---|
| `DEBUG` | 详细调试信息（URL、请求参数等） |
| `INFO` | 正常运行信息（问题标题、下载进度） |
| `WARN` | 警告信息（非致命错误） |
| `ERROR` | 错误信息（下载失败、解析异常） |

**调整日志级别（减少输出噪音）：**

```properties
# 将 DEBUG 改为 INFO 减少日志量
log4j.rootLogger=INFO, stdout
```

---

## 线程池配置

线程池在 `JsonAnalysis.java` 中通过 `ExecutorService` 配置。如需调整并发数，
修改 `Executors.newFixedThreadPool(n)` 中的 `n` 值：

```java
// 当前默认（按 CPU 核心数调整）
ExecutorService pool = Executors.newFixedThreadPool(5);
```

> [!WARNING]
> 并发数过高可能触发知乎的反爬机制，建议保持在 5 以内。
