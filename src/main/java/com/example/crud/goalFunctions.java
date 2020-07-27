package com.example.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class goalFunctions
{
    Logger logger= LoggerFactory.getLogger(GController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<goal> list() {
        String sql = "SELECT * FROM goal";
        List<goal> li= jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(goal.class));
        return li;
    }

    public void save(goal g){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("goal").usingColumns("goalId", "title", "details","eta","createDate","updateDate");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(g);
        insertActor.execute(param);
    }

    public goal get(Integer goalId) throws GException{
        try {
            String sql = "SELECT * FROM goal WHERE goalId = ?";
            Object[] args = {goalId};
            goal g = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(goal.class));
            return g;
        }
        catch (Exception e){
            logger.info("Goal with id {} doesn't exists",goalId);

            throw new GException("Goal Details Doesn't Exits");
        }
    }
    //method to check if a goal exists
    public goal check(Integer GoalId) {
        try{
            String sql = "SELECT * FROM goal WHERE goalId = ?";
            Object[] args = {GoalId};
            logger.info("Running sql query to check if goal exists");
            goal goal = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(goal.class));
            return goal;
        }
        catch(Exception e){
            return null;
        }
    }

    public goal delete(Integer goalId) throws GException {
        try{
            String sql = "SELECT * FROM goal WHERE goalId = ?";
            Object[] args = {goalId};
            goal g = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(goal.class));

            String del = "DELETE FROM goals WHERE GoalId = ?";
            jdbcTemplate.update(del, goalId);
            return g;
        }
        catch (Exception e){
            logger.info("Goal with id {} doesn't exists",goalId);
            throw new GException("Goal Details Doesn't Exits");
        }
    }

    public void update(goal g, Integer goalId) throws GException {
        try{
            String sql = "UPDATE goal SET title=:title, details=:details, eta=:eta, createDate=createDate, updateDate=updateDate WHERE GoalId=:GoalId";
            BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(g);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
            template.update(sql, param);
        }
        catch(Exception e){
            logger.info("Goal with id {} doesn't exists",goalId);
            throw new GException("Goal Details Can't Be Updated");
        }
    }
}
