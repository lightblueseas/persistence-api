package de.alpharogroup.springconfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The class {@link DataSourceBean}.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DataSourceBean
{

	/** The default h2 builder as start point to build a new DataSourceBean. */
	public static final DataSourceBean DEFAULT_H2_BUILDER = DataSourceBean
		.builder()
		.driverClassName("org.h2.Driver")
		.username("sa")
		.password("")
		.build();

	/** The driver class name. */
	private String driverClassName;

	/** The url. */
	private String url;

	/** The username. */
	private String username;

	/** The password. */
	private String password;
}