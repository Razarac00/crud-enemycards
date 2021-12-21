package com.razarac.enemycrud.utils;

import com.razarac.enemycrud.mocks.MockElement;
import com.razarac.enemycrud.models.EnemyElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class JSONMapperTests {
    JSONMapper jsonMapper;
    MockElement mockElement;

    @BeforeEach
    public void setup() {
        jsonMapper = new JSONMapper();
        mockElement = new MockElement();
    }

    @Test
    void serialize_ReturnsEmptyString_OnEmpty() {
        // Arrange
        // Act
        // Assert
        assertEquals("\"\"", jsonMapper.serialize(""));
    }

    @Test
    void serialize_ReturnsJsonObject_ForEnemyElement() {
        // Arrange
        EnemyElement obj = mockElement.mockElement();
        String expected = "{\"id\":" + obj.getId() + ",\"name\":\"" + obj.getName() + "\"}";
        // Act

        String result = jsonMapper.serialize(obj);
        // Assert
        assertEquals(expected, result);
    }

}
