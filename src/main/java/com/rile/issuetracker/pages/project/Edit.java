package com.rile.issuetracker.pages.project;

import com.rile.issuetracker.services.security.ProtectedPage;
import javax.annotation.security.RolesAllowed;

/**
 *
 * @author Stefan
 */
@ProtectedPage
@RolesAllowed(value = {"Admin", "Moderator", "Architect"})
public class Edit {
    
}
