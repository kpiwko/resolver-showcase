package org.jboss.shrinkwrap.resolver.test;

import java.io.File;
import java.util.Collection;

import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyShortcut;
import org.jboss.shrinkwrap.resolver.api.maven.filter.DependencyFilter;
import org.jboss.shrinkwrap.resolver.api.maven.filter.StrictFilter;
import org.jboss.shrinkwrap.resolver.showcase.test.ArchiveValidationUtil;
import org.jboss.shrinkwrap.resolver.showcase.test.FileValidationUtil;
import org.junit.Test;

/**
 * This class how can to resolve a single artifact without transitive dependencies and default Maven configuration, that is the
 * one from ~/.m2/settings.xml and Maven Central enabled.
 *
 * All the ways lead to the same result in this scenario.
 *
 * @author <a href="kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class SingleDependencyUsageTest {

    @Test
    public void getJUnitJarAsArchives() {
        Collection<GenericArchive> junitDeps = DependencyResolvers.use(MavenDependencyResolver.class)
                .artifact("junit:junit:4.10")
                // StrictFilter causes only the dependencies defined explicitly to be resolved
                .resolveAs(GenericArchive.class, new StrictFilter());
        new ArchiveValidationUtil("junit").validate(junitDeps);
    }

    @Test
    public void getJUnitJarAsArchivesDifferentFilter() {
        Collection<GenericArchive> junitDeps = DependencyResolvers.use(MavenDependencyResolver.class)
                .artifact("junit:junit:4.10")
                // DependencyFilter allows only a dependencies with specified groupId, artifactId, version (optional)
                .resolveAs(GenericArchive.class, new DependencyFilter("junit:junit"));
        new ArchiveValidationUtil("junit").validate(junitDeps);
    }

    @Test
    public void getJUnitJarAsArchivesExclusionPattern() {
        Collection<GenericArchive> junitDeps = DependencyResolvers.use(MavenDependencyResolver.class)
                .artifact("junit:junit:4.10")
                // exclusion pattern disables all transitive dependecies
                .exclusion("*").resolveAs(GenericArchive.class);
        new ArchiveValidationUtil("junit").validate(junitDeps);
    }

    @Test
    public void getJUnitJarAsFiles() {
        File[] junitDeps = DependencyResolvers.use(MavenDependencyResolver.class).artifact("junit:junit:4.10")
        // StrictFilter causes only the dependencies defined via artifact() call to be resolved
                .resolveAsFiles(new StrictFilter());
        new FileValidationUtil("junit").validate(junitDeps);
    }

    @Test
    public void getJUnitJarShortcutApi() {
        GenericArchive junitDeps = DependencyResolvers.use(MavenDependencyShortcut.class).dependency("junit:junit:4.10");
        new ArchiveValidationUtil("junit").validate(junitDeps);
    }

    @Test
    public void getJUnitJarShortcut() {
        GenericArchive junitDeps = Maven.dependency("junit:junit:4.10");
        new ArchiveValidationUtil("junit").validate(junitDeps);
    }

}
