package com.rile.issuetracker.pages.user.controlpanel;

import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.UserDao;
import com.rile.issuetracker.services.security.ProtectedPage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Stefan
 */
@ProtectedPage
@RolesAllowed(value = {"Admin"})
public class Admin {

    @Property
    @SessionState
    private User loggedInUser;

    @Property
    private User userP1;
    @Property
    private Role userRoleP1;
    @Property
    private List<User> users;
    @Property
    @Inject
    private UserDao userDao;

    void onActivate() {
        users = userDao.loadAll();
    }

    @CommitAfter
    void onActionFromChangeRole(User user, Role newRole) {
        user.setRole(newRole);
        userDao.merge(user);
    }

    @CommitAfter
    void onActionFromDeleteUser(int userId) {
        userDao.delete(userId);
    }

    public List getRoleEnums() {
        List roles = new ArrayList();
        for (Role role : Role.values()) {
            if (role == Role.Guest) {
                continue;
            } else if (role == Role.Admin) {
                break;
            } else {
                roles.add(role);
            }
        }
        return roles;
    }

}
