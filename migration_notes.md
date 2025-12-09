## Міграція V1: Ініціалізація схеми

**Файл:** [`V1__init_schema.sql`](src/main/resources/db/migration/V1__init_schema.sql)

**Опис:** Створення початкової структури бази даних

**Результат:**

![migr_v1.png](images/migr_v1.png)

-----

## Міграція V2: Додавання нової таблиці

**Файл:** [`V2__create_admin_table.sql`](src/main/resources/db/migration/V2__add_review_table.sql)

**Опис:** Створено таблицю `admins` для зберігання облікових записів адмінісраторів

**SQL:**

```sql
CREATE TABLE admins (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
```

**Результат:**

![migr_v2.png](images/migr_v2.png)
