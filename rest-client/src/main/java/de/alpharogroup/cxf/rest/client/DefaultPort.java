/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.alpharogroup.cxf.rest.client;

/**
 * The enum {@link DefaultPort}
 */
public enum DefaultPort
{

	/** The default http port. */
	HTTP(8080),
	/** The default https port. */
	HTTPS(8443);

	/** The port. */
	private final int port;

	/**
	 * instantiates a new {@link DefaultPort} object
	 *
	 * @param port
	 *            the port
	 */
	private DefaultPort(final int port)
	{
		this.port = port;
	}

	public int getPort()
	{
		return port;
	}
}
