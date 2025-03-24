# Dynamic logic
Пример приложения функционал которого можно расширять во время его выполнения. Динамический функционал основывается на Groovy.

Расширение системы происходит двумя способами
- Написание модулей расширения, которые предоставляют дополнительные классы
- Написание скриптов, которые могут выполняться через веб-АПИ

Кроме базового АПИ платформы скрипты могут использовать ранее написанные модули расширения. Также можно реализовывать другие модули расширения которые полагаются на ранее загруженные в систему модули.

## Run application

### Собрать jar
```bash
./mvnw clean verify
```

### Запустить jar
```bash
java -jar target/dynamic-logic-0.0.1-SNAPSHOT.jar
```

## Example usage

Предположим что мы хотим написать модуль расширения Math, который затем будем использовать в расчетах написанных на скрипте.

Пример модуля
```groovy
package ru.cyberforum

public class Math
{
  public int sum(int a, int b)
  {
    return a + b;
  }
}
```

и пример скрипта
```groovy
import ru.cyberforum.Math;

def math = new Math();
math.sum(1, 3)
```

Далее происходит загрузка модулей и скриптов в систему.

### Добавляем модуль. Модуль это что-то общее для скриптов, как библиотека
```bash
curl -X POST --location "http://localhost:8080/module_sources" \
-H "Content-Type: application/json" \
-d '{ "code": "Math", "source": "package ru.cyberforum public class Math { public int sum(int a, int b) { return a + b; } }" }'
```

### Скомпилировать модули, чтобы они стали доступными в скриптах
```bash
curl -X GET --location "http://localhost:8080/modules/reload" \
-H "Content-Type: application/json"
```

### Проверим, какие модули есть
```bash
curl -X GET --location "http://localhost:8080/module_sources" \
-H "Content-Type: application/json"
```

### Добавим скрипт, который потом выполним
```bash
curl -X POST --location "http://localhost:8080/script_sources" \
-H "Content-Type: application/json" \
-d '{ "code": "test", "source": "import ru.cyberforum.Math; def math = new Math(); math.sum(1, 3)" }'
```

### Проверим, что скрипт добавился
```bash
curl -X GET --location "http://localhost:8080/script_sources" \
-H "Content-Type: application/json"
```

### Выполним скрипт
```bash
curl -X GET --location "http://localhost:8080/scripts/run/test?args=" \
-H "Content-Type: application/json"
```
