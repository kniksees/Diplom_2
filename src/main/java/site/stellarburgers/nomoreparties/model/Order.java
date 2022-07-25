package site.stellarburgers.nomoreparties.model;

import org.apache.commons.lang.RandomStringUtils;

import java.util.List;

public class Order {

    private List<String> ingredients;

    public List<String> getIngredients() {

        return ingredients;
    }

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredients(List<String> ingredients) {

        this.ingredients = ingredients;
    }

    public static Order getOrder() {
        List<String> list = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        return new Order(list);
    }

    public static Order getOrderWithBadHash() {
        List<String> list = List.of("61c0c5a71d1f82001bdaaa6d1", "61c0c5a71d1f82001bdaaa6f2");
        return new Order(list);
    }

    public static Order getOrderWithOutIngredients() {
        List<String> list = null;
        return new Order(list);
    }

}
