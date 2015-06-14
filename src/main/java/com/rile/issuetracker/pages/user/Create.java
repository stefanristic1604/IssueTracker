package com.rile.issuetracker.pages.user;

import com.rile.issuetracker.services.security.ProtectedPage;
import javax.annotation.security.RolesAllowed;

/**
 *
 * @author Stefan
 */
@ProtectedPage
@RolesAllowed(value = {"Admin"})
public class Create {
    
}
