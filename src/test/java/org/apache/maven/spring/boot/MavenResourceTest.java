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

import org.apache.maven.spring.boot.ext.MavenResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MavenResource}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@DisplayName("MavenResource Tests")
class MavenResourceTest {

    @Test
    @DisplayName("Builder creates resource with all fields")
    void testBuilder() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar")
                .groupId("com.example")
                .artifactId("my-artifact")
                .extension("jar")
                .classifier("exec")
                .version("1.0.0")
                .generatePom(true)
                .createChecksum(true)
                .repositoryUrl("http://repo.example.com/releases")
                .repositoryId("releases")
                .build();

        assertThat(resource.getFilepath()).isEqualTo("/tmp/test.jar");
        assertThat(resource.getGroupId()).isEqualTo("com.example");
        assertThat(resource.getArtifactId()).isEqualTo("my-artifact");
        assertThat(resource.getExtension()).isEqualTo("jar");
        assertThat(resource.getClassifier()).isEqualTo("exec");
        assertThat(resource.getVersion()).isEqualTo("1.0.0");
        assertThat(resource.isGeneratePom()).isTrue();
        assertThat(resource.isCreateChecksum()).isTrue();
        assertThat(resource.getRepositoryUrl()).isEqualTo("http://repo.example.com/releases");
        assertThat(resource.getRepositoryId()).isEqualTo("releases");
    }

    @Test
    @DisplayName("Builder creates resource with default extension")
    void testBuilderDefaults() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar")
                .groupId("com.example")
                .artifactId("my-artifact")
                .version("1.0.0")
                .build();

        assertThat(resource.getExtension()).isEqualTo("jar");
        assertThat(resource.getClassifier()).isEmpty();
        assertThat(resource.isGeneratePom()).isFalse();
        assertThat(resource.isCreateChecksum()).isFalse();
    }

    @Test
    @DisplayName("parse with simple coordinates")
    void testParseSimple() {
        MavenResource resource = MavenResource.parse("/tmp/test.jar", "com.example:my-artifact:1.0.0");
        assertThat(resource.getGroupId()).isEqualTo("com.example");
        assertThat(resource.getArtifactId()).isEqualTo("my-artifact");
        assertThat(resource.getVersion()).isEqualTo("1.0.0");
        assertThat(resource.getExtension()).isEqualTo("jar");
        assertThat(resource.getClassifier()).isEmpty();
    }

    @Test
    @DisplayName("parse with extension")
    void testParseWithExtension() {
        MavenResource resource = MavenResource.parse("/tmp/test.pom", "com.example:my-artifact:pom:1.0.0");
        assertThat(resource.getExtension()).isEqualTo("pom");
    }

    @Test
    @DisplayName("parse with extension and classifier")
    void testParseWithClassifier() {
        MavenResource resource = MavenResource.parse("/tmp/test.jar", "com.example:my-artifact:jar:exec:1.0.0");
        assertThat(resource.getClassifier()).isEqualTo("exec");
        assertThat(resource.getExtension()).isEqualTo("jar");
    }

    @Test
    @DisplayName("getFilename without classifier")
    void testGetFilenameWithoutClassifier() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar")
                .groupId("com.example")
                .artifactId("my-artifact")
                .version("1.0.0")
                .build();
        assertThat(resource.getFilename()).isEqualTo("my-artifact-1.0.0.jar");
    }

    @Test
    @DisplayName("getFilename with classifier")
    void testGetFilenameWithClassifier() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar")
                .groupId("com.example")
                .artifactId("my-artifact")
                .classifier("exec")
                .version("1.0.0")
                .build();
        assertThat(resource.getFilename()).isEqualTo("my-artifact-1.0.0-exec.jar");
    }

    @Test
    @DisplayName("getDescription returns toString")
    void testGetDescription() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar")
                .groupId("com.example")
                .artifactId("my-artifact")
                .version("1.0.0")
                .build();
        assertThat(resource.getDescription()).isEqualTo(resource.toString());
    }

    @Test
    @DisplayName("toString without classifier")
    void testToStringWithoutClassifier() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar")
                .groupId("com.example")
                .artifactId("my-artifact")
                .version("1.0.0")
                .build();
        assertThat(resource.toString()).isEqualTo("com.example:my-artifact:jar:1.0.0");
    }

    @Test
    @DisplayName("toString with classifier")
    void testToStringWithClassifier() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar")
                .groupId("com.example")
                .artifactId("my-artifact")
                .classifier("exec")
                .version("1.0.0")
                .build();
        assertThat(resource.toString()).isEqualTo("com.example:my-artifact:jar:exec:1.0.0");
    }

    @Test
    @DisplayName("equals and hashCode are consistent")
    void testEqualsAndHashCode() {
        MavenResource a = new MavenResource.Builder()
                .filepath("/tmp/a.jar").groupId("g").artifactId("a").version("1.0.0").build();
        MavenResource b = new MavenResource.Builder()
                .filepath("/tmp/b.jar").groupId("g").artifactId("a").version("1.0.0").build();
        MavenResource c = new MavenResource.Builder()
                .filepath("/tmp/c.jar").groupId("g").artifactId("a").classifier("exec").version("1.0.0").build();
        MavenResource d = new MavenResource.Builder()
                .filepath("/tmp/d.jar").groupId("g").artifactId("a").version("2.0.0").build();

        // Same coordinates (filepath and classifier don't matter for equals)
        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());

        // Different classifier
        assertThat(a).isNotEqualTo(c);

        // Different version
        assertThat(a).isNotEqualTo(d);

        // Self-equality
        assertThat(a).isEqualTo(a);

        // Null and other types
        assertThat(a).isNotEqualTo(null);
        assertThat(a).isNotEqualTo("string");
    }

    @Test
    @DisplayName("equals with classifier")
    void testEqualsWithClassifier() {
        MavenResource a = new MavenResource.Builder()
                .filepath("/tmp/a.jar").groupId("g").artifactId("a")
                .classifier("exec").version("1.0.0").build();
        MavenResource b = new MavenResource.Builder()
                .filepath("/tmp/b.jar").groupId("g").artifactId("a")
                .classifier("exec").version("1.0.0").build();
        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @Test
    @DisplayName("repositoryUrl and repositoryId setters")
    void testRepositorySetters() {
        MavenResource resource = new MavenResource.Builder()
                .filepath("/tmp/test.jar").groupId("g").artifactId("a").version("1.0.0").build();
        resource.setRepositoryUrl("http://repo.example.com");
        resource.setRepositoryId("releases");
        assertThat(resource.getRepositoryUrl()).isEqualTo("http://repo.example.com");
        assertThat(resource.getRepositoryId()).isEqualTo("releases");
    }

    @Test
    @DisplayName("URI_SCHEME constant")
    void testUriScheme() {
        assertThat(MavenResource.URI_SCHEME).isEqualTo("maven");
    }
}
