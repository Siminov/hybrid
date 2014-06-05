package siminov.hybrid.adapter.handlers;

import org.apache.http.auth.AuthenticationException;

import siminov.connect.authorization.design.ICredential;
import siminov.connect.exception.AuthorizationException;
import siminov.connect.model.AuthorizationDescriptor;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.DatabaseException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.model.DatabaseMappingDescriptor;

public class CredentialManagerHandler implements IAdapter {

	private siminov.connect.resource.Resources connectResources = siminov.connect.resource.Resources.getInstance();
	private siminov.hybrid.resource.Resources hybridResources = siminov.hybrid.resource.Resources.getInstance();
	
	public String isAnyActiveCredential() throws AuthorizationException {

		AuthorizationDescriptor authorizationDescriptor = connectResources.getAuthenticatorDescription();
		String credential = authorizationDescriptor.getCredential();
		credential = credential.substring(credential.lastIndexOf(".") + 1, credential.length());
		
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(credential);
		
		String whereClause = ICredential.ACTIVE + "='" + true + "'";
		
		int activeAccountCount = 0;
		try {
			activeAccountCount = DatabaseHandler.count(databaseMappingDescriptor, null, null, whereClause, null, null);
		} catch(DatabaseException databaseException) {
			Log.error(CredentialManagerHandler.class.getName(), "isAnyActiveCredential", "Database Exception caught while getting active credential count, " + databaseException.getMessage());
			throw new AuthorizationException(CredentialManagerHandler.class.getName(), "isAnyActiveCredential", databaseException.getMessage());
		}
		
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridSiminovData = new HybridSiminovData();
		
		hybridSiminovData.setDataValue(String.valueOf(activeAccountCount));
		hybridSiminovDatas.addHybridSiminovData(hybridSiminovData);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException se) {
			Log.error(AuthenticationException.class.getName(), "isAnyActiveCredential", "SiminovException caught while generating json response, " + se.getMessage());
			throw new AuthorizationException(AuthenticationException.class.getName(), "isAnyActiveCredential", se.getMessage());
		}
		
		return data;
	}
	
	public String getActiveCredential() throws AuthorizationException {

		AuthorizationDescriptor authorizationDescriptor = connectResources.getAuthenticatorDescription();
		String credential = authorizationDescriptor.getCredential();
		credential = credential.substring(credential.indexOf(".") + 1, credential.length());

		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(credential);
		
		String whereClause = ICredential.ACTIVE + "='" + true + "'";

		HybridSiminovDatas hybridSiminovDatas = null;
		try {
			hybridSiminovDatas = DatabaseHandler.select(databaseMappingDescriptor, null, whereClause, null, null, null, null, null, null);
		} catch(DatabaseException databaseException) {
			Log.error(CredentialManagerHandler.class.getName(), "getActiveCredential", "Database Exception caught while getting active credential from database, " + databaseException.getMessage());
			throw new AuthorizationException(CredentialManagerHandler.class.getName(), "getActiveCredential", databaseException.getMessage());
		}

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException se) {
			Log.error(AuthenticationException.class.getName(), "isAnyActiveCredential", "SiminovException caught while generating json response, " + se.getMessage());
			throw new AuthorizationException(AuthenticationException.class.getName(), "isAnyActiveCredential", se.getMessage());
		}
		
		return data;
	}
	
	public String getCredentials() throws AuthorizationException {

		AuthorizationDescriptor authorizationDescriptor = connectResources.getAuthenticatorDescription();
		String credential = authorizationDescriptor.getCredential();
		credential = credential.substring(credential.indexOf(".") + 1, credential.length());

		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(credential);
		
		HybridSiminovDatas hybridSiminovDatas = null;
		try {
			hybridSiminovDatas = DatabaseHandler.select(databaseMappingDescriptor, null, null, null, null, null, null, null, null);
		} catch(DatabaseException databaseException) {
			Log.error(CredentialManagerHandler.class.getName(), "getActiveCredential", "Database Exception caught while getting active credential from database, " + databaseException.getMessage());
			throw new AuthorizationException(CredentialManagerHandler.class.getName(), "getActiveCredential", databaseException.getMessage());
		}

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException se) {
			Log.error(AuthenticationException.class.getName(), "isAnyActiveCredential", "SiminovException caught while generating json response, " + se.getMessage());
			throw new AuthorizationException(AuthenticationException.class.getName(), "isAnyActiveCredential", se.getMessage());
		}
		
		return data;
	}
}
