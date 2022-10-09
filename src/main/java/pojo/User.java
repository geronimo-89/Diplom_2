package pojo;

import io.qameta.allure.Step;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

public class User {

    @Getter
    @Setter
    private String email, password, name;
    public User() {
    }
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public static String generateRandomEmail() {
        return RandomStringUtils.randomAlphanumeric(16) + "@yandex.ru";
    }
    public static String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    @Step("Пользователь со всеми обязательными полями")
    public static User createRandom() {
        return new User(generateRandomEmail(), generateRandomString(), generateRandomString());
    }
    @Step("Пользователь без имени")
    public static User withoutName() {
        return new User(generateRandomEmail(), generateRandomString(), null);
    }
    @Step("Пользователь без почты")
    public static User withoutEmail() {
        return new User(null, generateRandomString(), generateRandomString());
    }
    @Step("Пользователь без пароля")
    public static User withoutPassword() {
        return new User(generateRandomEmail(), null, generateRandomString());
    }
    @Step("Пользователь для авторизации")
    public static User loginData(User user) {
        return new User(user.getEmail(), user.getPassword(), null);
    }

    @Step("Пользователь для смены пароля")
    public static User password() {
        return new User(null, generateRandomString(), null);
    }
}
