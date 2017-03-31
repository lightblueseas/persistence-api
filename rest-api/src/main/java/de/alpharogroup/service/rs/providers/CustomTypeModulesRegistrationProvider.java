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
package de.alpharogroup.service.rs.providers;

import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jaxrs.Jaxrs2TypesModule;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * The class {@link CustomTypeModulesRegistrationProvider} extends the
 * {@link JacksonJsonProvider} and register the module
 * {@link Jaxrs2TypesModule}.
 */
@Provider
public class CustomTypeModulesRegistrationProvider extends JacksonJsonProvider {

	/**
	 * Instantiation block.
	 */
	{
		ObjectMapper mapper = newTypeModulesRegistration();
		setMapper(mapper);
	}

	/**
	 * Instantiates a new {@link CustomTypeModulesRegistrationProvider} object.
	 */
	public CustomTypeModulesRegistrationProvider() {
	}

	/**
	 * Instantiates a new new {@link CustomTypeModulesRegistrationProvider}
	 * object with the given parameters.
	 *
	 * @param annotationsToUse
	 *            Annotation set(s) to use for configuring data binding
	 */
	public CustomTypeModulesRegistrationProvider(Annotations... annotationsToUse) {
		super(annotationsToUse);
	}

	/**
	 * Instantiates a new {@link CustomTypeModulesRegistrationProvider} object
	 * with the given parameters.
	 *
	 * @param mapper
	 *            the object mapper
	 */
	public CustomTypeModulesRegistrationProvider(ObjectMapper mapper) {
		super(mapper);
	}

	/**
	 * Instantiates a new new {@link CustomTypeModulesRegistrationProvider}
	 * object with the given parameters.
	 *
	 * @param mapper
	 *            the object mapper
	 * @param annotationsToUse
	 *            Sets of annotations (Jackson, JAXB) that provider should
	 *            support
	 */
	public CustomTypeModulesRegistrationProvider(ObjectMapper mapper, Annotations[] annotationsToUse) {
		super(mapper, annotationsToUse);
	}

	/**
	 * Factory callback method for registration of new type modules. This method
	 * is invoked in the constructor and can be overridden so users can add
	 * specific type modules for the specific provider.
	 *
	 * @return the object mapper
	 */
	protected ObjectMapper newTypeModulesRegistration() {
		ObjectMapper mapper = _mapperConfig.getConfiguredMapper();
		if (mapper == null) {
			mapper = _mapperConfig.getDefaultMapper();
		}
		mapper.registerModule(new Jaxrs2TypesModule());
		return mapper;
	}

}