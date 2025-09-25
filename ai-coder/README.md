# Spring AI Coder

🚀 **Spring AI Coder** is a VS Code extension that helps you generate **Spring Boot code** (controllers, services, models, configs, etc.) using AI.  
Unlike other code generators, it understands your **open project context** (up to 3 active files) so the generated code integrates smoothly with your existing project.

---

## ✨ Features

- 🔹 **AI-powered Spring Boot code generation**
- 🔹 Automatically includes **context from open files** (service, model, config, etc.)
- 🔹 Generate **Controllers, Services, Repositories, Entities, DTOs**
- 🔹 Works directly inside **Visual Studio Code**
- 🔹 Simple right-click or command usage

---

## 📸 Screenshots

> Replace these with actual screenshots/GIFs when you test.

### Context-aware code generation
![Context Demo](images/context-demo.png)

### Generated Spring Boot Controller
![Generated Code](images/generated-controller.png)

---

## ⚡ How to Use

1. Install the extension from the [VS Code Marketplace](https://marketplace.visualstudio.com/).
2. Open your **Spring Boot project** in VS Code.
3. Open the files you want to include as **context** (max 3 tabs).
4. Run the command:
   - `Ctrl+Shift+P` → **Generate Spring Boot Code**
5. Enter your prompt (e.g. *"Create a UserController with CRUD endpoints"*).
6. The generated code will be inserted into your editor.

---

## 🛠️ Requirements

- **Java 17+** installed (for running Spring Boot projects).
- **VS Code 1.80+**.
- Backend server running (Spring Boot service that connects to AI).

---

## ⚙️ Extension Settings

Currently, no additional settings are required.  
Future plans may include:
- Configurable number of context files
- Model selection
- Custom backend API endpoint

---

## 📦 Installation

Install from VS Code Marketplace (coming soon)  
or manually:

```bash
vsce package
code --install-extension spring-ai-coder-0.0.1.vsix