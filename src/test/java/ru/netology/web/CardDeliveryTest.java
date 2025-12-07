package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    public String generateDate(int days, String pattern) { //
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSendAForm() {
        String planningDate = generateDate(4, "dd.MM.yyyy"); // создаём планируемую дату через 4 дня по маске дд.ММ.гггг

        Selenide.open("http://localhost:9999"); // запускаем тестируемый сервис
        $("[data-test-id='city'] input") // находим поле ввода города
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE) // очищаем строку от возможного введённого значения
                .setValue("Пенза"); // вводим город
        $("[data-test-id='date'] input") // находим поле ввода даты
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE) // очищаем строку от возможного введённого значения
                .setValue(planningDate); // вводим планируемую дату
        $("[data-test-id='name'] input") // находим поле ввода имени
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE) // очищаем строку от возможного введённого значения
                .setValue("Петров Иван"); // вводим фамилию и имя
        $("[data-test-id='phone'] input") // находим поле ввода телефона
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE) // очищаем строку от возможного введённого значения
                .setValue("+79876543210"); // вводим номер телефона
        $("[data-test-id='agreement']").click(); // находим чек-бокс согласия и кликаем по нему
        $$("button").filter(Condition.visible).find(text("Забронировать")).click(); // находим все кнопки, отфильтровываем видимые, находим кнопку с текстом "Забронировать" и кликаем
        $("[data-test-id='notification']") // находим уведомление
                .should(Condition.visible, Duration.ofSeconds(15)) // оно должно быть видимым по истечении не более 15 секунд
                .should(Condition.text("Встреча успешно забронирована на " + planningDate)); // должно иметь указанный тест плюс запланированная дата
    }

//    @Test
//    void shouldSendAFormWithPopUpMenues() {
//        String planningDate = generateDate(7, "dd.MM.yyyy");
//
//        Selenide.open("http://localhost:9999");
//        $("[data-test-id='city'] input")
//                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
//                .setValue("Пе"); // вводим две буквы названия города
//        $$(".menu-item__control").find(Condition.text("Пенза")).click(); // находим селекторы и фильтруем по тексту "Пенза", кликаем
//        $(".icon_name_calendar").click() // вызываем виджет кадендаря
//
//                .setValue(planningDate);
//        $("[data-test-id='name'] input")
//                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
//                .setValue("Петров Иван");
//        $("[data-test-id='phone'] input")
//                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
//                .setValue("+79876543210");
//        $("[data-test-id='agreement']").click();
//        $$("button").filter(Condition.visible).find(text("Забронировать")).click();
//        $("[data-test-id='notification']")
//                .should(Condition.visible, Duration.ofSeconds(15))
//                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
//    }
}
