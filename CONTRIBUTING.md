# 贡献指南（Contributing）

感谢您对 zhihu-crwaler 的关注！我们欢迎任何形式的贡献。

---

## 目录

- [前提条件](#前提条件)
- [提交 Pull Request](#提交-pull-request)
- [开发工作流](#开发工作流)
- [代码风格](#代码风格)
- [Commit 规范](#commit-规范)
- [资源](#资源)

---

## 前提条件

在开始之前，请确保本地已安装：

1. [Java 1.7+](https://www.java.com/zh-CN/download/)（推荐 Java 11+）
2. [Maven 3.x](https://maven.apache.org/download.cgi)
3. [Git](https://git-scm.com/downloads)

验证安装：

```bash
java -version
mvn -version
git --version
```

---

## 提交 Pull Request

> [!NOTE]
> 如果您的 PR 涉及较大变更（如新增功能模块、修改核心爬取逻辑），建议先通过 Issue 讨论后再提交，以避免大量返工。

1. Fork 本仓库并克隆到本地：
   ```bash
   git clone https://github.com/your-username/zhihu-crwaler.git
   cd zhihu-crwaler
   ```

2. 创建新分支：
   ```bash
   git checkout -b feat/your-feature-name
   # 或
   git checkout -b fix/your-bug-fix
   ```

3. 进行代码修改，确保本地构建通过：
   ```bash
   mvn clean package
   ```

4. 提交变更（遵循 [Commit 规范](#commit-规范)）：
   ```bash
   git add .
   git commit -m "feat(crawler): add support for video download"
   ```

5. 推送并发起 Pull Request：
   ```bash
   git push origin feat/your-feature-name
   ```

6. 在 GitHub 上提交 PR，并填写变更描述。

---

## 开发工作流

```bash
# 克隆项目
git clone https://github.com/loulanyue/zhihu-crwaler.git
cd zhihu-crwaler

# 编译（跳过测试）
mvn clean package -DskipTests

# 运行主类
mvn exec:java -Dexec.mainClass="crwaler.MyCrawler"

# 清理构建产物
mvn clean

# 或使用 Makefile
make build
make run
make clean
```

---

## 代码风格

- 使用标准 Java 编码规范（Google Java Style 或 Alibaba Java 编码规范）
- 类名使用 `UpperCamelCase`，方法名使用 `lowerCamelCase`
- 常量使用 `UPPER_SNAKE_CASE`
- 中文注释优先，关键逻辑必须添加注释
- 避免魔法数字，使用命名常量

---

## Commit 规范

本项目遵循 [Conventional Commits](https://www.conventionalcommits.org/zh-hans/) 规范：

```
<类型>(<范围>): <简短描述>

[可选的正文]

[可选的脚注]
```

### 类型（Type）说明

| 类型 | 说明 | 示例 |
|---|---|---|
| `feat` | 新功能 | `feat(crawler): add video download support` |
| `fix` | Bug 修复 | `fix(http): handle connection timeout exception` |
| `docs` | 文档更新 | `docs(readme): update configuration section` |
| `refactor` | 代码重构（不改变功能） | `refactor(json): extract parsing logic to utility` |
| `chore` | 构建/工具/依赖更新 | `chore(deps): bump httpclient to 4.5.14` |
| `test` | 添加或修改测试 | `test(crawler): add unit test for ZhihuUrl` |
| `style` | 代码格式调整（不影响逻辑） | `style(all): apply consistent indentation` |

---

## 资源

- [快速开始](./docs/quickstart.md)
- [配置说明](./docs/configuration.md)
- [常见问题](./docs/troubleshooting.md)
- [安全政策](./SECURITY.md)
- [支持渠道](./SUPPORT.md)
- [Conventional Commits 规范](https://www.conventionalcommits.org/zh-hans/)
