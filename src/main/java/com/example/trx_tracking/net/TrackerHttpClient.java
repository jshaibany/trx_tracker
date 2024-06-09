package com.example.trx_tracking.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.trx_tracking.helper.JsonHelper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@PropertySource({ "classpath:application.properties" })
public class TrackerHttpClient {

	@Autowired
    private Environment env;
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> search(String bodyString) throws IOException{
		
		String url=String.format("%s%s%s%s", env.getProperty("tracker.scheme"),
				env.getProperty("tracker.host"),
				env.getProperty("tracker.port"),
				env.getProperty("tracker.search.path"));
		OkHttpClient client = new OkHttpClient().newBuilder()
									.connectTimeout(Integer.parseInt(env.getProperty("tracker.timeout")),TimeUnit.MILLISECONDS)
									.writeTimeout(Integer.parseInt(env.getProperty("tracker.write.timeout")), TimeUnit.MILLISECONDS)
									.readTimeout(Integer.parseInt(env.getProperty("tracker.read.timeout")), TimeUnit.MILLISECONDS)
									.build();
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(bodyString,mediaType);
		Request request = new Request.Builder()
				  .url(url)
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .build();
		Response response = client.newCall(request).execute();
		
		Map<String,Object> mapResponse = new HashMap<>();
		
		mapResponse = JsonHelper.mapJsonString(response.body().string());
		
		if(response.code()==200) {
			
			mapResponse=(Map<String,Object>)mapResponse.get("Result");
			
		}
		else {
			
			
		}
		
		
		return mapResponse;
		
	    
	}
}
