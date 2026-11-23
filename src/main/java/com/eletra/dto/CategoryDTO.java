package com.eletra.dto;

public class CategoryDTO {
    private Integer id;
    private String name;
    private Integer lineId;

    public CategoryDTO() {}

    public CategoryDTO(Integer id, String name, Integer lineId) {
        this.id = id;
        this.name = name;
        this.lineId = lineId;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public Integer getLineId() { return lineId; }
}
