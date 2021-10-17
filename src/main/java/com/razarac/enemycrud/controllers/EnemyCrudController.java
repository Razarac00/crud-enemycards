package com.razarac.enemycrud.controllers;

import com.razarac.enemycrud.models.Enemy;
import com.razarac.enemycrud.models.PageModel;
import com.razarac.enemycrud.services.EnemyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.razarac.enemycrud.utils.Constants.JSONCHARSET;

@RestController
@Api(tags = "Enemy Operations - fetch one or more enemies")
public class EnemyCrudController {

    @Autowired
    private EnemyService enemyService;

    /**
     * Get all enemies within a page
     * @param search filter for the search query
     * @param pageNumber the page of enemies requested
     * @param pageSize the size of the page--the number of enemies--requested
     * @return the specified page of enemies with respect to the search query
     */
    @ApiOperation(
            value = "Fetch a page of enemies",
            notes = "Search for a set of enemies and receive a page of them w.r.t the page size and the page number.",
            response = PageModel.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Enemies fetched successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Malformed request"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Invalid token, expired or tampered"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Apparently not your request")
    })
    @GetMapping(value = "/enemies", produces = JSONCHARSET)
    public PageModel getEnemies(
        @RequestParam(value = "search", required = false) String search, 
        @RequestParam("pageNumber") Integer pageNumber, 
        @RequestParam("pageSize") Integer pageSize) {
        
        if (search == null) {
            search = "";
        }
        return enemyService.getEnemies(search, pageSize, pageNumber);
    }

    /**
     * Get one enemy by name
     * @param name the name of the enemy
     * @return the first enemy with this name
     */
    @ApiOperation(
            value = "Fetch a single enemy",
            notes = "Use to get the first enemy with this name.",
            response = Enemy.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Enemy fetched successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Malformed request"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Invalid token, expired or tampered"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Apparently not your request")
    })
    @GetMapping(value = "/enemies/{name}", produces = JSONCHARSET)
    public Enemy getEnemy(@PathVariable("name") String name) {

        return enemyService.getEnemy(name);
    }
}
