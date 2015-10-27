package edu.upc.eetac.dsa.grouptalk.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Hp on 2015-10-05.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAlreadyExistsException extends Exception {
}
