# Задание
Необходимо написать REST сервис для бронирования мест в кинотеатрах. Сервис 
должен иметь следующую функциональность:
1. Получение списка мест в зале со статусами свободно/забронировано. 
2. Бронирование одного или нескольких свободных мест в зале кинотеатра. 
Примечания: 
1. Добавить недостающие по Вашему мнению методы в API (например: список 
кинотеатров, список залов в кинотеатре и прочее). 
2. Для реализации API можно использовать любой фреймворк. 
3. Для хранения данных использовать PostgreSQL или embedded db c SQLсинтаксисом. 
4. При работе с базой использовать JDBC (не использовать ORM). 
5. Продемонстрировать работу API с помощью тестов. 
6. Обеспечить максимально простое развёртывание приложения (например 
docker-контейнер). 
7. Исходный код выложить на github или bitbucket.

# Запуск приложения, в терминале выполняем команду 
```docker-compose up --build```

# Методы сервиса
1. GET Список кинотеатров
    ```/cinema```
    
    В ответ получаем список кинотеатров
    ```
    [
        {
            "id": 1,
            "name": "Мираж1",
            "hallList": null
        },
       ...
    ]
   ```
2. GET Список залов в кинотеатре 
    ```cinema/{id кинотеатра}/hall```
    
    ```[
        {
            "id": 1,
            "name": "1",
            "filmSessionList": null
        },
        {
            "id": 2,
            "name": "2",
            "filmSessionList": null
        },
        {
            "id": 3,
            "name": "3",
            "filmSessionList": null
        }
    ]
    ```
3. GET Список сеансов предстоящих сеансов в кинотеатре 
       ```hall/{id зала}/actualfilmsession?onDate=``` 
    onDate, на которую получить список сеансов в формате
       yyyy-MM-dd
       Пример запроса
       ``` [
            {
                "id": 1,
                "name": "Фильм 1",
                "start": "2021-04-14",
                "dateEnd": "2021-04-14",
                "finished": false
            },
           ...
        ]
       ```
4. GET Получить список сеансов в интервал дат 
```/hall/{id зала}/filmsession?dateFrom={Дата с}&dateTo={Дата по}``` даты в формате yyyy-MM-dd 
    ```
    [
    {
        "id": 7,
        "name": "Фильм 7",
        "start": "2021-04-14",
        "dateEnd": "2021-04-14",
        "finished": false
    },
    {
        "id": 8,
        "name": "Фильм 8",
        "start": "2021-04-13",
        "dateEnd": "2021-04-13",
        "finished": true
    }
    ]
    ```    

5. GET Места на сеансе 
    ```filmsession/{id сеанса}/places```
    ```    [
        {
            "id": 1,
            "row": 1,
            "placeNum": 1,
            "free": true
        },
            ...  
        ]        
    ```
6. POST Бронирование места 
    ```place/book ```
    ```
   {
        "phone":"791176",
        "places":[5,6]
    }
    ```
    Где в теле запроса phone телефон пользователя,
    массив places, с идентификаторами мест
    Результат выполнения status 200 или сообщение об ошибке в поле error
    ```
    {
        "error": "Место 2 в ряду 2 уже забронировано"
    }
    ```
7. POST Отмена бронирования
    ```place/unbook```
Тело запроса аналогично бронированию

8. GET Получение списка бронирований
    ```place/booked?phone={номер телефона пользователя}```
    ```
    [
    {
        "id": 5,
        "row": 2,
        "placeNum": 2,
        "free": false
    },...
    ]
    ```     
