package io.github.danielharada.core;

import io.github.danielharada.resources.entities.URLResponse;
import io.github.danielharada.CacheConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class URLHandler {

    private CacheConfiguration configuration = new CacheConfiguration();
    private Logger log = LoggerFactory.getLogger(URLHandler.class);
    private String healthCheckUrl = configuration.getHealthCheckUrl();

    public URLHandler(){}

    public URLResponse getUrlResponse(String stringURL){
        URL url = convertStringToURL(stringURL);
        URLResponse urlResponse;
        if(url == null){
            // url is null if the convert method threw an exception.
            // Treat null urls as if they gave back a 404 response with null body/headers
            urlResponse = new URLResponse(null, null, 404, stringURL);
        } else {
            urlResponse = callOutToURL(url);
        }

        return urlResponse;
    }

    public URL convertStringToURL(String stringURL){
        URL url;
        String validatedUrl = validateHttpURL(stringURL);
        try{
            url = new URL(validatedUrl);
        } catch (MalformedURLException e) {
            log.error("\'{}\' is not a valid URL", validatedUrl, e);
            url = null;
        }

        return url;
    }

    public String validateHttpURL(String stringUrl){
        // URL objects must start with http:// or https://.  If it doesn't, assume http://
        boolean http = stringUrl.toLowerCase().startsWith("http://");
        boolean https = stringUrl.toLowerCase().startsWith("https://");
        if(http || https){
            return stringUrl;
        }
        return "http://".concat(stringUrl);
    }

    public URLResponse callOutToURL(URL url){
        int responseCode;
        String headers;
        String body;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
            headers = connection.getHeaderFields().toString();

            // construct the response body
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder bodyBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                bodyBuilder.append(inputLine);
            }
            in.close();
            body = bodyBuilder.toString();
        } catch(UnknownHostException e) {
            log.error("Could not find url {}", url.toString());
            // We couldn't find this URL, give back a 404
            responseCode = 404;
            headers = null;
            body = null;
        } catch (IOException e) {
            log.warn("Error reading from url {}", url.toString());
            // Treat read exception as a 500 error
            body = null;
            headers = null;
            responseCode = 500;
        } finally {
            try{
                connection.disconnect();
            } catch (Exception e) {
                log.warn("Could not disconnect from HTTP connection.");
            }
        }

        return new URLResponse(body, headers, responseCode, url.toString());
    }

    public int getHealthCheckResponseCode() {
        URL googleUrl = convertStringToURL(healthCheckUrl);
        HttpURLConnection connection = null;
        int responseCode;

        try{
            connection = (HttpURLConnection) googleUrl.openConnection();
            responseCode = connection.getResponseCode();
        } catch(Exception e) {
            log.error("Exception while connecting to {}", healthCheckUrl, e);
            // Any non-200 code will cause the health check to fail, so it's not super important what the real code was
            responseCode = 404;
        } finally {
            try{
                connection.disconnect();
            } catch (Exception e) {
                log.warn("Could not disconnect from HTTP connection.");
            }
        }

        return responseCode;

    }
}
