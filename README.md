
---

# 📘 Rule Evaluation API

A powerful and extensible REST API for dynamic rule validation and decision-making. This API empowers systems to perform runtime validation of arbitrary JSON data against hierarchical rules defined and persisted in JSON format.

---

## 🧩 Features

- 🔍 Evaluate complex, nested rules (`AND`, `OR`, `SIMPLE`)
- 🔄 Fully recursive rule engine
- 🗃️ Rules stored in a relational database (PostgreSQL, MySQL, etc.)
- 📄 External OpenAPI 3 YAML specification
- 📦 Designed for modular integration in any decision engine pipeline

---

## 🚀 Getting Started

### 🛠️ Requirements

- Java 17+
- Maven 3.6+
- A relational database (H2/MySQL/PostgreSQL supported)
- Spring Boot 3.1+

### ▶️ Run the API

```bash
# Clone the repository
git clone https://github.com/madani20/dynamic-rules-engine.git
cd dynamic-rules-engine

# Launch the API
mvn spring-boot:run
