package org.jboss.shrinkwrap.resolver.test;

import java.io.File;

import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyShortcut;
import org.jboss.shrinkwrap.resolver.api.maven.filter.StrictFilter;
import org.jboss.shrinkwrap.resolver.showcase.test.ArchiveValidationUtil;
import org.jboss.shrinkwrap.resolver.showcase.test.FileValidationUtil;
import org.junit.Test;

public class IndenpendentUsageTest {

    @Test
    public void getJUnitJarDefault() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).useCentralRepo(true);

        File[] junitDeps = resolver.artifact("junit:junit:4.10").resolveAsFiles(new StrictFilter());
        new FileValidationUtil("junit").validate(junitDeps);
    }

    @Test
    public void getJUnitJarShortcut() {
        MavenDependencyShortcut resolver = DependencyResolvers.use(MavenDependencyShortcut.class);

        GenericArchive junitDeps = resolver.dependency("junit:junit:4.10");
        new ArchiveValidationUtil("junit").validate(junitDeps);
    }
}
