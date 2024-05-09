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
  - [Create PRs to update the script](https://github.com/gradle/develocity-ci-injection/actions/runs/9019735975/workflow#L38-L76) in various CI plugin repositories. [See here for an example run](https://github.com/gradle/develocity-ci-injection/actions/runs/9019735975#summary-24783287345) with links to generated PRs.
 

### Current status of CI integrations

#### GitHub Action for Gradle

- Differences from reference script are inconsequential.
- Ready to merge: https://github.com/gradle/actions/pull/215/files

#### Jenkins Gradle plugin

- Tested on a repository fork. Differences from reference script are inconsequential.
- Switch to the real repo, and ready to merge: https://github.com/bigdaz/jenkins-gradle-plugin/pull/1/files

#### Develocity Bamboo plugin

- Quite a few differences evident: https://github.com/bigdaz/develocity-bamboo-plugin/pull/1/files
- The input parameters mostly have a "develocity-plugin." prefix (eg `DEVELOCITY_PLUGIN_DEVELOCITY_PLUGIN_VERSION` instead of `DEVELOCITY_PLUGIN_VERSION`)
  - Need to work out if this is required or if we can rename for consistency
- The bamboo script assumes an additional "bamboo_" prefix for all input variables. Perhaps this is something automatically added by Bamboo?
- Current script in Bamboo plugin lacks some features (capture input files, enforce URL).
  When we switch to the reference script, we'd still need to implement these in the Bamboo plugin itself.

#### GitLab templates

- The implementation seems to inline the entire init-script into the YAML file. Two options:
  - Include content from a separate reference file at runtime. I presume this won't work since the YAML file is directly downloaded by a GitLab workflow, so the additional content wouldn't be available.
  - Implement a build process to construct the final YAML based on the reference script.
