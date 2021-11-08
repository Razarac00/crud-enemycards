package com.razarac.enemycrud.controllers;

import com.razarac.enemycrud.models.EnemyElement;
import com.razarac.enemycrud.models.PageModel;
import com.razarac.enemycrud.services.ElementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("elements")
@Api(tags = "Element Operations - tbd")
@Slf4j
public class ElementCrudController {

    @Autowired
    private ElementService elementService;

    /**
     * Delete element with this id everywhere
     * @param id the id of the element
     * @return empty body with a 204 response
     */
    @ApiOperation(
            value = "Delete an element",
            notes = "Deletes a specific element with this id. The effect cascades to all enemies using this element.",
            response = ResponseEntity.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Element deleted successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Malformed request"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No such element"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Invalid token, expired or tampered"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Apparently not your request")
    })
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteElement(@PathVariable String id) {

        try {
            final Long elementId = Long.valueOf(id);
            EnemyElement element = elementService.deleteElementById(elementId);

            if (element != null) {
                return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("Element deleted successfully");
            }
        } catch (NumberFormatException exception) {
            return ResponseEntity.badRequest().body("Malformed request");
        }

        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("No such element");
    }
}
