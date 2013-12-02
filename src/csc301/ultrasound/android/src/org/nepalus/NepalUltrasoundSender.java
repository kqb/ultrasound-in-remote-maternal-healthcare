package org.nepalus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Base64;

import com.google.gson.Gson;

public class NepalUltrasoundSender implements NepalUltrasoundAPI {

	private static String API_VERSION = "0.1";
	private String url;
	
	private static class Ultrasound {		
		public String personName;
		public String personAge;
		public String comments;		
		public String image;
	}
	/*
	 * return API_Version of the device
	 * @see org.nepalus.NepalUltrasoundAPI#getVersion()
	 */
	@Override
	public String getVersion() {
		return API_VERSION;
	}
	
	/*
	 * Set value of url to connect to.
	 * @param url: url link to set.
	 * @see org.nepalus.NepalUltrasoundAPI#setURL(java.lang.String)
	 */
	@Override
	public boolean setURL(String url) {
		this.url = url;
		return true;
	}
	
	/*
	 * return already set URL
	 * @see org.nepalus.NepalUltrasoundAPI#getURL()
	 */
	@Override
	public String getURL() {
		return this.url;
	}	
	
	
	/*
	 * Send person details and image to server.
	 * @params personName: Name of the person.
	 * 		   comments: comments associated with patient
	 * 		   image: image of patient's ultrasound
	 * @see org.nepalus.NepalUltrasoundAPI#send(java.lang.String, java.lang.String, android.graphics.Bitmap, java.lang.String)
	 */
	@Override
	public HttpResponse send(String personName, String comments, Bitmap image,
			String personAge) throws ClientProtocolException, IOException {
	
		if (this.url == null)
			throw new IOException("URL has not been set.");
		
		Gson gson = new Gson();
		Ultrasound u = new Ultrasound();
		u.personName = personName;
		u.personAge = personAge;
		u.image = toBase64(image);
		u.comments = comments;	
		
	    HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost(this.url);
	    HttpEntity entity = new StringEntity(gson.toJson(u));
	    
	    post.setHeader("Content-type", "application/json");
    	post.setEntity(entity);
        return client.execute(post);

	}
	
	private static String toBase64(Bitmap b) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		b.compress(Bitmap.CompressFormat.PNG, 100, out);  
		byte[] bytes = out.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
}
