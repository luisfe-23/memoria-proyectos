package com.soaint.crud.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
import com.soaint.crud.models.Eloqua.ContactosEl;

@Service
public class ServiceEloqua{
	
//****************************************Method GET*********************************************************************	
	
	public ContacAll buscarEloqua(String emails)  {
		
		ContacAll contactoEl = new ContacAll();
		
		try {
				URL obj = new URL(Services.UrlElEmail()+ "'" +emails+ "'");
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Authorization", "Basic "+ new String( Base64.getEncoder().encode(Services.getPasswdEl().getBytes())));
								
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}in.close();
				con.disconnect();
			
				JSONObject jsonObject = new JSONObject(response.toString());
				JSONArray elements = jsonObject.getJSONArray("elements");
				contactoEl.setId(elements.getJSONObject(0).getLong("id"));
				contactoEl.setEmailAddess(elements.getJSONObject(0).getString("emailAddress"));
	
				System.out.println("Contacto encontrado " + contactoEl.toString()); 
			
		} catch (Exception e) {
				System.out.println("Error contacto no encontrado Eloqua");
		}	
	return contactoEl;
	}
	
//******************************************Method POST********************************************************************
	public String crearEl(String contacto) {
		String response = ""; 
		try {
			JSONObject jsonObject = new JSONObject(contacto);
			String emails = jsonObject.getString("emailAddress");
			if (buscarEloqua(emails) != null) {
				CloseableHttpClient httpclient = HttpClients.createDefault(); 
				HttpPost httpPost = new HttpPost(Services.getURLEloqua() + Services.getPostEl());
				String autorizacion = Services.getPasswdEl();
				String basicAutorizacion = "Basic " + Base64.getEncoder().encodeToString(autorizacion.getBytes());
				
				StringEntity entity = new StringEntity(contacto);
				httpPost.setHeader(HttpHeaders.AUTHORIZATION, basicAutorizacion);
				
				httpPost.setEntity(entity);
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");
				
				httpclient.execute(httpPost);
				httpclient.close();
				
				return "Contacto creado con exito Eloqua";
				
	
			} else {
				return"Contacto no se ha podido crear";
			}
		} catch (Exception e) {
			
			return"Error";
		}
				
	}
	
//*************************************************************************************************************************	
	public String serializarObjectoEl(String jsonSend) {
		JSONObject jsonObject = new JSONObject(jsonSend);
		ContactosEl contacto = new ContactosEl();

		contacto.setFirstName(Metodos.JsonTransformer(jsonObject.getString("FirstName")));
		contacto.setLastName(Metodos.JsonTransformer(jsonObject.getString("LastName")));
		contacto.setEmailAddress(Metodos.JsonTransformer(jsonObject.getString("EmailAddress")));
		
		try {
			String contactJSON = new ObjectMapper().writeValueAsString(contacto);
			crearEl(contactJSON);	
			return "Creado Correctamente";
		}catch (Exception e) {
			return "Error";
		}
	}
	
//******************************************Method DELETE******************************************************************

	public String deleteEloqua(String emails)   {
		
		try {
			ContacAll delEl = buscarEloqua(emails);
			long id = delEl.getId();
			if (id != 0) {
				URL obj = new URL(Services.getURLEloqua() + Services.getDelEl() + id);
				HttpURLConnection delete = (HttpURLConnection) obj.openConnection();
				
				delete.setRequestMethod("DELETE");
				delete.setRequestProperty("Authorization", "Basic "+ new String( Base64.getEncoder().encode(Services.getPasswdEl().getBytes())));
				delete.setRequestProperty("Content-Type", "application/json");
				delete.setDoOutput(true);
				
		        BufferedReader in = new BufferedReader(new InputStreamReader(delete.getInputStream()));
		        System.out.println("Contato eliminado ");
			} else {
					System.out.println("Contacto no existente");
			}
		} catch (Exception exception) {
		    System.out.println("Error");
		}
		return "Contacto eliminado";
	}
}
