package edu.upc.eetac.dsa.grouptalk.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.grouptalk.entity.Group;
import edu.upc.eetac.dsa.grouptalk.entity.GroupCollection;

import java.sql.SQLException;

/**
 * Created by Hp on 2015-10-28.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GroupDAO {
    public Group createGroup(String id, String theme, String description) throws SQLException;
    public Group getGroupById(String id) throws SQLException;
    //public GroupCollection getGroups() throws SQLException;
    public Group updateGroup(String s, String id, String theme) throws SQLException;
    public boolean deleteGroup(String id) throws SQLException;


}
