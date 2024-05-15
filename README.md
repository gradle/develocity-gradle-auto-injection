# Develocity CI auto-injection

This repository is the home for tooling and scripts that allow auto-injection of Develocity into various Build Tool configurations by CI plugins.
It is designed to host the common build-tool integrations that will be leveraged by the various CI plugin implementations.

At this stage, only the Gradle init-script for Develocity has been migrated to this repository.

## Develocity injection Gradle init-script

An init-script that can be used by CI integrations to inject Develocity into a Gradle build.

- The latest source for the init-script can be [found here](https://github.com/gradle/develocity-ci-injection/blob/main/src/main/resources/develocity-injection.init.gradle).
- The repository includes a [set of integration tests](https://github.com/gradle/develocity-ci-injection/blob/main/src/test/groovy/com/gradle/TestDevelocityInjection.groovy) for different features of the init-script.
- The `reference` directory contains the [latest _released_ version of the init-script](https://github.com/gradle/develocity-ci-injection/blob/main/reference/develocity-injection.init.gradle): this script has a version number embedded, and is designed to be re-used in other repositories.
- When executed manually, the [gradle-release.yml workflow](https://github.com/gradle/develocity-ci-injection/actions/workflows/gradle-release.yml) will:
  - Copy the latest script source into `reference`, applying the supplied version number
  - Tag the repository with the version number
  - Commit the new reference script to this repository
  - [Create PRs to update the script](https://github.com/gradle/develocity-ci-injection/actions/runs/9102707566/workflow#L48-L57) in various CI plugin repositories. [See here for an example run](https://github.com/gradle/develocity-ci-injection/actions/runs/9102707566) with links to generated PRs.
 

### Current status of CI integrations

#### GitHub Action for Gradle

- Ready to merge: https://github.com/gradle/actions/pull/215/files

#### Develocity Bamboo plugin

- [Uses `sed` to add 'bamboo_' as an environment variable prefix](https://github.com/gradle/develocity-ci-injection/blob/main/.github/workflows/gradle-release.yml#L77)
- Ready to merge: https://github.com/gradle/develocity-bamboo-plugin/pull/157/files

#### Jenkins Gradle plugin

- PR against a repository fork: Git token in repo does not have permissions to create branch on `jenkinsci/gradle-plugin`.
- PR to merge into fork: https://github.com/bigdaz/jenkins-gradle-plugin/pull/1/files

#### GitLab templates

- Uses a build process to merge the reference build script into `develocity-gradle.yml`
- PR against a repository fork: Git token in repo does not have permissions to create branch on `gradle/develocity-gitlab-templates`.
- PR to merge into fork: https://github.com/bigdaz/develocity-gitlab-templates/pull/2/files

#### TeamCity plugin

- Changes are required to reference script in order to support capture of build-scan links. This could also be leveraged in Jenkins plugin.
- Currently generating PR against repository fork, but this is incomplete.
