
````markdown
# 🧠 Rule Evaluation API

A powerful and extensible REST API for dynamic rule validation and decision-making. This API empowers systems to perform runtime validation of arbitrary JSON data against hierarchical logic trees defined and persisted in JSON.

---

## ✨ Features

- 🔍 Evaluate complex, nested rules (`AND`, `OR`, `SIMPLE`)
- 🔄 Fully recursive rule engine
- 🗃️ Rules stored in a relational database (H2, PostgreSQL, MySQL)
- 🔌 RESTful API with external OpenAPI 3 YAML specification
- 🔒 Input validation and error handling
- 🧩 Designed for modular integration in any decision engine

---

## 🚀 Getting Started

### 📦 Requirements

- Java 17+
- Maven 3.6+
- Spring Boot 3.1+
- A relational database (H2 for dev, PostgreSQL/MySQL for prod)

### ▶️ Run the API

```bash
# Clone the repository
git clone https://github.com/madani20/dynamic-rules-engine.git
cd dynamic-rules-engine

# Launch the API
mvn spring-boot:run
````

---

## 📡 API Usage

### 🔌 Endpoint: Evaluate a Rule

**POST** `/api/rules/{id}/evaluate`

Evaluate user data against a rule by its ID.

#### ✅ Sample Request

```http
POST /api/rules/1/evaluate
Content-Type: application/json
```

```json
{
  "age": 17,
  "income": 60000,
  "country": "Italy"
}
```

#### ✅ Success Response

```json
{
  "valid": true,
  "failedRules": []
}
```

#### ❌ Error Response

```json
{
  "valid": false,
  "failedRules": ["age-rule"]
}
```

#### ⚠️ Invalid Rule ID

```json
{
  "status": 400,
  "message": "Rule with ID 99 not found.",
  "error": "RuleNotFoundException",
  "path": "/api/rules/99/evaluate",
  "timestamp": "2025-06-06T18:04:12"
}
```

---

## 🧠 Rule Format

Rules are stored as JSON logic trees. They may be of type `SIMPLE`, `AND`, or `OR`.

### ✔️ Simple Rule

```json
{
  "id": "age-rule",
  "type": "SIMPLE",
  "field": "age",
  "operator": ">=",
  "value": 18
}
```

### ✔️ AND Rule

```json
{
  "type": "AND",
  "rules": [
    { "type": "SIMPLE", "field": "age", "operator": ">", "value": 25 },
    { "type": "SIMPLE", "field": "income", "operator": ">=", "value": 50000 }
  ]
}
```

### ✔️ OR Rule

```json
{
  "type": "OR",
  "rules": [
    { "id": "funct-rule", "type": "SIMPLE", "field": "employmentType", "operator": "==", "value": "Fonctionnaire" },
    { "id": "cdi-rule", "type": "SIMPLE", "field": "employmentType", "operator": "==", "value": "CDI" }
  ]
}
```

---

## 📜 OpenAPI Specification

The complete API specification is available in the `openapi.yaml` file at the root of the repository.

### 📖 View with:

* [Swagger Editor](https://editor.swagger.io/)
* [Redocly Viewer](https://redocly.github.io/redoc/)
* Postman / Insomnia (YAML import)

---

## 🧪 Testing

Run the full test suite:

```bash
mvn test
```

You can also test endpoints via Swagger or curl/Postman.

---

## 👥 Contributing

Contributions, issues, and feature requests are welcome!
Feel free to check the [issues page](https://github.com/madani20/dynamic-rules-engine/issues).

### Steps

1. Fork the project
2. Create your feature branch (`git checkout -b feature/awesome`)
3. Commit your changes (`git commit -m 'feat: add new rule logic'`)
4. Push to the branch (`git push origin feature/awesome`)
5. Open a pull request

---

## 📄 License

This project is licensed under the **MIT License**.
See the [LICENSE](LICENSE) file for details.

---

## 🧑‍💻 Author

**Madani20**
GitHub: [@madani20](https://github.com/madani20)

---

