package com.rile.issuetracker.services;

import com.rile.issuetracker.services.dao.ProjectDao;
import com.rile.issuetracker.services.dao.ProjectDaoImpl;
import com.rile.issuetracker.services.dao.ProjectFollowerDao;
import com.rile.issuetracker.services.dao.ProjectFollowerDaoImpl;
import com.rile.issuetracker.services.dao.TicketAttachmentDao;
import com.rile.issuetracker.services.dao.TicketAttachmentDaoImpl;
import com.rile.issuetracker.services.dao.TicketCommentDao;
import com.rile.issuetracker.services.dao.TicketCommentDaoImpl;
import com.rile.issuetracker.services.dao.TicketDao;
import com.rile.issuetracker.services.dao.TicketDaoImpl;
import com.rile.issuetracker.services.dao.TicketFollowerDao;
import com.rile.issuetracker.services.dao.TicketFollowerDaoImpl;
import com.rile.issuetracker.services.dao.UserDao;
import com.rile.issuetracker.services.dao.UserDaoImpl;
import com.rile.issuetracker.services.security.PageProtectionFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.tapestry5.*;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.javascript.StackExtensionType;
import org.slf4j.Logger;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to configure and extend Tapestry, or to place your own service definitions.
 */
public class AppModule {

    public static void bind(ServiceBinder binder) {
        // Make bind() calls on the binder object to define most IoC services.
        // Use service builder methods (example below) when the implementation
        // is provided inline, or requires more initialization than simply
        // invoking the constructor.
        // binder.bind(MyServiceInterface.class, MyServiceImpl.class);
        binder.bind(UserDao.class, UserDaoImpl.class);
        binder.bind(TicketDao.class, TicketDaoImpl.class);
        binder.bind(TicketCommentDao.class, TicketCommentDaoImpl.class);
        binder.bind(TicketAttachmentDao.class, TicketAttachmentDaoImpl.class);
        binder.bind(TicketFollowerDao.class, TicketFollowerDaoImpl.class);
        binder.bind(ProjectDao.class, ProjectDaoImpl.class);
        binder.bind(ProjectFollowerDao.class, ProjectFollowerDaoImpl.class);
    }

    public static void contributeFactoryDefaults(
            MappedConfiguration<String, Object> configuration) {
        // The application version is incorporated into URLs for most assets. Web
        // browsers will cache assets because of the far future expires header.
        // If existing assets change (or if the Tapestry version changes) you
        // should also change this number, to force the browser to download new
        // versions. This overrides Tapesty's default (a random hexadecimal
        // number), but may be further overriden by DevelopmentModule or QaModule 
        // by adding the same key in the contributeApplicationDefaults method.
        configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
        configuration.override(SymbolConstants.PRODUCTION_MODE, false);
    }

    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration) {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).
        configuration.add(SymbolConstants.HMAC_PASSPHRASE,
                        new BigInteger(130, new SecureRandom()).toString(32));
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add("tapestry.start-page-name", "tracker");
    }

    // This will override the bundled bootstrap version and will compile it at runtime
    @Contribute(JavaScriptStack.class)
    @Core
    public static void overrideBootstrapCSS(OrderedConfiguration<StackExtension> configuration) {
        configuration.override(
                "bootstrap.css",
                new StackExtension(
                        StackExtensionType.STYLESHEET,
                        "context:mybootstrap/css/bootstrap.css"
                ),
                "before:tapestry.css"
        );
    }

    /**
     * Use annotation or method naming convention: <code>contributeApplicationDefaults</code>
     */
    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void setupEnvironment(MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
//		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "context:mybootstrap");
        configuration.add(SymbolConstants.MINIFICATION_ENABLED, true);
    }

    /*
     // This will override the bundled bootstrap version and will compile it at runtime
     @Contribute(JavaScriptStack.class)
     @Core
     public static void overrideBootstrapCSS(OrderedConfiguration<StackExtension> configuration)
     {
     configuration.override("bootstrap.css",
     new StackExtension(StackExtensionType.STYLESHEET, "context:mybootstrap/css/bootstrap.css"), "before:tapestry.css");
     }
     */
    /**
     * This is a service definition, the service will be named "TimingFilter". The interface, RequestFilter, is used within the RequestHandler service pipeline, which is built from
     * the RequestHandler service configuration. Tapestry IoC is responsible for passing in an appropriate Logger instance. Requests for static resources are handled at a higher
     * level, so this filter will only be invoked for Tapestry related requests.
     * <p/>
     * <p/>
     * Service builder methods are useful when the implementation is inline as an inner class (as here) or require some other kind of special initialization. In most cases, use the
     * static bind() method instead.
     * <p/>
     * <p/>
     * If this method was named "build", then the service id would be taken from the service interface and would be "RequestFilter". Since Tapestry already defines a service named
     * "RequestFilter" we use an explicit service id that we can reference inside the contribution method.
     */
    public RequestFilter buildTimingFilter(final Logger log) {
        return new RequestFilter() {
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException {
                long startTime = System.currentTimeMillis();

                try {
                    // The responsibility of a filter is to invoke the corresponding method
                    // in the handler. When you chain multiple filters together, each filter
                    // received a handler that is a bridge to the next filter.

                    return handler.service(request, response);
                } finally {
                    long elapsed = System.currentTimeMillis() - startTime;

                    log.info(String.format("Request time: %d ms", elapsed));
                }
            }
        };
    }

    /**
     * This is a contribution to the RequestHandler service configuration. This is how we extend Tapestry using the timing filter. A common use for this kind of filter is
     * transaction management or security. The @Local annotation selects the desired service by type, but only from the same module. Without @Local, there would be an error due to
     * the other service(s) that implement RequestFilter (defined in other modules).
     */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,
            @Local RequestFilter filter) {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.

        configuration.add("Timing", filter);
    }

    public void contributeComponentRequestHandler(OrderedConfiguration<ComponentRequestFilter> configuration) {
        configuration.addInstance("PageProtectionFilter", PageProtectionFilter.class);
    }
}
