package siminov.hybrid.events;

import siminov.connect.design.authorization.ICredential;
import siminov.connect.events.IAuthenticationEvents;
import siminov.connect.exception.AuthorizationException;

public class AuthenticationEventHandler implements IAuthenticationEvents {

	public void onAuthenticationStart(ICredential credential) throws AuthorizationException {
		System.out.print("");
	}

	public void onAuthenticationFinish(ICredential credential) throws AuthorizationException {
		System.out.print("");
	}

	public void onAuthenticationTerminate(ICredential credential) throws AuthorizationException {
		System.out.print("");
	}
}
