package com.eletra.dto;

public class ModelDTO {
    private final Integer id;
    private final String name;
    private final Integer categoryId;

    public ModelDTO(Integer id, String name, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() { return name; }
}
