# Spring AI Alibaba 系列教程源码 · Spring AI Alibaba Tutorial Series Source Code

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-green)
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.1.2-blue)
![Spring AI Alibaba](https://img.shields.io/badge/Spring%20AI%20Alibaba-1.1.2.0-purple)

> 本仓库是作者《Spring AI Alibaba》系列博客文章的配套源码，共 18 个 Maven 模块（Saa01 ~ Saa18），与文章章节一一对应。
>
> This repository is the companion source code for the author's *Spring AI Alibaba* blog series. It contains 18 Maven modules (Saa01 ~ Saa18), each matching one chapter of the series.

---

## 📚 系列介绍 · About the Series

**中文**

这是一套面向 Java 开发者的 Spring AI Alibaba 实战教程源码。系列从 Hello World 起步，循序渐进地覆盖：

- 基础能力：`ChatModel` / `ChatClient`、Prompt 与提示词模板、流式输出与响应式编程、结构化输出；
- 工程能力：Redis 会话记忆、Embedding 向量化与向量数据库、RAG 检索增强生成；
- 进阶能力：Tool Calling 函数调用、MCP Server / Client / 第三方服务接入、百炼托管知识库；
- 智能体：基于 `ReactAgent` 打造"今日菜单"应用，组合本地 `@Tool` 与外部 MCP 工具。

**English**

This is a hands-on tutorial series on Spring AI Alibaba for Java developers. Starting from a Hello World example, it covers:

- Fundamentals: `ChatModel` / `ChatClient`, prompts & prompt templates, streaming output with reactive programming, structured output;
- Engineering: Redis-backed chat memory, embeddings & vector stores, RAG (retrieval-augmented generation);
- Advanced: tool calling with `@Tool`, MCP Server / Client / third-party services, Bailian (DashScope) hosted knowledge base;
- Agents: a *"What to eat today"* app built with `ReactAgent`, combining local `@Tool` callbacks with external MCP tools.

---

## 🧰 技术栈 · Tech Stack

| 技术 Technology | 说明 Description |
|---|---|
| Java 21 + Spring Boot 3.5.5 | 基础运行框架 Base framework |
| Spring AI 1.1.2 | 统一的 AI 应用编程模型 Unified AI programming model |
| Spring AI Alibaba 1.1.2.0 | 阿里云百炼（DashScope）接入、Agent Framework、Graph、MCP |
| Maven 多模块 + BOM | 三个 BOM 统一管理依赖版本 Dependency management |
| DashScope 模型 Models | qwen / deepseek / wanx2.1-t2i / cosyvoice-v3 / text-embedding-v3 |
| Ollama | 本地大模型 Local LLM |
| Redis / Redis Stack | 会话记忆与向量存储 Chat memory & vector store |
| MCP | Streamable-HTTP 与 stdio 协议接入 |

---

## 🚀 快速开始 · Quick Start

### 环境要求 · Prerequisites

- JDK 21+
- Maven 3.9+（或直接使用 IDEA 打开父工程）
- 阿里云百炼平台 API Key（DashScope）
- 可选依赖：Ollama（Saa02）、Redis Stack（Saa08 / Saa11 / Saa12 / Saa13）、Node.js + npx（Saa16 / Saa18 百度地图 MCP）

### 1. 配置 API Key · Configure API Key

1. 登录[阿里云百炼平台](https://bailian.console.aliyun.com/)，创建 DashScope API Key；
2. 在系统环境变量（或 IDEA Run Configuration 的 Environment Variables）中设置 `DASHSCOPE_API_KEY`；
3. 保持各模块 `application.yml` 中的配置为 `spring.ai.dashscope.api-key: ${DASHSCOPE_API_KEY}`。

> ⚠️ 本仓库所有模块的 `api-key` 已清空，请务必通过环境变量注入，**不要**把真实 Key 写进配置文件并提交。

### 2. 运行 · Run

用 IDEA 打开父工程，选择对应模块运行其 `*Application.java`；或使用 Maven 命令行：

```bash
mvn -pl Saa01 -am spring-boot:run
```

各模块端口见其 `application.yml`（多为 `8001` / `8004` / `8014` / `8015`）。

### 3. 测试示例 · Smoke Test

```bash
# Saa01 普通对话 / 流式对话
curl "http://localhost:8001/hello/dochat?msg=你好"
curl "http://localhost:8001/hello/streamchat?msg=介绍一下Java"
```

---

## 📦 模块一览 · Modules

| 模块 Module | 章节 Chapter | 主题 Topic |
|---|---|---|
| Saa01 | 01 | HelloWorld：初识 Spring AI Alibaba（`ChatModel` 普通 / 流式调用）<br>First look at the framework: `ChatModel` call & stream |
| Saa02 | 02 | Ollama 本地大模型调用<br>Local LLM with Ollama |
| Saa03 | 03 | `ChatModel` 与 `ChatClient` 深度对比<br>In-depth comparison of `ChatModel` vs `ChatClient` |
| Saa04 | 04 | 流式输出与响应式编程（Flux / SSE）<br>Streaming output & reactive programming |
| Saa05 | 05 | Prompt：提示词基础与多种消息类型<br>Prompt basics & message types |
| Saa06 | 06 | PromptTemplate：提示词模板与变量替换<br>Prompt templates & variable substitution |
| Saa07 | 07 | 结构化输出与对象映射（`entity()` / records）<br>Structured output & object mapping |
| Saa08 | 08 | 持久化会话与 Redis 内存管理（`RedisSaver` / `threadId`）<br>Chat memory persistence with Redis |
| Saa09 | 09 | Text2Image：文本生成图像（wanx2.1-t2i-plus）<br>Text-to-image generation |
| Saa10 | 10 | Text2Voice：文本转语音（cosyvoice-v3-flash）<br>Text-to-speech synthesis |
| Saa11 | 11 | Embedding：向量化与向量数据库（Redis Stack）<br>Embeddings & vector store |
| Saa12 | 12 | RAG：检索增强生成（运维知识库 RAG4AiOps）<br>RAG with an ops knowledge base |
| Saa13 | 13 | Tool Calling：函数工具调用（`@Tool`）<br>Function / tool calling with `@Tool` |
| Saa14 | 14 | MCP Server：本地服务与工具集成（天气服务）<br>Expose local tools as an MCP server |
| Saa15 | 15 | MCP Client：调用本地 MCP 服务<br>Call the local MCP server from a client |
| Saa16 | 16 | 调用百度地图 MCP 服务（stdio + npx）<br>Call the third-party Baidu Map MCP |
| Saa17 | 17 | 百炼 RAG 知识库应用（`DashScopeDocumentRetriever`）<br>Bailian hosted RAG knowledge base |
| Saa18 | 18 | Agent 智能体与今日菜单应用（`ReactAgent`）<br>Agent demo: "What to eat today" |

---

## 📖 文章专栏 · Articles

系列文章发布于**稀土掘金（juejin.cn）**、**CSDN** 等博客平台。在对应平台搜索作者 **Flittly**，即可找到同名作者及其《Spring AI Alibaba》专栏，与各章节配套阅读效果更佳。

文章目录如下：

1. SAA-01 HelloWorld：初识 Spring AI Alibaba 框架
2. SAA-02 Ollama：本地大模型调用
3. SAA-03 ChatModel 与 ChatClient 的深度对比
4. SAA-04 流式输出与响应式编程
5. SAA-05 Prompt：提示词基础与多种消息类型
6. SAA-06 PromptTemplate：提示词模板与变量替换
7. SAA-07 StructureOutput：结构化输出与对象映射
8. SAA-08 持久化会话与 Redis 内存管理
9. SAA-09 Text2Image：文本生成图像技术
10. SAA-10 Text2Voice：文本转语音技术
11. SAA-11 Embedding：向量化与向量数据库
12. SAA-12 RAG4AiOps：检索增强生成技术
13. SAA-13 ToolCalling：函数工具调用技术
14. SAA-14 MCP 本地服务与工具集成
15. SAA-15 MCP Client 调用本地服务
16. SAA-16 调用百度 MCP 服务
17. SAA-17 百炼 RAG 知识库应用
18. SAA-18 Agent 智能体与今日菜单应用

---

## 🔐 安全提示 · Security Notice

- 所有模块的 `api-key` 均已清空，运行时请通过环境变量（如 `DASHSCOPE_API_KEY`）注入；
- 不要将任何 API Key / AK 写入配置文件并提交，包括 `mcp-server.json5` 中的百度地图 AK；
- 一旦密钥曾提交到公开仓库，应立即在平台控制台吊销并重新生成。

---

## 📄 声明 · License

本仓库代码仅供学习交流使用，请遵守阿里云百炼平台及相关开源组件的许可条款。

Code in this repository is provided for learning purposes only. Please comply with the license terms of Alibaba Cloud Bailian and the open-source components used.
