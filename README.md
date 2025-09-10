## NewsAggregator

Небольшой агрегатор новостей на Spring Boot: периодически опрашивает источники, парсит ленты и сохраняет новости в PostgreSQL. Предоставляет REST API для получения списка новостей, категорий и источников.

### Стек
- Java 24, Spring Boot 3
- Spring Web, Spring Data JPA, Scheduling
- PostgreSQL
- Lombok

### Возможности
- Планировщик парсинга новостей каждые 15 минут
- Хранение новостей в БД с защитой от дублей по `link`
- REST API для получения новостей (последние 25), новости по `id`, категорий и источников

### Требования
- JDK 24 (или совместимый)
- Maven (или `mvnw` из проекта)
- PostgreSQL 14+

### Установка и запуск
1) Настроить БД PostgreSQL:
- создать БД `newsaggregator`
- задать доступы пользователя

2) Заполнить `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/newsaggregator
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3) Сборка и запуск:
```
./mvnw clean package
./mvnw spring-boot:run
```
Windows PowerShell:
```
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

### REST API
- GET `/api/v1/news` — последние 25 новостей
- GET `/api/v1/news/{id}` — новость по идентификатору
- GET `/api/v1/categories` — список категорий
- GET `/api/v1/sources` — список источников

Примеры:
```
curl http://localhost:8080/api/v1/news
curl http://localhost:8080/api/v1/news/1
curl http://localhost:8080/api/v1/categories
curl http://localhost:8080/api/v1/sources
```

### Планировщик
- Включён аннотацией `@EnableScheduling`
- Задача `@Scheduled(fixedRate = 15 * 60 * 1000)` запускает парсинг раз в 15 минут

### Структура проекта
- `controller/AggregatorController` — REST эндпоинты
- `service/` — бизнес-логика: парсинг, загрузка, оркестратор, планировщик
- `repository/` — доступ к БД (JPA)
- `entity/`, `dto/` — сущности и DTO
- `resources/application.properties` — конфигурация

### Тесты
Запуск:
```
./mvnw test
```

### Примечания
- Миграции схемы управляются Hibernate (`ddl-auto=update`). Для продакшн рекомендуется Flyway/Liquibase.
- Логирование сейчас базовое; для продакшн рекомендуются SLF4J + Logback.