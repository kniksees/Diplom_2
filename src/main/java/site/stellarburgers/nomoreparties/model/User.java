package site.stellarburgers.nomoreparties.model;

import com.github.javafaker.Faker;
import org.apache.commons.lang.RandomStringUtils;

public class User {

    private String email;
    private String password;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String name){
        this.email = email;
        this.name = name;
    }

    public static User getRandomUser(){
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = RandomStringUtils.randomNumeric(10) + faker.internet().emailAddress(name);
        String password = faker.internet().password();
        return new User(email, password, name);
    }

    public static User getRandomUserWithOutPassword(){
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = name + RandomStringUtils.randomNumeric(10) + "@yandex.ru";
        return new User(email, name);
    }
}
