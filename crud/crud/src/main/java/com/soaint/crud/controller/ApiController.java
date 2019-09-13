package com.soaint.crud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.crud.models.Eloqua.ContacAll;
import com.soaint.crud.service.ServiceEloqua;
import com.soaint.crud.service.ServiceOracle;
import com.soaint.crud.service.ServiceRigthNow;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	ServiceRigthNow serviceClient = new ServiceRigthNow();
	
	ServiceEloqua serviceEloqua = new ServiceEloqua();
	
	ServiceOracle serviceOracle = new ServiceOracle();
	
	//RigthNow *************************************************************************************
	
	@RequestMapping(value="/rigth/buscar/{email}", method=RequestMethod.GET)//buscar todo
	   public ContacAll buscarEmail(@PathVariable("email") String email) throws Exception {
	      return serviceClient.buscarEmail(email);
	 }
	
	@RequestMapping(value = "/rigth/crear", method = RequestMethod.POST)
		public ResponseEntity<String> CrearConta(@RequestBody String c) throws Exception {
			return new ResponseEntity<String>(serviceClient.serializarObjecto(c), HttpStatus.OK);
		}
		
	@DeleteMapping(value="/rigth/delete/{emails}")
	   public ResponseEntity<String> delete(@PathVariable ("emails") String emails) throws Exception {
		serviceClient.deleteEmails(emails);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//Eloqua***************************************************************************************
	
	@RequestMapping(value="/eloqua/buscar/{emails}", method=RequestMethod.GET)
		public ContacAll buscarId(@PathVariable("emails") String emails) throws Exception {
			return serviceEloqua.buscarEloqua(emails);
	}
	
	@RequestMapping(value = "/eloqua/crear", method = RequestMethod.POST)
	public ResponseEntity<String> CrearContaEl(@RequestBody String conta) throws Exception {
		return new ResponseEntity<String>(serviceEloqua.serializarObjectoEl(conta), HttpStatus.OK);
	}

	@RequestMapping(value="/eloqua/delete/{email}", method=RequestMethod.DELETE)
		public ResponseEntity<Object> deleteId(@PathVariable("email") String email) throws Exception {
		serviceEloqua.deleteEloqua(email);
			return new ResponseEntity<>("Contact borrado correctamente", HttpStatus.OK);		
	}
	
	//OracleSalesCloud *******************************************************************************
	
	@RequestMapping(value="/oracle/buscar/{emails}", method=RequestMethod.GET)
		public ContacAll buscar(@PathVariable("emails") String emails) throws Exception {
			return serviceOracle.buscarOracleSC(emails);
	}
	
	@RequestMapping(value ="/oracle/crear", method = RequestMethod.POST)
	public ResponseEntity<String> CrearContaOsc(@RequestBody String conta) throws Exception {
		return new ResponseEntity<String>(serviceOracle.serializarObjectoOsc(conta), HttpStatus.OK);
	}
	
	@RequestMapping(value="/oracle/delete/{email}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteOsc(@PathVariable("email") String email) throws Exception {
		serviceOracle.deleteOsc(email);
		return new ResponseEntity<String>("Contact borrado correctamente", HttpStatus.OK);		
	}

	//AllServices**************************************************************************************
	@RequestMapping(value="/all/crear", method= RequestMethod.POST)
		public ResponseEntity<String> CrearContaAll(@RequestBody String contaAll){
		return new ResponseEntity<String>(serviceClient.serializarObjecto(contaAll)+ "\n"
						 + serviceEloqua.serializarObjectoEl(contaAll)+ "\n"
						 + serviceOracle.serializarObjectoOsc(contaAll), HttpStatus.OK);
	}
	
	@RequestMapping(value="/all/delete/{emails}", method=RequestMethod.DELETE)
		public ResponseEntity<String> deleteAll(@PathVariable("emails") String DeleteAll) {
			return new ResponseEntity<String>(serviceClient.deleteEmails(DeleteAll)+ "\n"
					+ serviceEloqua.deleteEloqua(DeleteAll) + "\n"
					+ serviceOracle.deleteOsc(DeleteAll), HttpStatus.OK);		
	}
	
}
