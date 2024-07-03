package com.alura.forohub.model;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public enum Category {
    IA("IA"),
    FRONTEND("Front"),
    BACKEND("Back"),
    REDES("Red"),
    BLOCKCHAIN("Cripto");


    private final String category;

    Category(String category){
        this.category = category;
    }

    public static Category fromString(String value){

        for (Category category: Category.values()){
            if(category.getCategory().equalsIgnoreCase(value)){
                return category;
            }
        }

        throw new IllegalArgumentException("Category not found");
    }

    public String getCategory() {
        return category;
    }
}
