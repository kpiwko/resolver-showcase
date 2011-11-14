package org.jboss.shrinkwrap.resolver.showcase.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class ArchiveEqualityUtilTest {

    @Test
    public void testTheSameArchive() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addClasses(Object.class, Integer.class);

        ArchiveEqualityUtil.contentEquals(jar, jar);
    }

    @Test(expected = AssertionError.class)
    public void testTheDifferentArchive() {
        JavaArchive jar1 = ShrinkWrap.create(JavaArchive.class).addClasses(Object.class, Integer.class);
        JavaArchive jar2 = ShrinkWrap.create(JavaArchive.class).addClasses(Object.class, Long.class);

        ArchiveEqualityUtil.contentEquals(jar1, jar2);
    }

    @Test(expected = AssertionError.class)
    public void testTheDifferentContent() {
        JavaArchive jar1 = ShrinkWrap.create(JavaArchive.class).add(new StringAsset("foo"), "test.properties");
        JavaArchive jar2 = ShrinkWrap.create(JavaArchive.class).add(new StringAsset("bar"), "test.properties");

        ArchiveEqualityUtil.contentEquals(jar1, jar2);
    }
}
