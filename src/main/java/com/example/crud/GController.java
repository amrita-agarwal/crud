package com.example.crud;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@OpenAPIDefinition(
        info = @Info(
                title = "API for Goal",
                version = "1.0",
                description = "CRUD Goal API",
                license = @License(name = "Apache 2.0", url = "http://foo.bar"),
                contact = @Contact(url = "#", name = "Amrita", email = "amrita.agarwal@geminisolutions.com")
        )
)

@RestController
public class GController
{
    private final goalFunctions service;
    //to log files
    Logger logger= (Logger) LoggerFactory.getLogger(GController.class);

    //default
    @RequestMapping("/goals")
    public String index(){
        return "Greetings!";
    }

    public GController(goalFunctions service) {

        this.service = service;
    }

    //GET request
    @GetMapping("/goals")
    public ResponseEntity<Object> getAllGoals() {
        logger.info("Request received to fetch All Goals...");
        List<goal> listGoals = service.list();
        logger.info("All Goals fetched, returning data");
        return new ResponseEntity<Object>(listGoals, HttpStatus.OK);
    }
    @Operation(summary = "Get goal by goalId",
            responses = {
                    @ApiResponse(description = "The goal",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = goal.class))),
                    @ApiResponse(responseCode = "400", description = "Goal not found")})

    // GET request to out a goal detail via its ID.

    @GetMapping("/goals/{GoalId}")
    public ResponseEntity<Object> getGoalById(@PathVariable Integer GoalId){
        logger.info("Request received to fetch goal with goalId "+GoalId);
        goal goal=service.get(GoalId);
        if(goal==null){
            return new ResponseEntity<Object>("Goal doesn't exist", HttpStatus.OK);
        }
        logger.info("Fetched Goal Accessed");
        return new ResponseEntity<Object>(goal,HttpStatus.OK);
    }
    // POST request to CREATE a goal.

    @Parameter(name = "GoalName",description = "Id of the Goal",required = true)
    @Parameter(name = "GoalContent",description = "Content of the Goal",required = true)
    @Operation(summary = "Create a new Goal",description ="Rest Endpoint that returns a object after creating a goal")

    @PostMapping(value = "/goals")
    //PUT request to update a goal.
    public ResponseEntity<Object>  createGoal(@RequestBody goal goal) {
        logger.info("Request received to create a new goal with goalId "+goal.getGoalid());
        if(service.check(goal.getGoalid())==null) {
            service.save(goal);
            logger.info("Goal Created Successfully");
            return new ResponseEntity<Object>("Goal Created successfully", new HttpHeaders(), HttpStatus.CREATED);
        } else {
            logger.info("Goal Already Exists, Operation Unsuccessful");
            ResponseEntity<Object> goal_creation_unsuccessful = new ResponseEntity<>("Goal Creation Unsuccessful", new HttpHeaders(), HttpStatus.OK);
            return goal_creation_unsuccessful;
        }

    }
    @Operation(summary = "Update a content of Goal by GoalId", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = goal.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Goal not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})

    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.PUT)

    public ResponseEntity<Object>  updateGoal(@RequestBody goal newgoal, @PathVariable Integer GoalId){
        logger.info("Request received to update goal having goalId "+GoalId);
        if(service.get(GoalId)!=null) {
            service.update(newgoal,GoalId);
            logger.info("Goal Updated Successfully");
            return new ResponseEntity<Object>("Goal Updated Successfully",HttpStatus.OK);
        } else {
            logger.info("Operation Unsuccessful");
            return new ResponseEntity<Object>("Goal Update Unsuccessful", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    //DELETE request to delete a goal.
    @Operation(summary = "Delete a content of Goal by GoalId")
    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.DELETE)

    public ResponseEntity<Object>  deleteGoal(@PathVariable Integer GoalId){
        logger.info("Request received to delete goal");
        if(service.get(GoalId)!=null) {
            goal goal= service.delete(GoalId);
            logger.info("Goal Deleted Successfully");
            return new ResponseEntity<Object>(goal,HttpStatus.OK);
        } else {
            logger.info("Operation Unsuccessful");
            return new ResponseEntity<Object>("Goal Deletion Unsuccessful", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
}
