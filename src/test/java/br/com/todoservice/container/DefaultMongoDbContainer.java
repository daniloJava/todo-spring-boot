package br.com.todoservice.container;

import lombok.Getter;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

public class DefaultMongoDbContainer extends GenericContainer<DefaultMongoDbContainer> {

	@Getter
	private static boolean enabled = Boolean.parseBoolean(StringUtils.defaultIfBlank(System.getProperties().getProperty("todo.testcontainer.enabled"), "true"));

	private static DefaultMongoDbContainer container;

	public static final int MONGODB_PORT = 27017;

	public static final String DEFAULT_IMAGE_AND_TAG = "mongo:4.0";

	public static DefaultMongoDbContainer getInstance() {
		if (container == null) {
			container = new DefaultMongoDbContainer();
		}

		return container;
	}

	public DefaultMongoDbContainer() {
		this(DEFAULT_IMAGE_AND_TAG);
	}

	public DefaultMongoDbContainer(@NotNull String image) {
		super(image);
		addExposedPort(MONGODB_PORT);
	}

	@NotNull
	public Integer getPort() {
		return getMappedPort(MONGODB_PORT);
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void stop() {
		//do nothing, JVM handles shut down
	}

}
