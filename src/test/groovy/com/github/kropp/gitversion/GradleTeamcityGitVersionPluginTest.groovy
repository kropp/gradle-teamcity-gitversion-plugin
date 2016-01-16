package com.github.kropp.gitversion

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * @author Victor Kropp
 */
class GradleTeamcityGitVersionPluginTest {
    @Test
    public void teamcityGitVersionPluginSetsProjectVersion() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.github.kropp.teamcity-gitversion'

        assert project.version != null
    }
}
