# 常见问题排查（Troubleshooting）

本文档列举 zhihu-crwaler 使用过程中常见的问题及解决方案。

---

## 安装类

### Q1：`mvn: command not found`

**原因**：Maven 未安装或未加入 PATH。

**解决**：
```bash
# macOS（使用 Homebrew）
brew install maven

# 或下载安装后配置 PATH
export PATH=/path/to/apache-maven/bin:$PATH
```

---

### Q2：`java: command not found`

**原因**：Java 未安装或 JAVA_HOME 未配置。

**解决**：
```bash
# macOS（使用 Homebrew）
brew install openjdk@11

# 配置 JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
export PATH=$JAVA_HOME/bin:$PATH
```

---

### Q3：`Could not resolve dependencies` / 依赖下载失败

**原因**：网络问题，无法访问 Maven Central。

**解决**：在 `~/.m2/settings.xml` 中配置国内镜像：
```xml
<mirrors>
  <mirror>
    <id>aliyun</id>
    <name>阿里云 Maven 镜像</name>
    <url>https://maven.aliyun.com/repository/public</url>
    <mirrorOf>central</mirrorOf>
  </mirror>
</mirrors>
```

---

## 运行类

### Q4：程序启动后立即退出，没有下载任何图片

**原因**：知乎 API 返回错误，通常是登录态失效或 Cookie 未配置。

**排查步骤**：
1. 检查日志中的 `ERROR` 级别输出
2. 确认问题 ID 是否正确（可在浏览器中打开问题验证）
3. 检查 `ZhihuLogin.java` 中的 Cookie 配置是否为最新

---

### Q5：`读取答案总数或者问题标题出错`

**原因**：知乎 API 接口变更，或请求被拦截（返回了非预期的 JSON 格式）。

**解决**：
1. 在 `log4j.properties` 中将日志级别改为 `DEBUG` 查看详细请求响应
2. 检查知乎 API 是否更新了字段名

---

### Q6：`ConnectTimeoutException` / 连接超时

**原因**：网络不稳定，或知乎对请求频率进行了限流。

**解决**：
1. 检查网络连接
2. 减少线程池并发数（详见 [configuration.md](./configuration.md#线程池配置)）
3. 在请求之间增加随机延时（需修改 `HttpService.java`）

---

### Q7：图片下载了但文件大小为 0 / 文件损坏

**原因**：图片 URL 已过期，或被知乎的防盗链机制拦截。

**解决**：
1. 重新运行爬虫（知乎图片 URL 有时效限制）
2. 检查 `Referer` 请求头是否正确设置

---

### Q8：`NullPointerException` 在 `JsonAnalysis.java`

**原因**：知乎 API 返回的 JSON 字段结构与代码预期不符（API 版本更新）。

**排查步骤**：
1. 开启 DEBUG 日志，打印 API 原始响应
2. 对比 `JsonAnalysis.java` 中的字段名与实际 JSON 结构

---

## 输出类

### Q9：图片保存路径不存在 / `No such file or directory`

**原因**：`MyCrawler.java` 中的 `base` 路径配置错误，或目标磁盘不存在。

**解决**：
1. 检查 `base` 路径是否正确（注意 Windows/macOS 路径格式差异）
2. 确认目标磁盘挂载正常
3. 手动创建目录：`mkdir -p /your/save/path`

---

### Q10：图片文件夹命名异常（出现乱码）

**原因**：问题标题或回答者名称包含特殊字符，文件系统不支持。

**解决**：在 `JsonAnalysis.java` 中对文件夹名称进行清洗：
```java
// 示例：移除文件名中的非法字符
String safeName = title.replaceAll("[\\\\/:*?\"<>|]", "_");
```

---

## 仍有问题？

请通过 [GitHub Issues](https://github.com/loulanyue/zhihu-crwaler/issues/new) 提交问题报告，
并参考 [SUPPORT.md](../SUPPORT.md) 了解如何填写有效的问题报告。
