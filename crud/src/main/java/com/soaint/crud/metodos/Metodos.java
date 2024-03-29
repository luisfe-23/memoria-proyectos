package com.soaint.crud.metodos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Base64;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import com.soaint.crud.models.Eloqua.ContacAll;
import com.soaint.crud.service.Services;

public class Metodos {

	public static String JsonTransformer(String jsonSend) {
        jsonSend = jsonSend.replace("ñ", "\\u00F1").replace("Ñ", "\\u00D1").replace("Á", "\\u00C1").replace("á", "\\u00E1").replace("É", "\\u00C9").replace("é", "\\u00E9").replace("Í", "\\u00CD").replace("í", "\\u00ED").replace("Ó", "\\u00D3").replace("ó", "\\u00F3").replace("Ú", "\\u00DA").replace("ú", "\\u00FA").replace("Ü", "\\u00DC").replace("ü", "\\u00FC");
        return jsonSend;
    }
	
	
	public String crearlead(String contacto) {
		String response = ""; 		
		try {		
				
			CloseableHttpClient httpclient = HttpClients.createDefault(); 
			HttpPost httpPost = new HttpPost(Services.getURLOracle() + Services.leadOsc());
			String autorizacion = Services.getUserOracle() + ":" + Services.getPasswdOracle();
			String basicAutorizacion = "Basic " + Base64.getEncoder().encodeToString(autorizacion.getBytes());
					
			StringEntity entity = new StringEntity(contacto);
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, basicAutorizacion);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			httpclient.execute(httpPost);
			httpclient.close();
					
		} catch (Exception exception) {
			exception.printStackTrace();
				
		}	
		return response;	
	}
	
	
	 public static String buscarOracleSCLead(String emails) throws Exception{	 
	 
			URL obj = new URL(Services.queryleads() + "'" +emails+ "'");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			Authenticator.setDefault (new Authenticator() {protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication (Services.getUserOracle(), Services.getPasswdOracle().toCharArray()); }
			});	
			
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
				}in.close();
				con.disconnect();
				
				return response.toString();
	 }
}
	

