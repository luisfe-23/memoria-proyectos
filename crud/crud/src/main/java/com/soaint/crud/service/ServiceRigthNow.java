package com.soaint.crud.service;

import java.io.BufferedReader;
import java.io.IOException;
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
import com.soaint.crud.modelsRn.AddressType;
import com.soaint.crud.modelsRn.Contactos;
import com.soaint.crud.modelsRn.ContactosRn;
import com.soaint.crud.modelsRn.Emails;
import com.soaint.crud.modelsRn.Name;

@Service
public class ServiceRigthNow{
				
//*************************************Method GET*****************************************

	public ContacAll buscarEmail(String emails) throws IOException {
		ContacAll contactoRn = new ContacAll();	
		try {
				URL obj = new URL(Services.getEmailRn() + "'" +emails+ "'");
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication (Services.getUserRn(), Services.getPasswdRn().toCharArray());
				    }
				});			
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}in.close();
					
				JSONObject jsonObject = new JSONObject(response.toString());
				JSONArray items = jsonObject.getJSONArray("items");
				contactoRn.setId(items.getJSONObject(0).getLong("id"));
				contactoRn.setFirstName(items.getJSONObject(0).getString("lookupName"));
				System.out.println("Contacto encontrado "+ contactoRn.toString());
				
				in.close();
				con.disconnect();	
		} catch (Exception e) {
			
			System.out.println("Error Contacto no encontrado Rn");
		}	
		return contactoRn;
		
	}
	
//**************************************Method POST************************************
	 	
public String crearRn(String contacto) {
		String response = ""; 		
		try {		
				JSONObject jsonObject = new JSONObject(contacto);
				JSONObject items = (JSONObject)jsonObject.get("emails");
				String emails = items.getString("address");
				
			if (buscarEmail(emails) != null) {
					CloseableHttpClient httpclient = HttpClients.createDefault(); 
					HttpPost httpPost = new HttpPost(Services.getURLRightNow() + Services.getPostRn());
					String autorizacion = Services.getUserRn() + ":" + Services.getPasswdRn();
					String basicAutorizacion = "Basic " + Base64.getEncoder().encodeToString(autorizacion.getBytes());
					
					StringEntity entity = new StringEntity(contacto);
					httpPost.setHeader(HttpHeaders.AUTHORIZATION, basicAutorizacion);
					httpPost.setEntity(entity);
					httpPost.setHeader("Accept", "application/json");
					httpPost.setHeader("Content-type", "application/json");
					
					httpclient.execute(httpPost);
					httpclient.close();

					System.out.println("Contacto creado con exito Rn");
					response = httpPost.getRequestLine().toString();
					} else {
						System.out.println("Contacto existente");
					}
			} catch (Exception exception) {
				
				return "Error";
		}	
		return response;	
 }

//***********************************************************************************************
	public String serializarObjecto(String jsonSend) {
		JSONObject jsonObject = new JSONObject(jsonSend);
		ContactosRn contacto = new ContactosRn();

		contacto.setName(new Name(Metodos.JsonTransformer(jsonObject.getString("FirstName")), Metodos.JsonTransformer(jsonObject.getString("LastName"))));
		contacto.setEmails(new Emails(Metodos.JsonTransformer(jsonObject.getString("EmailAddress")), new AddressType(0)));
		try {
			String contactJSON = new ObjectMapper().writeValueAsString(contacto);
			crearRn(contactJSON);	
			return "Creado Correctamente";
		}catch (Exception e) {
			return "Error";
		}
	}
	     	
//**************************************Method DELETE**********************************
	   
	public String deleteEmails(String emails) {
		
		try {
				ContacAll conDel = buscarEmail(emails);
				long id = conDel.getId();
				if (id != 0) {	
					URL obj = new URL(Services.getURLRightNow() + Services.getDelRn()  + id);
					HttpURLConnection delete = (HttpURLConnection) obj.openConnection();
					delete.setRequestMethod("DELETE");
					delete.setDoOutput(true);
					Authenticator.setDefault(new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(Services.getUserRn(), Services.getPasswdRn().toCharArray());
		    				}
		    			});
				
					BufferedReader in = new BufferedReader(new InputStreamReader(delete.getInputStream()));
					System.out.println("Contato eliminado ");
				} else {
					System.out.println("Contacto no encontrado");
			}
		} catch (Exception e) {
		    System.out.println("Eliminado correctamente");
		}
		return "Contacto eliminado"; 
	}
	}