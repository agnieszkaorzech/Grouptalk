package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Group;

import java.sql.SQLException;

/**
 * Created by Hp on 2015-10-28.
 */
public class GroupDAOImpl implements GroupDAO {
    @Override
    public Group createGroup(String theme, String description) throws SQLException {
        Group group = new Group();
        return group;
    }
}
