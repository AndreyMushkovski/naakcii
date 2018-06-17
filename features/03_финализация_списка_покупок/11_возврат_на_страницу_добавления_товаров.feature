#language: ru
Функция: Возврат на страницу добавления товаров
  Как покупатель ищущий экономию
  Я хочу иметь возможность вернуться на страницу формирования списка
  Чтобы иметь возможность добавить дополнительно товары в список

Сценарий: возврат на страницу формирования списка
  Допустим я на странице "Список покупок – naakcii.by"
  И на панели "Список покупок" отображается следующая информация по сетям:
    |торговая сеть|товарные_позиции|количество|стоимость_без_скидки|скидка_руб._(%)|стоимость_со_скидкой|дата_окончания_акции
    |Виталюр      |      2         |    6     |       74,06        |  17,00 (23%)  |        57,06       |        18 мая      |
    |Белмаркет    |      3         |    13    |       44,55        |  6,48  (15%)  |        38,07       |        18 мая      |
    |Соседи       |      3         |    7     |       20,56        |  3,03 (15 %)  |        17,53       |        18 мая      |
  И на панели "Список покупок" отображаются следующие неакционные товары:
    |неакционные_товары|количество|
    |Картошка, 1кг     |  1       |
    |Корм для кошки    |  1       |
  И в строке "Итого" на панели "Список покупок" отображается следующая информация:
    |текст |товарные_позиции|количество|стоимость_без_скидки|скидка_руб._(%)|стоимость_со_скидкой|
    |Итого:|      8         |    26    |       139,17       |  26,51(19 %)  |        112,66 руб. |
  Если я нажимаю на кнопку "Вернуться к товарам" на панели "Список покупок"
  То должна открыться страница "Формирование списка покупок – naakcii.by"
  И в адресной строке браузера должен отобразиться адрес "http://178.124.206.54/form-shopping-list/"
    # Должно быть: И в адресной строке браузера должен отобразиться адрес "http://naakcii.by/form-shopping-list/
  И на фильтре "Торговые сети" должен отобразиться текст "Выбраны торговые сети: 3"
  И на панели "Список категорий" должны отобразиться следующие категории с соответствующими статусами:
    | категория                 | статус      |
    | Молочные продукты, яйца   | Выбрана     |
    | Хлебобулочные изделия     | Не выбрана  |
    | Овощи и фрукты            | Не выбрана  |
    | Мясо и колбасные изделия  | Не выбрана  |
    | Напитки, кофе, чай, соки  | Не выбрана  |
    | Бакалея                   | Не выбрана  |
    | Рыба и морепродукты       | Не выбрана  |
  И на панели "Список подкатегорий" должны отобразиться следующие подкатегории с соответствующими статусами:
    | подкатегория          | статус  |
    | Все                   | Выбрана |
    | Кисломолочные изделия | Выбрана |
    | Масло                 | Выбрана |
    | Молоко                | Выбрана |
    | Мороженое             | Выбрана |
    | Сметана               | Выбрана |
    | Сыр                   | Выбрана |
    | Творожные продукты    | Выбрана |
    | Яйцо                  | Выбрана |
  И на панели "Список акционных товаров" должны отобразиться следующие карточки товаров:
    | акционный_товар                                                                         | торговая_сеть |
    | Йогурт "Оптималь" Персик, Чернослив-злаки, Черника-Малина 2% жирность 350гр             | Белмаркет     |
    | Йогурт Савушкин 2%, 120г                                                                | Виталюр       |
    | Йогурт питьевой "Теос" 300г                                                             | Соседи        |
    | Кефир "Берёзка" 1,5% 950г                                                               | Соседи        |
    | Кефир "Минская Марка" 1.5% 900гр.                                                       | Белмаркет     |
    | Коктейль йогуртный "Даниссимо" 260г                                                     | Соседи        |
    | Продукт йогуртный "Нежный" 95г                                                          | Соседи        |
    | Продукт йогуртный Нежный с пюре 0,1%, 100г клубника/яблоко/абрикос                      | Виталюр       |
    | Продукт кисломолочный "Экспонента" 100г                                                 | Соседи        |
    | Масло сливочное "Nadivo" 72.5% 180г                                                     | Белмаркет     |
    | Масло сливочное "Крестьянское" 72,5% 180г                                               | Соседи        |
    | Молоко "Берёзка" 1,5% 950мл                                                             | Соседи        |
  И на панели "Список покупок" должен отобразиться следующий текст "Итог: 112,66 руб."
  И на панели "Список покупок" должны отобразиться следующие товары:
    | акционный_товар               |количество |
    | Кефир "Минская Марк...        | 4         |
    | Кефир "Берёзка" 1,5...        | 2         |
    | Булочка Маковая, 10...        | 2         |
    | Сдоба "Мадлен" 75гр           | 6         |
    | Хлеб "Литвинский" 4...        | 3         |
    | Колбаса оригинальна...        | 4         |
    | П/ф фарш "Сельский"...        | 2         |
    | Окорочок "домашний ...        | 3         |
  И должна отобразиться кнопка "Перейти к списку"
  Если я нажимаю на товар "Булочка Маковая, 10..." на панели "Список покупок"
  То товар "Булочка Маковая, 10..." должен раскрыться на панели "Список покупок"
  И  на панели "Список покупок" должна отобразиться следующая информация для товара "Булочка Маковая, 10...":
    | информация              |
    | Булочка Маковая, 100гр  |
    | Торговая сеть "Виталюр" |
    | Цена на акции 0,55 руб. |
    | % скидки 15 %           |
    | Итог 1,10 руб.          |
    | Убедись что свежая!     |