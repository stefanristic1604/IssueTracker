package com.rile.issuetracker.pages.user.controlpanel;

import com.rile.issuetracker.services.security.ProtectedPage;
import javax.annotation.security.RolesAllowed;

/**
 *
 * @author Stefan
 */
@ProtectedPage
@RolesAllowed(value = {"Architect"})
public class Architect {
    
}
