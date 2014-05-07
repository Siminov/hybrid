package siminov.hybrid.adapter.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.http.auth.AuthenticationException;

import siminov.connect.authorization.CredentialManager;
import siminov.connect.design.authorization.ICredential;
import siminov.connect.design.authorization.ICredentialManager;
import siminov.connect.exception.AuthorizationException;
import siminov.hybrid.adapter.constants.HybridCredential;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class AuthenticationHandler {

	private ICredentialManager credentialManager = CredentialManager.getInstance();
	
	public String isAnyActiveCredential() throws AuthorizationException {

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridSiminovData = new HybridSiminovData();
		
		hybridSiminovData.setDataValue(String.valueOf(credentialManager.isAnyActiveCredential()));
		
		String data = null;
		try {
			HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException se) {
			Log.loge(AuthenticationException.class.getName(), "isAnyActiveCredential", "SiminovException caught while generating json response, " + se.getMessage());
			throw new AuthorizationException(AuthenticationException.class.getName(), "isAnyActiveCredential", se.getMessage());
		}
		
		return data;
	}
	
	public String getActiveCredential() throws AuthorizationException {
		
		ICredential credential = credentialManager.getActiveCredential();
		
		Collection<ICredential> credentials = new ArrayList<ICredential>();
		if(credential != null) {
			credentials.add(credential);
		}
		
		return generateHybridCredential(credentials.iterator());
	}
	
	public String getCredentials() throws AuthorizationException {

		Iterator<ICredential> credentials = credentialManager.getCredentials();
		
		return generateHybridCredential(credentials);
	}
	
	private String generateHybridCredential(Iterator<ICredential> credentials) throws AuthorizationException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		while(credentials.hasNext()) {
			
			ICredential credential = credentials.next();
			
			HybridSiminovData credentialHybridData = new HybridSiminovData();
			credentialHybridData.setDataType(HybridCredential.CREDENTIAL);
			
			/*
			 * Inflate Account Id
			 */
			HybridSiminovData accountIdHybridData = new HybridSiminovData();
			accountIdHybridData.setDataType(HybridCredential.ACCOUNT_ID);
			accountIdHybridData.setDataValue(credential.getAccountId());
			
			/*
			 * Inflate Token
			 */
			HybridSiminovData tokenHybridData = new HybridSiminovData();
			tokenHybridData.setDataType(HybridCredential.TOKEN);
			tokenHybridData.setDataValue(credential.getToken());
			
			/*
			 * Inflate Is Active
			 */
			HybridSiminovData isActiveHybridData = new HybridSiminovData();
			isActiveHybridData.setDataType(HybridCredential.IS_ACTIVE);
			isActiveHybridData.setDataValue(String.valueOf(credential.isActive()));
			
			hybridSiminovDatas.addHybridSiminovData(credentialHybridData);
		}
		
		String data = null;
		try {
			HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException se) {
			Log.loge(AuthenticationException.class.getName(), "generateHybridCredential", "SiminovException caught while generating json response, " + se.getMessage());
			throw new AuthorizationException(AuthenticationException.class.getName(), "generateHybridCredential", se.getMessage());
		}
		
		return data;
	}
}
