package com.rile.issuetracker.pages.user;

import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.UserDao;
import com.rile.issuetracker.util.Util;
import java.util.Objects;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 *
 * @author Stefan
 */
public class View {

    @Property
    @SessionState
    private User loggedInUser;
    
    @Property
    private User user;
    @Property
    @Inject
    private UserDao userDao;
    
    @Inject
    private PageRenderLinkSource renderLinkSource;
    @Property
    private Util util = new Util();
    
    @PageLoaded
    void onPageLoad() {}
    
    public boolean getLoggedIn() {
        return loggedInUser.getEmail() != null;
    }  
    
    public boolean getHasActionPriviligies() {
        return getLoggedIn() && Objects.equals(loggedInUser.getId(), user.getId());
    }
    
    void onActivate(Integer userId) {        
        user = userDao.getByID(userId);
    }
    
    public String getUserAvatar(User user) {
        return user.getAvatarPath() == null || user.getAvatarPath().isEmpty() ?
                "user-avatar.png" : user.getAvatarPath();
    }
    
}
