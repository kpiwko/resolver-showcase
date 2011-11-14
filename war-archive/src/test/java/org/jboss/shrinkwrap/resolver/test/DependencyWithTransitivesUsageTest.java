package org.jboss.shrinkwrap.resolver.test;

import java.io.File;
import java.util.Collection;

import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyShortcut;
import org.jboss.shrinkwrap.resolver.showcase.test.ArchiveValidationUtil;
import org.jboss.shrinkwrap.resolver.showcase.test.FileValidationUtil;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class how can to resolve an artifact including transitive dependecies and default Maven configuration, that is the one
 * from ~/.m2/settings.xml and Maven Central enabled.
 *
 * All the ways lead to the same result in this scenario.
 *
 * @author <a href="kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class DependencyWithTransitivesUsageTest {

    @Test
    public void getJUnitJarsAsArchives() {
        Collection<GenericArchive> junitDeps = DependencyResolvers.use(MavenDependencyResolver.class)
        // no filter defined, get the transitive dependency tree as well
                .artifact("junit:junit:4.10").resolveAs(GenericArchive.class);
        new ArchiveValidationUtil("junit", "hamcrest").validate(junitDeps);
    }

    @Test
    public void getJUnitJarsAsFiles() {
        File[] junitDeps = DependencyResolvers.use(MavenDependencyResolver.class).artifact("junit:junit:4.10")
        // no filter defined, get the transitive dependency tree as well
                .resolveAsFiles();
        new FileValidationUtil("junit", "hamcrest").validate(junitDeps);
    }

    @Test
    @Ignore("Not possible with Shortcut API")
    public void getJUnitJarsShortcutApi() {
        GenericArchive junitDeps = DependencyResolvers.use(MavenDependencyShortcut.class).dependency("junit:junit:4.10");
        new ArchiveValidationUtil("junit", "hamcrest").validate(junitDeps);
    }

    @Test
    @Ignore("Not possible with Shortcut API")
    public void getJUnitJarsShortcut() {
        GenericArchive junitDeps = Maven.dependency("junit:junit:4.10");
        new ArchiveValidationUtil("junit", "hamcrest").validate(junitDeps);
    }
}
