package com.razarac.enemycrud.models;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
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

    @Override
    public String toString() {
        return "PageModel{" +
                "content=" + content +
                ", pageTotal=" + pageTotal +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", enemyTotal=" + enemyTotal +
                ", enemyOffset=" + enemyOffset +
                ", hasNext=" + hasNext +
                ", hasPrevious=" + hasPrevious +
                '}';
    }
}
