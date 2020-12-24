package ru.sberbank.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SberbankTest extends BaseTests {

    String baseUrl = "http://www.sberbank.ru/ru/person";

    @Test
    void orderACardTest() {
        //1. Перейти на страницу http://www.sberbank.ru/ru/person
        driver.get(baseUrl);

        //2. Нажать на меню – Карты
        String insuranceButtonXPath = "//a[contains(@aria-label,'Карты')]";
        WebElement cards = driver.findElement(By.xpath(insuranceButtonXPath));
        cards.click();

        //3. Выбрать подменю – «Дебетовые карты»
        String debitCardXPath = "//a[normalize-space()='Дебетовые карты']";
        WebElement debitCards = driver.findElement(By.xpath(debitCardXPath));
        debitCards.click();

        //4. Проверить наличие на странице заголовка – «Дебетовые карты»
        String debitCardsTittleXPath = "//h1";
        WebElement headerDebitCards = driver.findElement(By.xpath(debitCardsTittleXPath));
        waitUtilElementToBeVisible(headerDebitCards);
        assertEquals("Дебетовые карты", headerDebitCards.getText()
                , "Заголовок страницы не соответсвует ожидаемому/заголовок не найден");

        //5. Под заголовком из представленных карт найти “Молодёжная карта” и кликнуть на кнопку данной карты “Заказать онлайн”
        String orderOnlineXPath = "//a[@data-test-id='ProductCatalog_button' and @data-product='Молодёжная карта']/span";
        WebElement orderOnline = driver.findElement(By.xpath(orderOnlineXPath));
        scrollToElementJs(orderOnline);
        waitUtilElementToBeVisible(orderOnline);
        orderOnline.click();

        //6. Проверить наличие на странице заголовка – «Молодёжная карта»
        String headYouthCardXPaht = "//h1";
        WebElement headerYouthCard = driver.findElement(By.xpath(headYouthCardXPaht));
        assertEquals("Молодёжная карта",
                headerYouthCard.getText(),
                "Заголовок страницы не соответсвует ожидаемому/заголовок не найден");

        //7. Кликнуть на кнопку «Оформить онлайн» под заголовком
        String issueOnlineXPath = "//a[@data-test-id='PageTeaserDict_button']/span[text()='Оформить онлайн']";
        WebElement issueOnline = driver.findElement(By.xpath(issueOnlineXPath));
        scrollToElementJs(issueOnline);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitUtilElementToBeVisible(issueOnline);
        issueOnline.click();

        //8. В представленной форме заполнить поля:
        //    • Фамилию, Имя, Отчетво, Имя и фамилия на карте, Дату рождения, E-mail, Мобильный телефон


        String lastName = "Петров";
        String firstName = "Петр";
        String middleName = "Петрович";
        String email = "petr@mail.ru";
        String phoneNumber = "9111111111";
        String birthDate = "02.03.2000";
        String cardName = "PETR PETROV";


        WebElement lastNameElement = driver.findElement(By.xpath("//input[@data-name='lastName']"));
        WebElement firstNameElement = driver.findElement(By.xpath("//input[@data-name='firstName']"));
        WebElement middleNameElement = driver.findElement(By.xpath("//input[@data-name='middleName']"));
        WebElement cardNameElement = driver.findElement(By.xpath("//input[@data-name='cardName']"));
        WebElement emailElement = driver.findElement(By.xpath("//input[@data-name='email']"));
        WebElement phoneNumberElement = driver.findElement(By.xpath("//input[@data-name='phone']"));
        WebElement birthDateElement = driver.findElement(By.xpath("//input[@data-name='birthDate']"));


        lastNameElement.sendKeys(lastName);
        firstNameElement.sendKeys(firstName);
        middleNameElement.sendKeys(middleName);
        emailElement.sendKeys(email);
        cardNameElement.clear();
        cardNameElement.sendKeys(cardName);



        scrollToElementJs(phoneNumberElement);
        waitUtilElementToBeVisible(phoneNumberElement);
        phoneNumberElement.click();
        phoneNumberElement.sendKeys(phoneNumber);

        birthDateElement.sendKeys(birthDate);

        //9. Проверить, что все поля заполнены правильно
        Assertions.assertAll("Значения полей не соотвествуют ожидаемым значениям",
                () -> assertEquals(firstNameElement.getAttribute("value"), firstName),
                () -> assertEquals(firstNameElement.getAttribute("value"), firstName),
                () -> assertEquals(middleNameElement.getAttribute("value"), middleName),
                () -> assertEquals(emailElement.getAttribute("value"), email),
                () -> assertEquals(phoneNumberElement.getAttribute("value"), "+7 (911) 111-11-11"),
                () -> assertEquals(birthDateElement.getAttribute("value"), birthDate),
                () -> assertEquals(cardNameElement.getAttribute("value"), cardName));


        //10. Нажать «Далее»
        String buttonNext = "//span[text()='Далее']";
        WebElement closeCookie = driver.findElement(By.xpath("//button[@class='kitt-cookie-warning__close']"));
        WebElement buttonNextElement = driver.findElement(By.xpath(buttonNext));
        scrollToElementJs(buttonNextElement);
        waitUtilElementToBeClickable(buttonNextElement);
        closeCookie.click();
        buttonNextElement.click();

        //11. Проверить, что появилось сообщение именно у незаполненных полях – «Обязательное поле»
        String errorMassage = "Обязательное поле";
        String seriesErrorInputXPath = "//input[@data-name='series']/following-sibling::*";
        String numberErrorInputXPath = "//input[@data-name='number']/following-sibling::*";
        String dateErrorInputXPath = "//input[@data-name='issueDate']/parent::*/parent::*/parent::*/div[text()='Обязательное поле']";

        WebElement seriesErrorInputElement = driver.findElement(By.xpath(seriesErrorInputXPath));
        WebElement numberErrorInputElement = driver.findElement(By.xpath(numberErrorInputXPath));
        WebElement dateErrorInputElement = driver.findElement(By.xpath(dateErrorInputXPath));

        Assertions.assertAll("Отсутствует сообщение об ошибке",
                () -> assertEquals(seriesErrorInputElement.getText(), errorMassage),
                () -> assertEquals(numberErrorInputElement.getText(), errorMassage),
                () -> assertEquals(dateErrorInputElement.getText(), errorMassage));
    }
}





