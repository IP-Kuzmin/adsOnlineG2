# 💠 Ads Online 

## Описание

Ads Online — это веб-платформа для размещения объявлений о продаже и покупке вещей между пользователями. Пользователи могут создавать объявления, прикреплять изображения, писать комментарии, обновлять профиль и загружать аватарки. Администратор имеет расширенные права на редактирование и удаление любого контента.

Система состоит из клиентской части (фронтенд) и серверной (бэкенд), которые взаимодействуют через REST API.

Фронтенд запущен по адресу: [http://localhost:3000](http://localhost:3000) и предоставляет удобный интерфейс для всех действий с объявлениями, комментариями и профилем пользователя.

Бэкенд реализует всю бизнес-логику, безопасность и работу с базой данных. Также обрабатывает загрузку изображений и валидацию.

---

## 🔹 Реализовано:

- 🔐 Авторизация и аутентификация (JWT)
- 👤 Роли: пользователь / администратор
- 📦 CRUD для объявлений и комментариев
- 💬 Комментарии к объявлениям
- 🖼️ Загрузка изображений к объявлениям и аватарам

---

## 📊 Технологии:

- Java 17
- Spring Boot
- PostgreSQL / H2 (test)
- Liquibase (миграции БД)
- MapStruct (маппинг DTO)
- Swagger / OpenAPI 3 (документация API)
- JUnit 5 + Mockito (тесты)
- Maven (сборка)

---

## 🧑‍💻 Как запустить фронтенд:

1. Установите Docker Desktop
2. Выполните команду:
```bash
docker run -p 3000:3000 --rm ghcr.io/dmitry-bizin/front-react-avito:v1.21
```
3. Перейдите в браузере: [http://localhost:3000](http://localhost:3000)

---

## ⚙️ Как запустить бэкенд:

1. Укажите логин и пароль к PostgreSQL через VM options:
```bash
-Dlogin_db=your_login -Dpassword_db=your_password
```
2. Убедитесь, что PostgreSQL запущен и доступен по:
```
jdbc:postgresql://localhost:5432/ads_db
```
3. Запустите приложение как Spring Boot App
4. Liquibase применит миграции и создаст таблицы

---

## 📁 Параметры конфигурации (application.properties):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ads_db
spring.datasource.username=${login_db}
spring.datasource.password=${password_db}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate

spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.xml
spring.liquibase.enabled=true

# Каталог, куда сохраняются все изображения
app.upload.dir=uploads

# true — использовать абсолютный путь к папке загрузки, false — относительный от корня проекта
app.upload.absolute-path=false

# Путь к изображению по умолчанию, если пользователь или объявление не загрузили свою картинку
app.photo=default/placeholder.jpg
```

---

## 📚 О проекте

Данный проект создан в учебных целях в рамках программы обучения Java-разработчиков от **SkyPro**.

Мы благодарим наставников и команду SkyPro за поддержку, знания и практику, которые легли в основу этой работы.

Этот проект — результат совместной работы, практики и обучения на реальных задачах.

---

## 🗂️ Остальная информация

Описание API, логика миграций, структура директорий и примеры запросов — в разделе **Wiki** проекта.

