package com.soaint.crud.service;

import java.util.ResourceBundle;

public class Services {
	    private static final ResourceBundle PROPERTIES = ResourceBundle.getBundle("application");
	       
	    //OracleSalesCloud    
	    public static String getURLOracle(){return PROPERTIES.getString("UrlOracle");}
	    public static String getUserOracle(){return PROPERTIES.getString("UserOsc");}
	    public static String getPasswdOracle(){return PROPERTIES.getString("PasswdOsc");}
	    public static String getEmailUrlOsc(){return PROPERTIES.getString("UrlEmailOsc");}
	    public static String DelOsc(){return PROPERTIES.getString("DelOsc");}
	    public static String PostOsc(){return PROPERTIES.getString("PostOsc");}
	    public static String leadOsc(){return PROPERTIES.getString("LeadOsc");}
	    
	    //Eloqua
	    public static String getURLEloqua(){return PROPERTIES.getString("UrlEloqua");}
	    public static String getDelEl(){return PROPERTIES.getString("DelEl");}
	    public static String getPostEl(){return PROPERTIES.getString("PostEl");}
	    public static String getPasswdEl(){return PROPERTIES.getString("PassEloqua");}
	    public static String UrlElEmail(){return PROPERTIES.getString("UrlElEmail");}
	    
	    //RigthNow
	    public static String getURLRightNow(){return PROPERTIES.getString("UrlRn");}
    	public static String getPostRn(){return PROPERTIES.getString("PostRn");}
    	public static String getEmailRn(){return PROPERTIES.getString("UrlRnEmail");}
	    public static String getDelRn(){return PROPERTIES.getString("DelRn");}
	   	public static String getUserRn(){return PROPERTIES.getString("UserRn");}
	    public static String getPasswdRn(){return PROPERTIES.getString("PasswdRn");}
	    
	 	}
