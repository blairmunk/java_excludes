import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) throws IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в следующем порядке, разделенные пробелом:");
        System.out.println("Фамилия Имя Отчество ДатаРождения(dd.mm.yyyy) НомерТелефона(только цифры, без пробелов) Пол(m/f)");
        System.out.println("Например: Серегина Анастасия Валерьевна 01.02.2003 79999999999 f");

        String input = scanner.nextLine();

        String[] data = input.split(" ");

        if (data.length != 6) {
            System.out.println("Ошибка: введено неверное количество данных");
            return;
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String birthDate = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        try {
            validateBirthDate(birthDate); // Проверка формата даты рождения
            validatePhoneNumber(phoneNumber); // Проверка формата номера телефона
            validateGender(gender); // Проверка формата пола
            writeUserDataToFile(lastName, firstName, middleName, birthDate, phoneNumber, gender);
            System.out.println("Данные успешно записаны в файл");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Wrong BirthDate")) {
                System.out.println("Ошибка: неверный формат даты рождения");
            } else if (e.getMessage().equals("Wrong PhoneNumber")) {
                System.out.println("Ошибка: неверный формат номера телефона");
            } else if (e.getMessage().equals("Wrong Gender")) {
                System.out.println("Ошибка: неверный формат пола");
            } else {
                System.out.println("Ошибка: неверный формат данных");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи данных в файл:");
            e.printStackTrace();
        }
    }


    private static void validateBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate.parse(birthDate, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong BirthDate");
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Wrong PhoneNumber");
        }
    }

    private static void validateGender(String gender) {
        if (!gender.equals("f") && !gender.equals("m")) {
            throw new IllegalArgumentException("Wrong Gender");
        }
    }

    private static void writeUserDataToFile(String lastName, String firstName, String middleName,
                                            String birthDate, String phoneNumber, String gender) throws IOException {
        String fileName = lastName + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

        String userData = lastName + ' ' + firstName + ' ' + middleName + ' ' + birthDate + " " + phoneNumber + " " + gender;

        writer.write(userData);
        writer.newLine();
        writer.close();
    }
}