# 🤝 Contributing to Rule Evaluation API

Thank you for your interest in contributing to the Rule Evaluation API!  
Your contributions help make this project better, more reliable, and more versatile for everyone.

Please take a moment to review this guide to understand how to contribute effectively.

---

## 🧭 Table of Contents

- [📜 Code of Conduct](#-code-of-conduct)
- [🔧 How to Contribute](#-how-to-contribute)
    - [🐛 Reporting Bugs](#-reporting-bugs)
    - [💡 Suggesting Enhancements](#-suggesting-enhancements)
    - [🔁 Submitting Pull Requests](#-submitting-pull-requests)
- [🛠️ Development Setup](#-development-setup)
- [✨ Style Guidelines](#-style-guidelines)
- [🔐 Security](#-security)
- [📄 License](#-license)

---

## 📜 Code of Conduct

By participating in this project, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md).  
Please be respectful in all interactions and maintain a collaborative, inclusive environment.

---

## 🔧 How to Contribute

There are many ways to contribute to this project:

### 🐛 Reporting Bugs

If you find a bug, please open an issue and provide:

- A clear and descriptive title
- Steps to reproduce the issue
- Expected vs. actual behavior
- Environment details (OS, Java version, browser, etc.)
- Optional: screenshots or logs

Use the `bug` label when submitting.

### 💡 Suggesting Enhancements

We welcome suggestions to improve the project.  
Please open an issue and use the `enhancement` label.

Include:

- A detailed description of the proposed change
- Use cases or motivation
- If applicable, code snippets or UI mockups

### 🔁 Submitting Pull Requests

1. Fork the repository
2. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   git commit -m "feat(rule): support for nested OR inside AND"
   git push origin feature/your-feature-name
Make your changes

Add or update tests if necessary

Ensure the code compiles and all tests pass

Commit changes with clear messages:

git commit -m "feat(rule): support for nested OR inside AND"

Push your branch:

    git push origin feature/your-feature-name

    Open a pull request on GitHub, linking to any related issues

✅ Pull Request Checklist

Code follows the existing style and conventions

Lint and formatting checks pass

Tests are added/updated

OpenAPI documentation (if affected) is updated

    Description in PR explains the change

### 🛠️ Development Setup

To build and run the project locally:
Requirements

    Java 17+

    Maven 3.6+

    Git

    Docker (optional, for DB setup)

    PostgreSQL / MySQL / H2

Steps

git clone https://github.com/madani20/dynamic-rules-engine.git
cd dynamic-rules-engine
mvn clean install
mvn spring-boot:run

Access the API at:

http://localhost:8080/api/rules/{id}/evaluate

### ✨ Style Guidelines

Please follow these conventions to keep the codebase clean:

    Use camelCase for variable and method names

    Use PascalCase for class names

    Favor final for immutability

    Format code with google-java-format or spring-javaformat

    Keep line length under 120 characters

    Use @Slf4j for logging

    Add JavaDoc for all public classes and methods

### 🧪 Testing

This project uses:

    JUnit 5 for unit testing

    Mockito for mocking dependencies

To run tests:

mvn test

You should aim for at least 80% test coverage for new features or fixes.

### 📄 License

By contributing to this repository, you agree that your contributions will be licensed under the MIT License.

We appreciate every contribution, no matter how big or small.

Thank you for helping make Rule Evaluation API better! 🙏