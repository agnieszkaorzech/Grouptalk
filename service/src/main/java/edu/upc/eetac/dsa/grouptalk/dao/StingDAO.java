package edu.upc.eetac.dsa.grouptalk.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.grouptalk.entity.Sting;
import edu.upc.eetac.dsa.grouptalk.entity.StingCollection;

import java.sql.SQLException;

/**
 * Created by Hp on 2015-10-07.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface StingDAO {
    public Sting createSting(String userid, String subject, String content) throws SQLException;
    public Sting getStingById(String id) throws SQLException;
    public StingCollection getStings(long timestamp, boolean before) throws SQLException;
    //public StingCollection getStings(long before) throws SQLException;
    //public StingCollection getStings() throws SQLException;
    public Sting updateSting(String id, String subject, String content) throws SQLException;
    public boolean deleteSting(String id) throws SQLException;
    //public StingCollection getStingsAfter(long after) throws SQLException;
}
