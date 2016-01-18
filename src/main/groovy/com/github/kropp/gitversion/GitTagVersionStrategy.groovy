package com.github.kropp.gitversion

import org.eclipse.jgit.api.Git

import static com.github.kropp.gitversion.StringUtil.strip

/**
 * @author Victor Kropp
 */
class GitTagVersionStrategy implements VersionStrategy {
    private Git git

    GitTagVersionStrategy(Git git) {
        this.git = git
    }

    @Override
    Version getVersion() {
        def desc = git.describe().call()
        if (desc == null || desc.empty) {
            return null
        }

        def matcher = desc =~ /(.*)-(\d+)-(\w+)/
        if (matcher.matches()) {
            new Version(strip(strip(matcher.group(1), "build-"), "v"), true)
        } else {
            new Version(strip(strip(desc, "build-"), "v"), git.status().call().hasUncommittedChanges())
        }
    }
}
