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
import com.soaint.crud.models.osc.leed.Leed;
import com.soaint.crud.modelsRn.Emails;
import com.soaint.crud.service.Services;

public class Metodos {

	public static String JsonTransformer(String jsonSend) {
        jsonSend = jsonSend.replace("ñ", "\\u00F1").replace("Ñ", "\\u00D1").replace("Á", "\\u00C1").replace("á", "\\u00E1").replace("É", "\\u00C9").replace("é", "\\u00E9").replace("Í", "\\u00CD").replace("í", "\\u00ED").replace("Ó", "\\u00D3").replace("ó", "\\u00F3").replace("Ú", "\\u00DA").replace("ú", "\\u00FA").replace("Ü", "\\u00DC").replace("ü", "\\u00FC");
        return jsonSend;
    }

	
	//*********************************buscarLead*******************************************
			
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
	

