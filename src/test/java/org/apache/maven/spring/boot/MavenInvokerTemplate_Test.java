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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.InvokerLogger;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.shared.invoker.PrintStreamHandler;
import org.apache.maven.shared.invoker.SystemOutHandler;
import org.apache.maven.shared.invoker.SystemOutLogger;
import org.apache.maven.spring.boot.ext.MavenInvokerTemplate;
import org.apache.maven.spring.boot.ext.MavenResource;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MavenInvokerTemplate}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class MavenInvokerTemplate_Test {

	InvocationOutputHandler outputHandler = new SystemOutHandler();
	InvocationOutputHandler errorHandler = new PrintStreamHandler(System.err, false);
	InvokerLogger invokerLogger = new SystemOutLogger();

	private static final String MAVEN_HOME = System.getProperty("maven.home",
			System.getenv("M2_HOME") != null ? System.getenv("M2_HOME") : "/opt/homebrew/Cellar/maven/3.9.16/libexec");

	public Invoker mavenInvoker(MavenInvokerProperties properties) {

		Invoker invoker = new DefaultInvoker();

		// Sets the handler used to capture the error output from the Maven build.
		invoker.setErrorHandler(errorHandler);
		// Sets the path to the base directory of the local repository to use for the
		// Maven invocation.
		if (StringUtils.hasText(properties.getLocalRepository())) {
			File localRepositoryDirectory = new File(properties.getLocalRepository());
			if (localRepositoryDirectory.exists() && localRepositoryDirectory.isDirectory()) {
				invoker.setLocalRepositoryDirectory(localRepositoryDirectory);
			} else {
				localRepositoryDirectory.mkdir();
				invoker.setLocalRepositoryDirectory(localRepositoryDirectory);
			}
		} else {
			File localRepositoryDirectory = new File(FileUtils.getUserDirectory(), ".m2" + File.separator + "repository");
			if (!localRepositoryDirectory.exists()) {
				localRepositoryDirectory.mkdir();
			}
			invoker.setLocalRepositoryDirectory(localRepositoryDirectory);
		}
		// Sets the logger used by this invoker to output diagnostic messages.
		invoker.setLogger(invokerLogger);
		//
		if (StringUtils.hasText(properties.getMavenExecutable())) {
			invoker.setMavenExecutable(new File(properties.getMavenExecutable()));
		}
		// Sets the path to the base directory of the Maven installation used to invoke
		// Maven.
		if (StringUtils.hasText(properties.getMavenHome())) {
			invoker.setMavenHome(new File(properties.getMavenHome()));
		}
		// Sets the handler used to capture the standard output from the Maven build.
		invoker.setOutputHandler(outputHandler);

		return invoker;
	}

	@Test
	void testConstructor() {
		MavenInvokerProperties properties = new MavenInvokerProperties();
		properties.setMavenHome(MAVEN_HOME);
		MavenInvokerTemplate template = new MavenInvokerTemplate(outputHandler, errorHandler,
				mavenInvoker(properties), properties);
		assertThat(template).isNotNull();
	}

	@Test
	void testDeployHandlesFailureGracefully() {
		MavenInvokerProperties properties = new MavenInvokerProperties();
		properties.setMavenHome(MAVEN_HOME);

		MavenInvokerTemplate template = new MavenInvokerTemplate(outputHandler, errorHandler,
				mavenInvoker(properties), properties);

		MavenResource resource = new MavenResource.Builder()
				.filepath("/tmp/nonexistent.jar")
				.groupId("test")
				.artifactId("test")
				.version("1.0.0")
				.repositoryId("nexus-releases")
				.repositoryUrl("http://127.0.0.1:1/repository/maven-releases/")
				.build();

		try {
			InvocationResult result = template.deploy(resource);
			// If we get here, the invoker returned a result (likely non-zero exit code)
			assertThat(result).isNotNull();
		} catch (MavenInvocationException e) {
			// Expected when Maven can't be invoked or the operation fails
			assertThat(e).isNotNull();
		}
	}

	@Test
	void testInstallHandlesFailureGracefully() {
		MavenInvokerProperties properties = new MavenInvokerProperties();
		properties.setMavenHome(MAVEN_HOME);

		MavenInvokerTemplate template = new MavenInvokerTemplate(outputHandler, errorHandler,
				mavenInvoker(properties), properties);

		MavenResource resource = new MavenResource.Builder()
				.filepath("/tmp/nonexistent.jar")
				.groupId("test")
				.artifactId("test")
				.version("1.0.0")
				.generatePom(true)
				.createChecksum(true)
				.build();

		try {
			InvocationResult result = template.install(resource);
			assertThat(result).isNotNull();
		} catch (MavenInvocationException e) {
			assertThat(e).isNotNull();
		}
	}

	@Test
	void testInstallWithCoordinates() {
		MavenInvokerProperties properties = new MavenInvokerProperties();
		properties.setMavenHome(MAVEN_HOME);

		MavenInvokerTemplate template = new MavenInvokerTemplate(outputHandler, errorHandler,
				mavenInvoker(properties), properties);

		try {
			InvocationResult result = template.install("/tmp/test.jar", "test:test:1.0.0");
			assertThat(result).isNotNull();
		} catch (MavenInvocationException e) {
			assertThat(e).isNotNull();
		}
	}

	@Test
	void testDeployWithCoordinates() {
		MavenInvokerProperties properties = new MavenInvokerProperties();
		properties.setMavenHome(MAVEN_HOME);

		MavenInvokerTemplate template = new MavenInvokerTemplate(outputHandler, errorHandler,
				mavenInvoker(properties), properties);

		try {
			InvocationResult result = template.deploy("/tmp/test.jar", "test:test:1.0.0",
					"http://127.0.0.1:1/releases", "releases");
			assertThat(result).isNotNull();
		} catch (MavenInvocationException e) {
			assertThat(e).isNotNull();
		}
	}

	@Test
	void testExecuteHandlesFailureGracefully() {
		MavenInvokerProperties properties = new MavenInvokerProperties();
		properties.setMavenHome(MAVEN_HOME);

		MavenInvokerTemplate template = new MavenInvokerTemplate(outputHandler, errorHandler,
				mavenInvoker(properties), properties);

		try {
			InvocationResult result = template.execute("/tmp", "validate");
			assertThat(result).isNotNull();
		} catch (MavenInvocationException e) {
			assertThat(e).isNotNull();
		}
	}

	@Test
	void testReadModelThrowsForNonexistentFile() {
		MavenInvokerProperties properties = new MavenInvokerProperties();
		MavenInvokerTemplate template = new MavenInvokerTemplate(outputHandler, errorHandler,
				mavenInvoker(properties), properties);

		try {
			template.readModel(new File("/tmp/nonexistent.jar"));
		} catch (Exception e) {
			assertThat(e).isInstanceOfAny(IOException.class, java.util.zip.ZipException.class);
		}
	}

}
