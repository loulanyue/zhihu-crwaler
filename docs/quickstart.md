# 快速开始指南

本指南帮助你在 10 分钟内完成 zhihu-crwaler 的安装并爬取第一批知乎图片。

---

## 第一步：环境准备

确保已安装以下工具：

```bash
# 检查 Java（需要 1.7+，推荐 11+）
java -version

# 检查 Maven
mvn -version
```

如未安装：
- Java：[下载 JDK](https://adoptium.net/zh-CN/)
- Maven：[下载 Maven](https://maven.apache.org/download.cgi)

---

## 第二步：克隆项目

```bash
git clone https://github.com/loulanyue/zhihu-crwaler.git
cd zhihu-crwaler
```

---

## 第三步：编译项目

```bash
mvn clean package -DskipTests
# 或使用 Makefile
make build
```

编译成功后会在 `target/` 目录生成 `ZhihuCrawler.war`。

---

## 第四步：配置爬虫参数

打开 `src/main/java/crwaler/MyCrawler.java`，修改以下两处：

### 4.1 设置图片保存路径

```java
// 修改为你本地的保存目录
public static String base = "/Users/yourname/zhihu-images";  // macOS/Linux
// public static String base = "D:/zhihu";                   // Windows
```

### 4.2 设置知乎问题 ID

找到你想爬取的知乎问题，复制 URL 中的数字 ID：

```
https://www.zhihu.com/question/271680368
                                ^^^^^^^^^
                                这就是问题 ID
```

然后修改 `main` 方法：

```java
public static void main(String[] args) throws CloneNotSupportedException {
    // 第一个参数：问题 ID
    // 第二个参数：每页回答数（建议 20）
    // 第三个参数：起始偏移量（从 0 开始）
    ZhihuUrl url = new ZhihuUrl("271680368", 20, 0);
    startCrawler(url);
}
```

---

## 第五步：运行爬虫

重新编译（因为修改了源码）并运行：

```bash
mvn clean package -DskipTests
mvn exec:java -Dexec.mainClass="crwaler.MyCrawler"
# 或
make build && make run
```

---

## 第六步：查看结果

爬取完成后，打开你设置的保存路径，图片会按如下结构组织：

```
/Users/yourname/zhihu-images/
└── [问题标题]/
    ├── [点赞数-回答者]/
    │   ├── image_001.jpg
    │   ├── image_002.jpg
    │   └── ...
    └── ...
```

---

## 下一步

- 了解更多配置选项：[docs/configuration.md](./configuration.md)
- 遇到问题：[docs/troubleshooting.md](./troubleshooting.md)
