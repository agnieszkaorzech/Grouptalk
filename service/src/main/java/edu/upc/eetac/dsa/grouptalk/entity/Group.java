package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.grouptalk.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Hp on 2015-10-28.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {
    @InjectLinks({
            @InjectLink(resource = GrouptalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Grouptalk Root API"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-groups", title = "Current groupss"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-groups", title = "Create group", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = GroupResource.class, method = "getGroup", style = InjectLink.Style.ABSOLUTE, rel = "self group", title = "Group", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
            @InjectLink(resource = GroupResource.class, method = "getGroups", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer groupss", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = GroupResource.class, method = "getGroups", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older groupss", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")}),
    })



    private List<Link> links;
    private String id;

    private String groupid;
    private String creator;

    private String theme;
    private String description;

    private long creationTimestamp;
    private long lastModified;


    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }



    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
