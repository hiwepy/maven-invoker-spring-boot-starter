/*
 * Copyright (c) 2018, hiwepy (https://github.com/easy-4-java).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.maven.spring.boot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest.CheckSumPolicy;
import org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MavenInvokerProperties}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@DisplayName("MavenInvokerProperties Tests")
class MavenInvokerPropertiesTest {

    @Test
    @DisplayName("Default instance has expected defaults")
    void testDefaults() {
        MavenInvokerProperties props = new MavenInvokerProperties();
        assertThat(props.isAlsoMake()).isFalse();
        assertThat(props.isAlsoMakeDependents()).isFalse();
        assertThat(props.isBatchMode()).isFalse();
        assertThat(props.isDebug()).isFalse();
        assertThat(props.getGlobalSettings()).isNull();
        assertThat(props.getGlobalToolchains()).isNull();
        assertThat(props.getGlobalChecksumPolicy()).isEqualTo(CheckSumPolicy.Warn);
        assertThat(props.getJavaHome()).isNull();
        assertThat(props.getLocalRepository()).isNull();
        assertThat(props.getMavenExecutable()).isNull();
        assertThat(props.getMavenHome()).isNull();
        assertThat(props.getMavenOpts()).isNull();
        assertThat(props.getMavenRepositorys()).isNotNull().isEmpty();
        assertThat(props.isNonPluginUpdates()).isFalse();
        assertThat(props.isOffline()).isFalse();
        assertThat(props.getProperties()).isNull();
        assertThat(props.getPomFilename()).isNull();
        assertThat(props.getProfiles()).isNull();
        assertThat(props.getProjects()).isNull();
        assertThat(props.getReactorFailureBehavior()).isEqualTo(ReactorFailureBehavior.FailFast);
        assertThat(props.isRecursive()).isTrue();
        assertThat(props.getResumeFrom()).isNull();
        assertThat(props.isShellEnvironmentInherited()).isTrue();
        assertThat(props.isShowErrors()).isFalse();
        assertThat(props.isShowVersion()).isFalse();
        assertThat(props.getShellEnvironments()).isNull();
        assertThat(props.getThreads()).isEqualTo(1);
        assertThat(props.isUpdateSnapshots()).isFalse();
        assertThat(props.getUserSettings()).isNull();
        assertThat(MavenInvokerProperties.PREFIX).isEqualTo("maven.invoker");
    }

    @Test
    @DisplayName("All setters and getters")
    void testSettersAndGetters() {
        MavenInvokerProperties props = new MavenInvokerProperties();

        props.setAlsoMake(true);
        assertThat(props.isAlsoMake()).isTrue();

        props.setAlsoMakeDependents(true);
        assertThat(props.isAlsoMakeDependents()).isTrue();

        props.setBatchMode(true);
        assertThat(props.isBatchMode()).isTrue();

        props.setDebug(true);
        assertThat(props.isDebug()).isTrue();

        props.setGlobalSettings("/path/to/global-settings.xml");
        assertThat(props.getGlobalSettings()).isEqualTo("/path/to/global-settings.xml");

        props.setGlobalToolchains("/path/to/toolchains.xml");
        assertThat(props.getGlobalToolchains()).isEqualTo("/path/to/toolchains.xml");

        props.setGlobalChecksumPolicy(CheckSumPolicy.Fail);
        assertThat(props.getGlobalChecksumPolicy()).isEqualTo(CheckSumPolicy.Fail);

        props.setJavaHome("/path/to/jdk");
        assertThat(props.getJavaHome()).isEqualTo("/path/to/jdk");

        props.setLocalRepository("/path/to/repo");
        assertThat(props.getLocalRepository()).isEqualTo("/path/to/repo");

        props.setMavenExecutable("/path/to/mvn");
        assertThat(props.getMavenExecutable()).isEqualTo("/path/to/mvn");

        props.setMavenHome("/path/to/maven");
        assertThat(props.getMavenHome()).isEqualTo("/path/to/maven");

        props.setMavenOpts("-Xmx1024m");
        assertThat(props.getMavenOpts()).isEqualTo("-Xmx1024m");

        Map<String, String> repos = new HashMap<>();
        repos.put("central", "http://repo1.maven.org/maven2");
        props.setMavenRepositorys(repos);
        assertThat(props.getMavenRepositorys()).isEqualTo(repos);

        props.setNonPluginUpdates(true);
        assertThat(props.isNonPluginUpdates()).isTrue();

        props.setOffline(true);
        assertThat(props.isOffline()).isTrue();

        Properties sysProps = new Properties();
        sysProps.setProperty("key", "value");
        props.setProperties(sysProps);
        assertThat(props.getProperties()).isEqualTo(sysProps);

        props.setPomFilename("build.xml");
        assertThat(props.getPomFilename()).isEqualTo("build.xml");

        props.setProfiles(Collections.singletonList("prod"));
        assertThat(props.getProfiles()).containsExactly("prod");

        props.setProjects(Collections.singletonList("module-a"));
        assertThat(props.getProjects()).containsExactly("module-a");

        props.setReactorFailureBehavior(ReactorFailureBehavior.FailAtEnd);
        assertThat(props.getReactorFailureBehavior()).isEqualTo(ReactorFailureBehavior.FailAtEnd);

        props.setRecursive(false);
        assertThat(props.isRecursive()).isFalse();

        props.setResumeFrom("module-b");
        assertThat(props.getResumeFrom()).isEqualTo("module-b");

        props.setShellEnvironmentInherited(false);
        assertThat(props.isShellEnvironmentInherited()).isFalse();

        props.setShowErrors(true);
        assertThat(props.isShowErrors()).isTrue();

        props.setShowVersion(true);
        assertThat(props.isShowVersion()).isTrue();

        Map<String, String> env = new HashMap<>();
        env.put("JAVA_HOME", "/path/to/jdk");
        props.setShellEnvironments(env);
        assertThat(props.getShellEnvironments()).isEqualTo(env);

        props.setThreads(4);
        assertThat(props.getThreads()).isEqualTo(4);

        props.setUpdateSnapshots(true);
        assertThat(props.isUpdateSnapshots()).isTrue();

        props.setUserSettings("/path/to/settings.xml");
        assertThat(props.getUserSettings()).isEqualTo("/path/to/settings.xml");
    }

    @Test
    @DisplayName("newRequest creates request with defaults")
    void testNewRequestDefaults() {
        MavenInvokerProperties props = new MavenInvokerProperties();
        InvocationRequest request = props.newRequest();
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("newRequest with all properties set")
    void testNewRequestWithAllProperties() {
        MavenInvokerProperties props = new MavenInvokerProperties();
        props.setAlsoMake(true);
        props.setAlsoMakeDependents(true);
        props.setBatchMode(true);
        props.setDebug(true);
        props.setGlobalChecksumPolicy(CheckSumPolicy.Fail);
        props.setJavaHome(System.getProperty("java.home"));
        props.setLocalRepository(System.getProperty("user.home") + "/.m2/repository");
        props.setMavenOpts("-Xmx512m");
        props.setNonPluginUpdates(true);
        props.setOffline(true);
        props.setProfiles(Collections.singletonList("default"));
        props.setProjects(Collections.singletonList("my-module"));
        props.setReactorFailureBehavior(ReactorFailureBehavior.FailNever);
        props.setRecursive(false);
        props.setResumeFrom("my-module");
        props.setShellEnvironmentInherited(false);
        props.setShowErrors(true);
        props.setShowVersion(true);
        Properties sysProps = new Properties();
        sysProps.setProperty("key", "value");
        props.setProperties(sysProps);
        props.setThreads(2);
        props.setUpdateSnapshots(true);
        props.setUserSettings(System.getProperty("user.home") + "/.m2/settings.xml");

        InvocationRequest request = props.newRequest();
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("newRequest with global settings file")
    void testNewRequestWithGlobalSettings() {
        MavenInvokerProperties props = new MavenInvokerProperties();
        props.setGlobalSettings(System.getProperty("user.home") + "/.m2/settings.xml");
        InvocationRequest request = props.newRequest();
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("newRequest with global toolchains file")
    void testNewRequestWithGlobalToolchains() {
        MavenInvokerProperties props = new MavenInvokerProperties();
        props.setGlobalToolchains(System.getProperty("user.home") + "/.m2/toolchains.xml");
        InvocationRequest request = props.newRequest();
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("newRequest with local repository creates directory")
    void testNewRequestWithLocalRepository() {
        MavenInvokerProperties props = new MavenInvokerProperties();
        props.setLocalRepository("/tmp/test-maven-repo-" + System.currentTimeMillis());
        InvocationRequest request = props.newRequest();
        assertThat(request).isNotNull();
    }
}
