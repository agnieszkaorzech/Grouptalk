package edu.upc.eetac.dsa.grouptalk.auth;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.regex.Pattern;
import java.util.List;

/**
 * Created by Hp on 2015-10-14.
 */
public class AuthorizedResource {
    private String path;
    private List<String> methods;
    private Pattern pattern;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        pattern = Pattern.compile(path);
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
