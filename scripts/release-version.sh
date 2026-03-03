#!/usr/bin/env -S java --source 17

import java.io.*;
import java.util.regex.*;

class ReleaseVersion {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: release-version.sh <release-version> <next-snapshot-version>");
            System.exit(1);
        }

        var releaseVersion = args[0];
        var nextVersion    = args[1];

        // ── Validate version formats ──────────────────────────────────────
        var semverRe   = Pattern.compile("^\\d+\\.\\d+\\.\\d+$");
        var snapshotRe = Pattern.compile("^\\d+\\.\\d+\\.\\d+-SNAPSHOT$");

        if (!semverRe.matcher(releaseVersion).matches()) {
            System.err.println("Error: release-version must be X.Y.Z (got: " + releaseVersion + ")");
            System.exit(1);
        }
        if (!snapshotRe.matcher(nextVersion).matches()) {
            System.err.println("Error: next-snapshot-version must be X.Y.Z-SNAPSHOT (got: " + nextVersion + ")");
            System.exit(1);
        }

        // ── Guard: project root ───────────────────────────────────────────
        if (!new File("./mvnw").exists()) {
            System.err.println("Error: ./mvnw not found. Run from the project root.");
            System.exit(1);
        }

        // ── Guard: clean working tree ─────────────────────────────────────
        var dirty = capture("git", "status", "--porcelain");
        if (!dirty.isEmpty()) {
            System.err.println("Error: working tree has uncommitted changes. Commit or stash them first.");
            System.exit(1);
        }

        // ── Guard: tag must not exist ─────────────────────────────────────
        var tag = "v" + releaseVersion;
        if (!capture("git", "tag", "-l", tag).isEmpty()) {
            System.err.println("Error: tag " + tag + " already exists.");
            System.exit(1);
        }

        // ── Release ───────────────────────────────────────────────────────
        run("./mvnw", "versions:set", "-DnewVersion=" + releaseVersion, "-DgenerateBackupPoms=false");
        run("git", "add", "pom.xml", "--", "**/pom.xml");
        run("git", "commit", "-m", "Bump version to " + releaseVersion);
        run("git", "tag", tag, "-m", "Release " + tag);

        // ── Next snapshot ─────────────────────────────────────────────────
        run("./mvnw", "versions:set", "-DnewVersion=" + nextVersion, "-DgenerateBackupPoms=false");
        run("git", "add", "pom.xml", "--", "**/pom.xml");
        run("git", "commit", "-m", "Increment version to " + nextVersion);

        System.out.println("Done. Tag " + tag + " created. Push with: git push && git push --tags");
    }

    static void run(String... cmd) throws Exception {
        int rc = new ProcessBuilder(cmd).inheritIO().start().waitFor();
        if (rc != 0) {
            System.err.println("Command failed (exit " + rc + "): " + String.join(" ", cmd));
            System.exit(rc);
        }
    }

    static String capture(String... cmd) throws Exception {
        var proc = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        var out  = new String(proc.getInputStream().readAllBytes()).strip();
        proc.waitFor();
        return out;
    }
}
