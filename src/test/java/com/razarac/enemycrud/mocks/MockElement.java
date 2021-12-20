package com.razarac.enemycrud.mocks;

import com.razarac.enemycrud.models.EnemyElement;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MockElement {

    public EnemyElement mockElement() {
        return EnemyElement.builder().id(200L).name("Frostbite").build();
    }

    public List<EnemyElement> mockElements() {
        return null;
    }
}
