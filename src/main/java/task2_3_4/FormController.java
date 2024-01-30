package task2_3_4;

/* Завдання 2
  Створіть контролер і HTML-форму, дані з якої записуватимуться у файл,
  а після відправлення форми буде виводитися повідомлення про успішну операцію та кількість записів за весь час роботи застосунку.
 */

/* Завдання 3
  Додайте на форму, яка реалізована в другому завданні,
  кнопку, після натискання на яку всі записи з файлу будуть виведені на сторінку.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас контролера, який обробляє запити з форми
@Controller
public class FormController {
    // Лічильник записів
    private int count = 0;

    // Метод, який повертає html форму при GET запиті
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("formData", new FormData());
        return "form";
    }

    // Метод, який обробляє дані з форми при POST запиті
    @PostMapping("/form")
    public String submitForm(@ModelAttribute FormData formData, Model model) {
        // Збільшуємо лічильник
        count++;
        // Записуємо дані з форми у файл
        try (PrintWriter writer = new PrintWriter(new FileWriter("data.txt", true))) {
            writer.println("Name: " + formData.getName());
            writer.println("Email: " + formData.getEmail());
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Додаємо дані до моделі
        model.addAttribute("name", formData.getName());
        model.addAttribute("email", formData.getEmail());
        model.addAttribute("count", count);
        // Повертаємо сторінку з повідомленням про успіх
        return "result";
    }

    @GetMapping("/show")
    public String show(Model model) {
        List<Record> records = getAllRecordsFromFile();
        model.addAttribute("records", records);
        return "route";
    }

    private List<Record> getAllRecordsFromFile() {
        List<Record> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String name = parts[1];
                    String email = reader.readLine().split(" ")[1];
                    records.add(new Record(name, email));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}