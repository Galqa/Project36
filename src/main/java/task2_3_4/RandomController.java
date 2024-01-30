package task2_3_4;

/* Завдання 4
Створити новий контролер з різним мапінгом,
який буде приймати тільки Get-запити та віддавати випадково згенеровану інформацію в JSON-форматі.
Наприклад, для мапінгу «/uuid» виводити випадково згенерований uuid,
для мапінгу «/exchange» виводити випадкове значення курсу валют або для «/exchange/{currency}» виводити значення курсу валюти,
переданої в змінному шляху «currency».
Для реалізації завдання потрібно буде скористатися анотацією @ResponseBody.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RandomController {

    @GetMapping("/uuid")
    @ResponseBody
    public String getRandomUUID() {
        return "{\"uuid\": \"" + UUID.randomUUID().toString() + "\"}";
    }

    @GetMapping("/exchange")
    @ResponseBody
    public String getRandomExchangeRate() {
        // наприклад курс валют між USD та EUR знаходиться в діапазоні від 0,8 до 0,9
        double rate = 0.8 + Math.random() * 0.1;
        return "{\"rate\": " + rate + "}";
    }

    @GetMapping("/exchange/{currency}")
    @ResponseBody
    public String getExchangeRate(@PathVariable String currency) {
        // наприклад курси валют для USD, EUR та GBP
        double rate = 0;
        switch (currency) {
            case "USD":
                rate = 1;
                break;
            case "EUR":
                rate = 0.85;
                break;
            case "GBP":
                rate = 0.72;
                break;
            default:
                return "{\"error\": \"Unknown currency\"}";
        }
        return "{\"rate\": " + rate + "}";
    }
}
