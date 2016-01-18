package com.github.kropp.gitversion;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @author Victor Kropp
 */
public class Version {
    private final int major;
    private final int minor;
    private final int build;
    private final boolean dirty;

    public static Version EMPTY = new Version(0, 0, 0, false);
    public static Version INITIAL = new Version(0, 1, 0, false);

    public Version(int major, int minor, int build, boolean dirty) {
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.dirty = dirty;
    }

    public Version(String version, boolean dirty) {
        this.dirty = dirty;
        int major = 0;
        int minor = 0;
        int build = 0;
        try {
            StringTokenizer st = new StringTokenizer(version, ".", false);
            major = Integer.parseInt(st.nextToken());
            if (st.hasMoreTokens()) {
                minor = Integer.parseInt(st.nextToken());
                if (st.hasMoreTokens()) {
                    build = Integer.parseInt(st.nextToken());
                }
            }
        } catch (NoSuchElementException ignored) {
        }

        this.major = major;
        this.minor = minor;
        this.build = build;
    }

    public boolean isDirty() {
        return dirty;
    }

    public Version incBuild() {
        return new Version(major, minor, build + 1, dirty);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + build;
    }
}
