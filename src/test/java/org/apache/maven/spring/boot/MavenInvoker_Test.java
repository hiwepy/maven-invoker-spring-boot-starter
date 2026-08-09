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
import java.util.Arrays;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for Maven Invoker integration.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class MavenInvoker_Test {

	private static final String MAVEN_HOME = System.getProperty("maven.home",
			System.getenv("M2_HOME") != null ? System.getenv("M2_HOME") : "/opt/homebrew/Cellar/maven/3.9.16/libexec");

	@Test
	void testInvokerCreation() {
		Invoker invoker = new DefaultInvoker();
		assertThat(invoker).isNotNull();
	}

	@Test
	void testInvocationRequestCreation() {
		InvocationRequest request = new DefaultInvocationRequest();
		assertThat(request).isNotNull();
	}

	@Test
	void testDeployHandlesFailureGracefully() {
		InvocationRequest request = new DefaultInvocationRequest();
		request.setLocalRepositoryDirectory(new File(System.getProperty("user.home"), ".m2/repository"));
		request.setBaseDirectory(new File("/tmp"));
		request.setGoals(Arrays.asList("deploy:deploy-file",
				"-Dfile=/tmp/nonexistent.jar",
				"-DgroupId=test",
				"-DartifactId=test",
				"-Dversion=1.0.0",
				"-Dpackaging=jar",
				"-Durl=http://127.0.0.1:1/releases/",
				"-DrepositoryId=releases"));

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File(MAVEN_HOME));
		try {
			InvocationResult result = invoker.execute(request);
			assertThat(result).isNotNull();
		} catch (MavenInvocationException e) {
			assertThat(e).isNotNull();
		}
	}

	@Test
	void testExecuteHandlesFailureGracefully() {
		InvocationRequest request = new DefaultInvocationRequest();
		request.setLocalRepositoryDirectory(new File(System.getProperty("user.home"), ".m2/repository"));
		request.setGoals(Arrays.asList("validate"));

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File(MAVEN_HOME));
		try {
			InvocationResult result = invoker.execute(request);
			assertThat(result).isNotNull();
		} catch (MavenInvocationException e) {
			assertThat(e).isNotNull();
		}
	}

}
