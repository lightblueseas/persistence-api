package de.alpharogroup.service.rs.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import lombok.NonNull;

/**
 * The class {@link InputStreamStreamingOutput} can be used for put an {@link InputStream} to a
 * {@link Response} object.
 */
public class InputStreamStreamingOutput implements StreamingOutput
{

	/** The input stream */
	private final InputStream inputStream;

	/**
	 * Instantiates a new {@link InputStreamStreamingOutput} object
	 *
	 * @param input
	 *            the input stream
	 */
	public InputStreamStreamingOutput(final @NonNull InputStream input)
	{
		this.inputStream = input;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final @NonNull OutputStream output)
		throws IOException, WebApplicationException
	{
		int bytesRead;
		byte[] buffer = new byte[4096];
		while ((bytesRead = this.inputStream.read(buffer)) != -1)
		{
			output.write(buffer, 0, bytesRead);
		}
	}

}