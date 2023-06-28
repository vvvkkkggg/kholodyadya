## Требования 

Для сборки серверной части потребуется
- `gradle v7.5.1` 
- `Java 17`
- `Docker`

## Сборка

Построение образа и запуск `docker`-контейнера с базой данных

```bash
docker build -t kholodyadya-database-image <path_to_dockerfile>
docker run -d -p 5432:5432 --name kholodyadya-database kholodyadya-database-image
```

Запуск сервера

```bash
tmux new-session -s kholodyadya-server
gradle bootRun
```