package com.github.kropp.gitversion

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Ref

import static com.github.kropp.gitversion.StringUtil.strip

/**
 * @author Victor Kropp
 */
class GitBranchVersionStrategy implements VersionStrategy {
    private Git git;

    GitBranchVersionStrategy(Git git) {
        this.git = git
    }

    @Override
    Version getVersion() {
        List<Ref> call = git.branchList().call();
        for (Ref ref : call) {
            def name = strip(ref.name, "refs/heads/")
            if (name != "master") {
                return new Version(strip(name, "release-"))
            }
        }
        null
    }
}
