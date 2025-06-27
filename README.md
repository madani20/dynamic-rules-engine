
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
```

## 📡 API Usage

📥 Evaluate a Rule

### 🔌 API Endpoint
POST /api/rules/{id}/evaluate

Evaluate user data against a stored rule with the given id.



✅ Sample Request

POST /api/rules/1/evaluate

Content-Type: application/json

{
  "age": 17,
  "income": 60000,
  "country": "Italy"
}

---

✅ Successful Response

{
  "valid": true,
  "failedRules": []
}


❌ Error Response

{
  "valid": false,
  "failedRules": ["age-rule"]
}

---

### 🧠 Rule Format Examples

Rules are defined as JSON trees using the following model:

     ✔️ Simple Rule
{
  "id": "age-rule",
  "type": "SIMPLE",
  "field": "age",
  "operator": ">=",
  "value": 18
}

      ✔️ AND Rule
{
  "type": "AND",  
  "rules": [
  
    { "type": "SIMPLE", "field": "age", "operator": ">", "value": 25 },
    { "type": "SIMPLE", "field": "income", "operator": ">=", "value": 50000 }
  ]
}

     ✔️  OR Rule

{
  "type": "OR",  
  "rules": [
  
    { "id": "funct-rule", "type": "SIMPLE", "field": "employmentType", "operator": "==", "value": "Fonctionnaire" },
    { "id": "cdi-rule", type": "SIMPLE", "field": "employmentType", "operator": "==", "value": "CDI" }
  ]
}


---

### 🧾 Documentation

The full OpenAPI 3 specification is defined externally in the openapi.yaml file in the root of the project.
#### 🌐 Preview Options:

    Swagger Editor

    Redocly Viewer

    Insomnia/Postman import from YAMLan Import Tool
---



