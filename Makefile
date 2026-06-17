.PHONY: help build test clean run package

# 默认目标
.DEFAULT_GOAL := help

## ─── 构建 ────────────────────────────────────────────────────────────────────

build: ## 编译项目（跳过测试）
	mvn clean package -DskipTests

package: ## 完整打包（含测试）
	mvn clean package

test: ## 运行单元测试
	mvn test

clean: ## 清理构建产物
	mvn clean

## ─── 运行 ────────────────────────────────────────────────────────────────────

run: ## 运行主类（需先 make build）
	mvn exec:java -Dexec.mainClass="crwaler.MyCrawler"

## ─── 依赖 ────────────────────────────────────────────────────────────────────

deps: ## 显示依赖树
	mvn dependency:tree

deps-update: ## 检查可更新的依赖
	mvn versions:display-dependency-updates

## ─── 帮助 ────────────────────────────────────────────────────────────────────

help: ## 显示帮助信息
	@echo ""
	@echo "zhihu-crwaler — 知乎图片爬虫 Makefile"
	@echo ""
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2}'
	@echo ""
