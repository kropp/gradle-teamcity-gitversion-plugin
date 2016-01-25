package com.github.kropp.gitversion

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

import static com.github.kropp.gitversion.StringUtil.strip

/**
 * @author Victor Kropp
 */
class GitVersionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        Repository repository = new FileRepositoryBuilder().readEnvironment().findGitDir().build()
        Git git = new Git(repository)

        def strategies = [new GitTagVersionStrategy(git), new GitBranchVersionStrategy(git)]

        def version = updateVersion(git, strategies.findResult { it.version } ?: Version.INITIAL as Version)

        if (version != null) {
            if (project.version != version) {
                project.version = version

                if (project.parent != null && project.parent.version != version) {
                    project.logger.info("Setting project version $version")
                    if (isTeamCity()) {
                        println "##teamcity[buildNumber '$version']"
                    }
                }
            }
        }
    }

    static String updateVersion(Git git, Version ver) {
        if (ver.isDirty()) {
            if (!isTeamCity()) {
                return ver.toString() + "-dev"
            } else {
                return incrementBuildNumber(git, ver).toString()
            }
        }

        return ver.toString()
    }

    private static boolean isTeamCity() {
        System.getenv("TEAMCITY_VERSION") != null
    }

    private static incrementBuildNumber(Git git, Version current) {
        def max = current.build
        def tagList = git.tagList().call()
        for (def tag : tagList) {
            def c = new Version(strip(strip(strip(tag.name, "refs/tags/"), "build-"), "v"), false)
            if (c.major == current.major && c.minor == current.minor && c.build > max) {
                max = c.build
            }
        }
        return current.withBuild(max + 1)
    }
}
