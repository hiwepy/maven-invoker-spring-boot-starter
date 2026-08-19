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
package org.apache.maven.spring.boot.ext;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class MavenResource {

	public static String URI_SCHEME = "maven";

	final static String DEFAULT_EXTENSION = "jar";

	final static String EMPTY_CLASSIFIER = "";

	private final String groupId;

	private final String artifactId;

	private final String extension;

	private final String classifier;

	private MavenResource(String filepath, String groupId, String artifactId, String extension, String classifier,
			String version, boolean generatePom, boolean createChecksum, String repositoryUrl, String repositoryId) {
		Assert.hasText(filepath, "filepath must not be blank");
		Assert.hasText(groupId, "groupId must not be blank");
		Assert.hasText(artifactId, "artifactId must not be blank");
		Assert.hasText(extension, "extension must not be blank");
		Assert.hasText(version, "version must not be blank");
		this.filepath = filepath;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.extension = extension;
		this.classifier = classifier == null ? EMPTY_CLASSIFIER : classifier;
		this.version = version;
		this.generatePom = generatePom;
		this.createChecksum = createChecksum;
		this.repositoryUrl = repositoryUrl;
		this.repositoryId = repositoryId;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getExtension() {
		return extension;
	}

	public String getClassifier() {
		return classifier;
	}

	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return this.toString();
	}
	

	public String getFilename() {
		return StringUtils.hasLength(classifier) ?
				String.format("%s-%s-%s.%s", artifactId, version, classifier, extension) :
				String.format("%s-%s.%s", artifactId, version, extension);
	}

	public String getFilepath() {
		return filepath;
	}
	
	public boolean isGeneratePom() {
		return generatePom;
	}

	public boolean isCreateChecksum() {
		return createChecksum;
	}
	
	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof MavenResource)) {
			return false;
		}
		MavenResource that = (MavenResource) o;
		return this.groupId.equals(that.groupId) &&
				this.artifactId.equals(that.artifactId) &&
				this.extension.equals(that.extension) &&
				this.classifier.equals(that.classifier) &&
				this.version.equals(that.version);
	}

	@Override
	public int hashCode() {
		int result = groupId.hashCode();
		result = 31 * result + artifactId.hashCode();
		result = 31 * result + extension.hashCode();
		if (StringUtils.hasLength(classifier)) {
			result = 31 * result + classifier.hashCode();
		}
		result = 31 * result + version.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return StringUtils.hasLength(classifier) ?
				String.format("%s:%s:%s:%s:%s", groupId, artifactId, extension, classifier, version) :
				String.format("%s:%s:%s:%s", groupId, artifactId, extension, version);
	}

	public static MavenResource parse(String filepath, String coordinates) {
		Assert.hasText(coordinates, "coordinates are required");
		Pattern p = Pattern.compile("([^: ]+):([^: ]+)(:([^: ]*)(:([^: ]+))?)?:([^: ]+)");
		Matcher m = p.matcher(coordinates);
		Assert.isTrue(m.matches(), "Bad artifact coordinates " + coordinates
				+ ", expected format is <groupId>:<artifactId>[:<extension>[:<classifier>]]:<version>");
		String groupId = m.group(1);
		String artifactId = m.group(2);
		String extension = StringUtils.hasLength(m.group(4)) ? m.group(4) : DEFAULT_EXTENSION;
		String classifier = StringUtils.hasLength(m.group(6)) ? m.group(6) : EMPTY_CLASSIFIER;
		String version = m.group(7);
		return new MavenResource(filepath, groupId, artifactId, extension, classifier, version, false, false, "", "");
	}

	/**
	 * <p>Builder for constructing builder instances.</p>
	 *
	 * @author <a href="https://github.com/loong10k">Loong Wan</a>
	 * @since 1.0.0
	 */
	public static class Builder {

		private String groupId;

		private String artifactId;

		private String extension = DEFAULT_EXTENSION;

		private String classifier = EMPTY_CLASSIFIER;

		private String version;

		private boolean generatePom;
		
		private boolean createChecksum;

		private String filepath;
		
		private String repositoryUrl;
		
		private String repositoryId;
		
		public Builder() {
		}
		/**
		 * <p>Group id.</p>
		 * @param groupId the group id
		 * @return the builder
		 */

		public Builder groupId(String groupId) {
			this.groupId = groupId;
			return this;
		}
		/**
		 * <p>Artifact id.</p>
		 * @param artifactId the artifact id
		 * @return the builder
		 */

		public Builder artifactId(String artifactId) {
			this.artifactId = artifactId;
			return this;
		}
		/**
		 * <p>Extension.</p>
		 * @param extension the extension
		 * @return the builder
		 */

		public Builder extension(String extension) {
			this.extension = extension;
			return this;
		}
		/**
		 * <p>Classifier.</p>
		 * @param classifier the classifier
		 * @return the builder
		 */

		public Builder classifier(String classifier) {
			this.classifier = classifier;
			return this;
		}
		/**
		 * <p>Version.</p>
		 * @param version the version
		 * @return the builder
		 */

		public Builder version(String version) {
			this.version = version;
			return this;
		}
		/**
		 * <p>Generate pom.</p>
		 * @param generatePom the generate pom
		 * @return the builder
		 */
		
		public Builder generatePom(boolean generatePom) {
			this.generatePom = generatePom;
			return this;
		}
		/**
		 * <p>Create checksum.</p>
		 * @param createChecksum the create checksum
		 * @return the builder
		 */
		
		public Builder createChecksum(boolean createChecksum) {
			this.createChecksum = createChecksum;
			return this;
		}
		/**
		 * <p>Filepath.</p>
		 * @param filepath the filepath
		 * @return the builder
		 */
		
		public Builder filepath(String filepath) {
			this.filepath = filepath;
			return this;
		}
		/**
		 * <p>Repository url.</p>
		 * @param repositoryUrl the repository url
		 * @return the builder
		 */
		
		public Builder repositoryUrl(String repositoryUrl) {
			this.repositoryUrl = repositoryUrl;
			return this;
		}
		/**
		 * <p>Repository id.</p>
		 * @param repositoryId the repository id
		 * @return the builder
		 */
		
		public Builder repositoryId(String repositoryId) {
			this.repositoryId = repositoryId;
			return this;
		}
		/**
		 * <p>Build.</p>
		 * @return the maven resource
		 */

		public MavenResource build() {
			return new MavenResource(filepath, groupId, artifactId, extension, classifier, version, 
					generatePom, createChecksum, repositoryUrl, repositoryId);
		}
		
	}
	
}
