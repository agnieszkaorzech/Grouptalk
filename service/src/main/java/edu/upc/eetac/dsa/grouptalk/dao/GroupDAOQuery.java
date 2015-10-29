package edu.upc.eetac.dsa.grouptalk.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Hp on 2015-10-28.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GroupDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_GROUP = "insert into groups (id, theme,description) values (UNHEX(?), ?, ?)";
    public final static String GET_GROUP_BY_ID = "select hex(g.id) as id, g.theme, g.description, from groups g where g.id=unhex(?)";
    public final static String GET_GROUPS = "select hex(id) as id, theme, description from groups";
    public final static String UPDATE_GROUP = "update groups set theme=?, description=? where id=unhex(?) ";
    public final static String DELETE_GROUP = "delete from groups where id=unhex(?)";
}
