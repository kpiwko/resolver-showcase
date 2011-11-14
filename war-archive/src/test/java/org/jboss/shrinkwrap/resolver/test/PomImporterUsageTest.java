package org.jboss.shrinkwrap.resolver.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.MavenImporter;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Shows how to let ShrinkWrap Maven resolver build the complete archive.
 *
 * FIXME: There should be a way how to disable the need to load effective pom.xml file. ShrinkWrap Resolver should be able to
 * get the information which file is being processed from IDE or CLI. See https://jira.codehaus.org/browse/SUREFIRE-790
 *
 * FIXME: For the moment, this works only in integration-test phase. There should be a way how to get emulate Maven packing
 * plugins to get to complete archive in the IDE and likely on CLI as well
 *
 * @author <a href="kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class PomImporterUsageTest {

    @Test
    @Ignore("Not working without integration-test phase")
    public void includeWebAppSources() {
        WebArchive war = ShrinkWrap.create(MavenImporter.class)
        // load Maven pom file to get information about dependencies, dependency management and remote repositories
        // also get more information about project packaging and type
                .loadEffectivePom("pom.xml")
                // add the build output (requires integration test phase)
                .importBuildOutput()
                // cast to the Web Archive representation
                .as(WebArchive.class);
    }

}
