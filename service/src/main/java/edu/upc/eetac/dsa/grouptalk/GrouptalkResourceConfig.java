package edu.upc.eetac.dsa.grouptalk;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Hp on 2015-09-30.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrouptalkResourceConfig extends ResourceConfig {
    public GrouptalkResourceConfig() {
        packages("edu.upc.eetac.dsa.grouptalk");
        packages("edu.upc.eetac.dsa.grouptalk.auth");
        packages("edu.upc.eetac.dsa.grouptalk.cors");

        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
    }

}