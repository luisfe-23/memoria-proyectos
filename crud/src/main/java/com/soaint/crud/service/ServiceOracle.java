package com.soaint.crud.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
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
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soaint.crud.metodos.Metodos;
import com.soaint.crud.models.Eloqua.ContacAll;
import com.soaint.crud.models.osc.ContactosOsc;
import com.soaint.crud.models.osc.leed.Leed;

@Service
public class ServiceOracle  {
	
//***************************************Method GetEmail************************************	
	
	 public ContacAll buscarOracleSC(String emails){
		 	ContacAll contactoOsc = new ContacAll();
		 	try {
	
				URL obj = new URL(Services.getEmailUrlOsc() + "'" +emails+ "'");
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

					JSONObject jsonObject = new JSONObject(response.toString());
					JSONArray items = jsonObject.getJSONArray("items");
					contactoOsc.setId(items.getJSONObject(0).getLong("PartyNumber"));
					contactoOsc.setEmailAddess(items.getJSONObject(0).getString("EmailAddress"));
					contactoOsc.setFirstName(items.getJSONObject(0).getString("FirstName"));
					
					System.out.println("Contacto encontrado " + contactoOsc.toString());
			} catch (Exception e) {
				System.out.println("Error contacto no encontrado Osc");
			}
			
			return contactoOsc;
	 }
	 
//****************************************MethodPOST********************************************
	 
	 public String crearOsc(String contacto) {
			String response = ""; 		
			try {		

					JSONObject jsonObject = new JSONObject(contacto);
					String emails = jsonObject.getString("EmailAddress");
				
				if (buscarOracleSC(emails) != null) {
						CloseableHttpClient httpclient = HttpClients.createDefault(); 
						HttpPost httpPost = new HttpPost(Services.getURLOracle() + Services.PostOsc());
						String autorizacion = Services.getUserOracle() + ":" + Services.getPasswdOracle();
						String basicAutorizacion = "Basic " + Base64.getEncoder().encodeToString(autorizacion.getBytes());
						
						StringEntity entity = new StringEntity(contacto);
						httpPost.setHeader(HttpHeaders.AUTHORIZATION, basicAutorizacion);
						httpPost.setEntity(entity);
						httpPost.setHeader("Accept", "application/json");
						httpPost.setHeader("Content-type", "application/json");

						httpclient.execute(httpPost);
						httpclient.close();
												
						System.out.println("Contacto creado con exito Osc");
						
						} else {
							System.out.println("Contacto existente");
						}
				} catch (Exception exception) {
					exception.printStackTrace();
					System.out.println("Error creacion");
					
			}	
			return response;	
	 }
	 
//**********************************************************************************************
		public String serializarObjectoOsc(String jsonSend) {
			JSONObject jsonObject = new JSONObject(jsonSend);
			ContactosOsc contacto = new ContactosOsc();

			contacto.setFirstName(Metodos.JsonTransformer(jsonObject.getString("FirstName")));
			contacto.setLastName(Metodos.JsonTransformer(jsonObject.getString("LastName")));
			contacto.setEmailAddress(Metodos.JsonTransformer(jsonObject.getString("EmailAddress")));

			try {
				String contactJSON = new ObjectMapper().writeValueAsString(contacto);
				crearOsc(contactJSON);	
				serializarlead(jsonSend);
				return "Creado Correctamente";
			}catch (Exception e) {
				return "Error";
			}
		}
		
//Lead******************************************************************************************
		
		public String serializarlead(String jsonSend) {
			JSONObject jsonObject = new JSONObject(jsonSend);
			ContacAll c1 = buscarOracleSC(jsonObject.getString("EmailAddress"));
			
			Leed l1 = new Leed();
			l1.setName(c1.getFirstName());
			l1.setPartyId(c1.getId());
			try {
				String contactJSON = new ObjectMapper().writeValueAsString(l1);
				return crearlead(contactJSON);
			}catch (Exception e) {
				e.printStackTrace();
				return "Error";
			}	
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
		
//****************************************MethodDelete******************************************
	 
	 	public String deleteOsc(String emails){
			
			try {
					ContacAll delOsc = buscarOracleSC(emails);
					long id = delOsc.getId();	
		 		if (id != 0) {
					URL obj = new URL(Services.getURLOracle() + Services.DelOsc() + id);
					HttpURLConnection delete = (HttpURLConnection) obj.openConnection();
					delete.setRequestMethod("DELETE");
					Authenticator.setDefault (new Authenticator() {
					    protected PasswordAuthentication getPasswordAuthentication() {
					        return new PasswordAuthentication (Services.getUserOracle(), Services.getPasswdOracle().toCharArray()); }
					});
					
			        BufferedReader in = new BufferedReader(new InputStreamReader(delete.getInputStream()));
			        return "Contato eliminado"; 
		        
				} else {
						return "Contacto no encontrado";
				}
			} catch (Exception e) {
				return"Error";
		}
	}
			
		
}
