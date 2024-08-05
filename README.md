# Dynamic logic
Пример приложения функционал которого можно расширять во время его выполнения. Динамический функционал основывается на Groovy

## Example usage
### Добавляем модуль. Модуль это что-то общее для скриптов, как библиотека
curl -X POST --location "http://localhost:8080/module_sources" \
-H "Content-Type: application/json" \
-d '{ "code": "Math", "source": "package ru.cyberforum public class Math { public int sum(int a, int b) { return a + b; } }" }'

### Скомпилировать модули, чтобы они стали доступными в скриптах
curl -X GET --location "http://localhost:8080/modules/reload" \
-H "Content-Type: application/json"

### Проверим, какие модули есть
curl -X GET --location "http://localhost:8080/module_sources" \
-H "Content-Type: application/json"

### Скомпилируем модули
GET http://localhost:8080/modules/reload
Content-Type: application/json


### Добавим скрипт, который потом выполним
curl -X POST --location "http://localhost:8080/script_sources" \
-H "Content-Type: application/json" \
-d '{ "code": "test", "source": "import ru.cyberforum.Math; def math = new Math(); math.sum(1, 3)" }'

### Проверим, что скрипт добавился
curl -X GET --location "http://localhost:8080/script_sources" \
-H "Content-Type: application/json"

### Выполним скрипт
curl -X GET --location "http://localhost:8080/scripts/run/test?args=" \
-H "Content-Type: application/json"
