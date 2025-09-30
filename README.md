# Quest App 🎮

Java веб-приложение для обработки и прохождения квестов, описанных в формате **JSON**.  
Приложение работает на **Servlets + JSP/JSTL**, разворачивается на **Tomcat 10.1.44** и использует систему сборки **Maven**.  

## 📖 Описание
Quest App позволяет загружать квесты в формате JSON и проходить их в виде интерактивного веб-приложения.  
Программа отслеживает папку с квестами и автоматически реагирует на добавление, изменение или удаление файлов.

### Пример шаблонного JSON-файла квеста
```json
{
  "id": 1,
  "name": "Sample Quest Name",
  "questions": [
    {
      "id": 101,
      "title": "Question Title Here",
      "content": "Detailed description or situation for the question.",
      "answers": [
        {
          "content": "Answer option 1",
          "currentQuestionId": 101,
          "nextQuestionId": 102,
          "isCorrect": true
        },
        {
          "content": "Answer option 2",
          "currentQuestionId": 101,
          "nextQuestionId": 103,
          "isCorrect": false
        },
        {
          "content": "Answer option 3",
          "currentQuestionId": 101,
          "nextQuestionId": 104,
          "isCorrect": true
        }, 
         {
            "id": 999,
            "title": "Final Question Title",
            "content": "Description of the final scenario or situation.",
            "answers": []
         }
      ]
    }
  ]
}
```

⚠️ Название JSON-файла должно содержать постфикс с ID квеста, например:  
```
sample-quest_1.json
```

## ⚙️ Технологии
- **Java** – 24  
- **Apache Tomcat 10.1.44**  
- **Servlets, JSP, JSTL**  
- **Maven** – управление зависимостями и сборка  


## 📂 Конфигурация
Для корректной работы необходимо указать путь до папки с квестами (JSON файлами):  

- В переменной `GeneralConstants.QUEST_JSON_FOLDER` прописать **абсолютный путь** к папке, в которой будут лежать квесты
или к папке `quests` (лежит в корне проекта).  

Если путь не задан, программа создаст папку автоматически по адресу:
```
{userHome}/com.javarush.peev.quest/json/
```
⚠️ Папка должна быть вне итогового war-файла для реализации динамического CRUD новых квестов.

## 📑 Структура проекта
```
src/main/java/com/quest/   # Java-код (servlets, utils, repository и т.д.)
src/main/webapp/jsp        # JSP-страницы и статические ресурсы
quests/                    # Папка с квестами (JSON)
```


## 👨‍💻 Авторы
- **Никита Пеев** – разработка  


