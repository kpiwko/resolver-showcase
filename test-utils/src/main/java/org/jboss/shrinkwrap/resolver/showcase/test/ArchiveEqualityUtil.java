package org.jboss.shrinkwrap.resolver.showcase.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.asset.Asset;

public class ArchiveEqualityUtil {

    public static void contentEquals(Archive<?> original, Archive<?> clone) throws AssertionError {
        Map<ArchivePath, Node> originalContent = original.getContent();
        Map<ArchivePath, Node> cloneContent = clone.getContent();

        if (originalContent.size() != cloneContent.size()) {
            throw new AssertionError(constructDifferenceDescription(original, clone));
        }

        for (ArchivePath path : originalContent.keySet()) {
            if (!equals(original.get(path), clone.get(path))) {
                throw new AssertionError(constructDifferenceDescription(original, clone));
            }
        }
    }

    private static String constructDifferenceDescription(Archive<?> original, Archive<?> clone) {

        Map<ArchivePath, Node> originalContent = original.getContent();
        Map<ArchivePath, Node> cloneContent = clone.getContent();

        StringBuilder sb = new StringBuilder();

        sb.append(original.getName()).append(" contains extra/different:\n");
        for (ArchivePath path : originalContent.keySet()) {
            if (!equals(original.get(path), clone.get(path))) {
                sb.append(path.get()).append("\n");
            }
        }

        sb.append(clone.getName()).append(" contains extra/different:\n");
        for (ArchivePath path : cloneContent.keySet()) {
            if (!equals(clone.get(path), original.get(path))) {
                sb.append(path.get()).append("\n");
            }
        }

        return sb.toString();
    }

    private static boolean equals(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null && node2 != null) {
            return false;
        } else if (node1 != null && node2 == null) {
            return false;
        }

        if (node1.getChildren().size() != node2.getChildren().size()) {
            return false;
        }

        Asset asset1 = node1.getAsset();
        Asset asset2 = node2.getAsset();

        if (asset1 == null && asset2 == null) {
            return true;
        } else if (asset1 == null && asset2 != null) {
            return false;
        } else if (asset1 != null && asset2 == null) {
            return false;
        }

        InputStream is1 = null;
        InputStream is2 = null;
        try {
            is1 = asset1.openStream();
            is2 = asset2.openStream();
            return contentEquals(is1, is2);
        } catch (IOException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        } finally {
            try {
                is1.close();
            } catch (IOException e) {
            }
            try {
                is2.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * * Origin of code: Apache Avalon (Excalibur)
     *
     * @author Peter Donald
     * @author Jeff Turner
     * @author Matthew Hawthorne
     * @author Stephen Colebourne
     * @author Gareth Davis
     */
    private static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }

        int ch = input1.read();
        while (-1 != ch) {
            int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }

        int ch2 = input2.read();
        return (ch2 == -1);
    }

}