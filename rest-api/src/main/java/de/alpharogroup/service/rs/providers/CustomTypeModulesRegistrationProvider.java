package de.alpharogroup.service.rs.providers;

import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jaxrs.Jaxrs2TypesModule;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * The class {@link CustomTypeModulesRegistrationProvider} extends the {@link JacksonJsonProvider} and register the 
 * module {@link Jaxrs2TypesModule}.
 */
@Provider
public class CustomTypeModulesRegistrationProvider extends JacksonJsonProvider {

	/**
	 * Instantiates a new {@link CustomTypeModulesRegistrationProvider} object.
	 */
	public CustomTypeModulesRegistrationProvider() {
		super();
		newTypeModulesRegistration();
	}

	/**
	 * Instantiates a new new {@link CustomTypeModulesRegistrationProvider} object with the given parameters.
	 *
	 * @param annotationsToUse  Annotation set(s) to use for configuring
     *    data binding
	 */
	public CustomTypeModulesRegistrationProvider(Annotations... annotationsToUse) {
		super(annotationsToUse);
		newTypeModulesRegistration();
	}

	/**
	 * Instantiates a new new {@link CustomTypeModulesRegistrationProvider} object with the given parameters.
	 *
	 * @param mapper the object mapper
	 * @param annotationsToUse Sets of annotations (Jackson, JAXB) that provider should
     *   support
	 */
	public CustomTypeModulesRegistrationProvider(ObjectMapper mapper, Annotations[] annotationsToUse) {
		super(mapper, annotationsToUse);
		newTypeModulesRegistration();
	}

	/**
	 * Instantiates a new {@link CustomTypeModulesRegistrationProvider} object with the given parameters.
	 *
	 * @param mapper the object mapper
	 */
	public CustomTypeModulesRegistrationProvider(ObjectMapper mapper) {
		super(mapper);
		newTypeModulesRegistration();
	}

	/**
	 * Factory callback method for registration of new type modules. This method is
	 * invoked in the constructor and can be overridden so users can
	 * add specific type modules for the specific provider.
	 */
	protected void newTypeModulesRegistration() {
		ObjectMapper mapper = _mapperConfig.getConfiguredMapper();
		if (mapper == null) {
			mapper = _mapperConfig.getDefaultMapper();
		}
		mapper.registerModule(new Jaxrs2TypesModule());
		setMapper(mapper);
	}

}