package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.http.auth.AuthenticationException;

import siminov.connect.authorization.AuthorizationFactory;
import siminov.connect.design.authorization.ICredential;
import siminov.connect.design.authorization.ICredentialManager;
import siminov.connect.exception.AuthorizationException;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.constants.HybridCredential;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class AuthenticationHandler {

	private ICredentialManager credentialManager = AuthorizationFactory.getInstance().getAuthorizationProvider();
	
	public String isAnyActiveAccount() throws AuthorizationException {

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridSiminovData = new HybridSiminovData();
		
		hybridSiminovData.setDataValue(String.valueOf(credentialManager.isAnyActiveCredential()));
		
		String data = null;
		try {
			HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException se) {
			Log.loge(AuthenticationException.class.getName(), "isAnyActiveAccount", "SiminovException caught while generating json response, " + se.getMessage());
			throw new AuthorizationException(AuthenticationException.class.getName(), "isAnyActiveAccount", se.getMessage());
		}
		
		return data;
	}
	
	public String getActiveAccount() throws AuthorizationException {
		
		ICredential credential = credentialManager.getActiveCredential();
		
		Collection<ICredential> credentials = new ArrayList<ICredential>();
		if(credential != null) {
			credentials.add(credential);
		}
		
		return generateHybridCredential(credentials.iterator());
	}
	
	public String getAccounts() throws AuthorizationException {

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
			
			/*
			 * Inflate Inline Resources
			 */
			HybridSiminovData inlineResourcesHybridData = new HybridSiminovData();
			inlineResourcesHybridData.setDataType(HybridCredential.INLINE_RESOURCES);
			
			Iterator<String> inlineResourceKeys = credential.getResources();
			while(inlineResourceKeys.hasNext()) {
				
				String inlineResourceKey = inlineResourceKeys.next();
				Object inlineResourceValue = credential.getResource(inlineResourceKey);
				
				if(inlineResourceValue instanceof Object) {
					continue;
				}
				
				HybridSiminovData inlineResourceHybridData = new HybridSiminovData();
				inlineResourceHybridData.setDataType(inlineResourceKey);
				inlineResourceHybridData.setDataValue((String) inlineResourceValue);
				
				inlineResourcesHybridData.addData(inlineResourceHybridData);
			}
			
			hybridSiminovDatas.addHybridSiminovData(inlineResourcesHybridData);
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
	
	public String getAuthenticate(String data) throws AuthorizationException {
		
		HybridSiminovDataReader hybridSiminovDataParser = null; 
		data = URLDecoder.decode(data);
		
		try {
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.loge(AuthenticationHandler.class.getName(), "getAuthenticate", "SiminovException caught while parsing siminov hybrid core data, " + siminovException.getMessage());
			throw new AuthorizationException(AuthenticationHandler.class.getName(), "getAuthenticate", "SiminovException caught while parsing siminov hybrid core data, " + siminovException.getMessage());
		}


		HybridSiminovDatas hybridSiminovDatas = hybridSiminovDataParser.getDatas();
		
		HybridSiminovData credentialHybridData = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(Constants.SIMINOV_AUTHENTICATE_ADAPTER_GET_AUTHENTICATE_CREDENTIAL_PARAMETER);
		
		return null;
	}
}
