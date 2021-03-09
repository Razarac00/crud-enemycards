package com.razarac.enemycrud.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageModel {
    private List<Enemy> content;

    private Integer pageTotal, pageSize, pageNumber;

    private Integer enemyTotal, enemyOffset;

    private Boolean hasNext, hasPrevious;

    public PageModel(List<Enemy> content, 
                    Integer pageTotal, Integer pageSize, Integer pageNumber, 
                    Integer enemyTotal, Integer enemyOffset, 
                    Boolean hasNext, Boolean hasPrevious) {
        this.content = content;
        
        this.pageTotal = pageTotal;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;

        this.enemyTotal = enemyTotal;
        this.enemyOffset = enemyOffset;

        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public PageModel() {
    }
}
