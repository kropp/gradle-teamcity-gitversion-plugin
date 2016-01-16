package com.github.kropp.gitversion

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Victor Kropp
 */
class GitVersionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        Repository repository = new FileRepositoryBuilder().setGitDir(new File(".git")).readEnvironment().findGitDir().build()
        Git git = new Git(repository)

        def strategies = [new GitTagVersionStrategy(git), new GitBranchVersionStrategy(git)]

        def version = updateVersion(strategies.findResult { it.version } ?: Version.EMPTY as Version)

        if (version != null) {
            println "Setting project version $version"
            if (isTeamCity()) {
                println "##teamcity[buildNumber '$version']"
            }
            project.ext.version = version
        }
    }

    static String updateVersion(Version ver) {
        if (isTeamCity()) {
            ver.incBuild().toString()
        } else {
            ver.toString() + "-dev"
        }
    }

    private static boolean isTeamCity() {
        System.getenv("TEAMCITY_VERSION") != null
    }
}
