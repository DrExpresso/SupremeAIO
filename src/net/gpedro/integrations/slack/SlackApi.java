package net.gpedro.integrations.slack;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

public class SlackApi {

	private static final String POST = "POST";
	private static final String PAYLOAD = "payload=";
	private static final String UTF_8 = "UTF-8";

	private final String service;
	private final int timeout;
	private final Proxy proxy;

	public SlackApi(String service) {
		this(service, 5000);
	}

	public SlackApi(String service, Proxy proxy) {
		this(service, 5000, proxy);
	}

	public SlackApi(String service, int timeout) {
		this(service, timeout, Proxy.NO_PROXY);
	}

	public SlackApi(String service, int timeout, Proxy proxy) {

		this.timeout = timeout;

		if (service == null) {
			throw new IllegalArgumentException("Missing WebHook URL Configuration @ SlackApi");
		}

		if (proxy == null) {
			this.proxy = Proxy.NO_PROXY;
		} else {
			this.proxy = proxy;
		}

		this.service = service;

	}

	/**
	 * Prepare Message and send to Slack
	 */
	public void call(SlackMessage message) {
		if (message != null) {
			this.send(message.prepare());
		}
	}

	private String send(JsonObject message) {
		HttpURLConnection connection = null;
		try {
			// Create connection
			final URL url = new URL(this.service);
			connection = (HttpURLConnection) url.openConnection(proxy);
			connection.setRequestMethod(POST);
			connection.setConnectTimeout(timeout);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			final String payload = PAYLOAD + URLEncoder.encode(message.toString(), UTF_8);

			// Send request
			final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(payload);
			wr.flush();
			wr.close();

			// Get Response
			final InputStream is = connection.getInputStream();
			final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}

			rd.close();
			return response.toString();
		} catch (Exception e) {
			throw new SlackException(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
