# -------------------------------
# Стадия 1: Сборка (build)
# -------------------------------
FROM eclipse-temurin:17-jdk-alpine AS builder

# Устанавливаем Maven (можно также использовать готовый образ maven, но тогда пришлось бы копировать артефакты)
# Альтернатива: FROM maven:3.9.6-eclipse-temurin-17 AS builder – так проще,
# но для демонстрации ручной установки оставим как есть.
# Для надёжности используем официальный образ maven.
# Я рекомендую использовать готовый maven образ, т.к. это проще и надёжнее.
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем только pom.xml и settings.xml (если есть), чтобы закэшировать зависимости
COPY pom.xml .
# Если есть файлы настроек maven, раскомментировать:
# COPY settings.xml /root/.m2/settings.xml

# Скачиваем зависимости (этот слой будет пересобираться только при изменении pom.xml)
RUN mvn dependency:go-offline -B

# Копируем остальной код
COPY src ./src

# Собираем jar
RUN mvn clean package -DskipTests -B

# -------------------------------
# Стадия 2: Финальный образ (runtime)
# -------------------------------
FROM eclipse-temurin:17-jre-alpine

# Создаём непривилегированного пользователя
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Копируем собранный jar из стадии builder
# Предполагается, что Maven собирает jar с именем *.jar в target/
COPY --from=builder /app/target/*.jar app.jar

# Владелец файла – appuser
RUN chown -R appuser:appgroup /app

# Переключаемся на непривилегированного пользователя
USER appuser

# Открываем порт (по умолчанию для Spring Boot – 8080, измените при необходимости)
EXPOSE 8080

# Точка входа
ENTRYPOINT ["java", "-jar", "app.jar"]