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

import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.InvokerLogger;
import org.apache.maven.spring.boot.ext.MavenInvokerTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MavenInvokerAutoConfiguration}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@DisplayName("MavenInvokerAutoConfiguration Tests")
class MavenInvokerAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MavenInvokerAutoConfiguration.class));

    @Test
    @DisplayName("Auto-configuration class can be instantiated")
    void testInstantiation() {
        MavenInvokerAutoConfiguration config = new MavenInvokerAutoConfiguration();
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("All beans are created when auto-configuration loads")
    void testAllBeansCreated() {
        runner.run(context -> {
            assertThat(context).hasSingleBean(InvocationOutputHandler.class);
            assertThat(context).hasSingleBean(InvokerLogger.class);
            assertThat(context).hasSingleBean(Invoker.class);
            assertThat(context).hasSingleBean(MavenInvokerTemplate.class);
            assertThat(context).hasSingleBean(MavenInvokerProperties.class);
        });
    }

    @Test
    @DisplayName("outputHandler bean is created")
    void testOutputHandler() {
        runner.run(context -> {
            InvocationOutputHandler handler = context.getBean(InvocationOutputHandler.class);
            assertThat(handler).isNotNull();
        });
    }

    @Test
    @DisplayName("invokerLogger bean is created")
    void testInvokerLogger() {
        runner.run(context -> {
            InvokerLogger logger = context.getBean(InvokerLogger.class);
            assertThat(logger).isNotNull();
        });
    }

    @Test
    @DisplayName("mavenInvoker bean is created")
    void testMavenInvoker() {
        runner.run(context -> {
            Invoker invoker = context.getBean(Invoker.class);
            assertThat(invoker).isNotNull();
        });
    }

    @Test
    @DisplayName("mavenInvokerTemplate bean is created")
    void testMavenInvokerTemplate() {
        runner.run(context -> {
            MavenInvokerTemplate template = context.getBean(MavenInvokerTemplate.class);
            assertThat(template).isNotNull();
        });
    }

    @Test
    @DisplayName("mavenInvoker with custom mavenHome")
    void testMavenInvokerWithCustomMavenHome() {
        String mavenHome = System.getProperty("maven.home",
                System.getenv("M2_HOME") != null ? System.getenv("M2_HOME") : "/opt/homebrew/Cellar/maven/3.9.16/libexec");
        runner.withPropertyValues("maven.invoker.maven-home=" + mavenHome)
                .run(context -> {
                    Invoker invoker = context.getBean(Invoker.class);
                    assertThat(invoker).isNotNull();
                });
    }

    @Test
    @DisplayName("mavenInvoker with custom localRepository")
    void testMavenInvokerWithCustomLocalRepository() {
        runner.withPropertyValues("maven.invoker.local-repository=/tmp/test-maven-repo")
                .run(context -> {
                    Invoker invoker = context.getBean(Invoker.class);
                    assertThat(invoker).isNotNull();
                });
    }
}
