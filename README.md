![Bot](https://github.com/Byte-Bosses/linktracker/actions/workflows/bot.yml/badge.svg)
![Scrapper](https://github.com/Byte-Bosses/linktracker/actions/workflows/scrapper.yml/badge.svg)

# Link Tracker

Проект подготовлен:
1. Гаврилов Д.С.
2. Гулякин Е.В.
3. Дроздов Н.А.
4. Крупнов Е.О.

Приложение для отслеживания обновлений контента по ссылкам.
При появлении новых событий отправляется уведомление в Telegram.

Проект написан на `Java 21` с использованием `Spring Boot 3`.

Проект состоит из 2-х приложений:
* Bot
* Scrapper

Для работы требуется БД `PostgreSQL`. Присутствует опциональная зависимость на `Kafka`.
