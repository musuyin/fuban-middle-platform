# 微信聊天与语音智能解析服务

> 基于 Spring Boot + 阿里云大模型（Qwen）构建的智能文本解析服务，支持上传微信聊天截图或语音文件，自动提取并结构化对话内容。

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

## 📌 功能概览

本服务提供两个核心能力：

1. **微信聊天截图解析**
    - 用户上传微信聊天界面截图（PNG/JPG）
    - 系统自动识别图中文字（OCR）

2. **语音录音转文本与理解**
    - 用户上传 MP3/WAV 语音文件
    - 调用阿里云语音识别（ASR）服务转换为文本

---

## 🛠 技术栈

| 模块     | 技术/服务               |
|--------|---------------------|
| 后端框架   | Spring Boot 3.2.6   |
| OCR 引擎 | 阿里云 **通用文字识别**      |
| 语音识别   | 阿里云 **智能语音交互（ASR）** |
| 构建工具   | Maven               |
| 语言     | Java 17             |

---

## 🚀 快速开始

### 前置条件

- 阿里云账号（已开通 [DashScope](https://dashscope.aliyun.com) 和 [智能语音交互](https://www.aliyun.com/product/nls) 服务）
- 获取以下 API Key：
    - `API_KEY_ALI_BAILIAN`（用于调用 ASR）
    - （用于 OCR）
    - 将 API Key 配置到环境变量中
- 安装 JDK 17
- 安装 Maven

### 配置环境变量

```bash
# Qwen 大模型（用于ASR）
export API_KEY_ALI_BAILIAN=your_api_key



```

### 启动服务

```bash
git clone https://github.com/musuyin/fuban-middle-platform 
cd 
./mvnw spring-boot:run
```

服务默认运行在 `http://localhost:8080`

---

## 📡 API 接口说明

### 1. 解析微信聊天截图

```http
POST /api/v1/chat/image
Content-Type: multipart/form-data

Form Data:
- file: (binary, image/png 或 image/jpeg)
```

**响应示例**：

```json
{
  "success": true,
  "data": {
    "structuredChat": [
      {
        "speaker": "张三",
        "message": "明天开会吗？"
      },
      {
        "speaker": "李四",
        "message": "开，上午10点。"
      }
    ],
    "rawOcrText": "张三\n明天开会吗？\n李四\n开，上午10点。\n..."
  }
}
```

### 2. 上传语音文件并转文本

```http
POST /api/v1/chat/audio
Content-Type: multipart/form-data

Form Data:
- file: (binary, audio/mp3 或 audio/wav)
```

**响应示例**：

```json
{
  "success": true,
  "data": {
    "transcript": "你好啊，今天过得怎么样？"
  }
}
```

---

## 💰 成本与免费额度

- **DashScope（Qwen）**：新用户赠送 **100万 tokens 免费额度**（足够数千次测试调用）
- **阿里云 OCR / ASR**：新用户通常享有 **每月数千次免费调用**
- 建议在开发阶段使用 `qwen-turbo` 模型以降低成本

> ⚠️ 请在阿里云控制台设置用量告警，避免意外超额。

---

## 🔒 安全与隐私

- 所有上传文件仅在内存或临时目录处理，**不会持久化存储**
- 敏感信息（如 AK/SK）通过环境变量注入，**不写入代码**
- 生产环境建议启用 HTTPS 与身份认证（如 JWT）

---

## 📦 扩展方向

- 支持导出为 Markdown / JSON / Excel
- 添加对话摘要、关键词提取、情绪判断等高级分析
- 集成 Web 前端（Vue/React）实现可视化上传
- 使用 OSS 存储原始文件（可选审计）

---

## 📄 许可证

本项目基于 [Apache License 2.0](LICENSE) 开源。

---

## 🙌 致谢

- [阿里云 DashScope](https://dashscope.aliyun.com)
- [通义千问 Qwen](https://qwen.ai)

---

如有问题或建议，欢迎提交 Issue 或 PR！