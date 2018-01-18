package GetToken;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

import com.sterlingcommerce.hadrian.api.remoteproxy.HadrianServer;
import com.sterlingcommerce.hadrian.common.net.ConnectParams;
import com.sterlingcommerce.hadrian.common.net.SslInfo;
import com.sterlingcommerce.security.provider.SecurityProperties;



public class GetToken_SEAS {
	private HadrianServer server = new HadrianServer();
	private ConnectParams params;

	private static String INVALIDATE_TOKEN_RESPONSE = "AUTH092I";
	private static String VALIDATE_TOKEN_RESPONSE = "AUTH090I";

	/**
	 * internal class for parsing xml response from SEAS server
	 * 
	 * @author iikonne
	 * 
	 */
	private class Message {
		private String msgID = "";
		private String msgText = "";
		private String token = "";

		public Message() {
		}

		public String getMsgID() {
			return msgID;
		}

		public String getMsgText() {
			return msgText;
		}

		public String getToken() {
			return token;
		}


		public String toString() {
			String string = "";
			if ((msgID.length() > 0) && (msgText.length() > 0)) {
				if (msgText.indexOf(msgID) >= 0) {
					string = msgText;
				} else {
					string = msgID + ": " + msgText;
				}
			} else if ((msgID.length() > 0) && (msgText.length() == 0)) {
				string = msgID;
			} else if ((msgID.length() == 0) && (msgText.length() > 0)) {
				string = msgText;
			}
			return string;
		}

		public void parse(String response) {
			int start = response.indexOf("<msgId>");
			if (start >= 0) {
				start += 7;

				int end = response.indexOf("</msgId>", start);
				if (end > 0) {
					this.msgID = response.substring(start, end).trim();
				}
			}

			// Look for <msgText> in response.
			start = response.indexOf("<msgText>");
			if (start >= 0) {
				start += 9;

				int end = response.indexOf("</msgText>", start);
				if (end > 0) {
					this.msgText = response.substring(start, end).trim();
				}
			}


			// Look for token.
			start = response.indexOf("<token>");
			if (start >= 0) {
				start += 7;

				int end = response.indexOf("</token>", start);
				if (end > 0) {
					this.token = response.substring(start, end).trim();
					//hanlde CDATA
	        			start = token.indexOf("<![CDATA[");
					if(start >= 0)
					{
						start += 9;
						end = token.indexOf("]]", start);
						if(end > 0)
						{
							this.token = token.substring(start, end).trim();
						}
					}
				}
			}

		}
	}

	/***
	 * Constructor: non-secure client connection.
	 * 
	 * @param serverHost
	 * @param serverPort
	 */
	public GetToken_SEAS(String serverHost, int serverPort) {
		params = new ConnectParams();
		params.setHost(serverHost);
		params.setPort(serverPort);
	}

	/***
	 * Constructor: secure client connection.
	 * 
	 * @param serverHost
	 * @param serverPort
	 * @param sslInfo
	 */
	public GetToken_SEAS(String serverHost, int serverPort, SslInfo si) {
		params = new ConnectParams();
		params.setHost(serverHost);
		params.setPort(serverPort);
		params.setSslInfo(si);
	}

	/**
	 * create SEASApi with hard-coded values
	 */
	public GetToken_SEAS() {

		Properties props = new Properties();

		/**
		 * Please substitute these with values that make sense for your
		 * environment
		 */

		System.setProperty("hadrian.client", "true");
		// Load security.properties but don't check for FIPS
		SecurityProperties.load(false);

		/*props.setProperty(SslInfo.KEY_STORE_FILE,
				"C:\\SEAS2430-20170519-MAINT-BUILD104\\conf\\system\\keystore");
		props.setProperty(SslInfo.KEY_STORE_PASSWORD, "password");
		props.setProperty(SslInfo.TRUST_STORE_FILE,
				"C:\\SEAS2430-20170519-MAINT-BUILD104\\conf\\system\\truststore");
		props.setProperty(SslInfo.TRUST_STORE_PASSWORD, "changeit");
		props.setProperty(SslInfo.TRUST_STORE_TYPE, "JKS");
		props.setProperty(SslInfo.KEY_STORE_TYPE, "JKS");
		props.setProperty(SslInfo.CLIENT_ALIAS, "mvince16");
		props.setProperty(SslInfo.SERVER_ALIAS, "mvince16");
		
		props.setProperty(SslInfo.PROTOCOL, "TLSv1.2");
		props.setProperty("SEAS-HOST", "iikonne");
		props.setProperty("SEAS-PORT", "61366");
		props.setProperty(SslInfo.CIPHER_SUITES,
				"TLS_RSA_WITH_AES_128_CBC_SHA TLS_RSA_WITH_3DES_EDE_CBC_SHA");

		// enable SSL connection
		props.setProperty("ENABLE-SSL", "true");

 
      props.setProperty(SslInfo.PROTOCOL, "TLSv1.2"); 
      
      */
		
		props.setProperty("SEAS-HOST", "9.109.116.190");
		props.setProperty("SEAS-PORT", "61365");
		this.initialize(props);
	
	}

	/*
	 * create SEASApi using properties
	 */
	public GetToken_SEAS(Properties props) {
		this.initialize(props);
	}

	/**
	 * initialize SEASApi
	 * 
	 * @param props
	 */
	private void initialize(Properties props) {

		if (props == null)
			throw new IllegalArgumentException("Invalid configuration");

		String seasHost = props.getProperty("SEAS-HOST");
		String seasPort = props.getProperty("SEAS-PORT");

		if (seasHost == null)
			throw new IllegalArgumentException("Invalid SEAS host specified");

		if (seasPort == null)
			throw new IllegalArgumentException("Invalid SEAS Port specified");

		params = new ConnectParams();
		params.setHost(seasHost);
		params.setPort(Integer.parseInt(seasPort));
		SslInfo sslInfo = null;

		
		String enableSSL = props.getProperty("ENABLE-SSL", "false");
		if (enableSSL != null && enableSSL.equalsIgnoreCase("true")) {
			sslInfo = new SslInfo();

			sslInfo.setKeyStoreType(props.getProperty(SslInfo.KEY_STORE_TYPE,
					"JKS"));
			sslInfo.setKeyStoreFile(props.getProperty(SslInfo.KEY_STORE_FILE));
			sslInfo.setKeyStorePassword(props
					.getProperty(SslInfo.KEY_STORE_PASSWORD));
			sslInfo.setClientAlias(props.getProperty(SslInfo.CLIENT_ALIAS));

			sslInfo.setTrustStoreType(props.getProperty(
					SslInfo.TRUST_STORE_TYPE, "JKS"));
			sslInfo.setTrustStoreFile(props
					.getProperty(SslInfo.TRUST_STORE_FILE));
			sslInfo.setTrustStorePassword(props
					.getProperty(SslInfo.TRUST_STORE_PASSWORD));

			sslInfo.setProtocol(props.getProperty(SslInfo.PROTOCOL, "SSL_TLS"));

			String cipherString = props.getProperty(SslInfo.CIPHER_SUITES);
			if (cipherString != null && cipherString.length() > 0) {
				String ciphers[] = cipherString.split(" ");
				if (ciphers != null && ciphers.length > 0) {
					List<String> cipherSuites = new ArrayList<String>();
					for (String cipher : ciphers) {
						cipherSuites.add(cipher);
					}
					sslInfo.setCipherSuites(cipherSuites);
				}
			}
			params.setSslInfo(sslInfo);
		}
	}

	/**
	 * validate a SEAS generated SAML token
	 * 
	 * @param userId
	 * @param password
	 * @param authProfile
	 * @return boolean
	 * @throws Exception
	 */
	public String requestSSOToken(String userId, String password,
			String authProfile) throws Exception {

		// Sanitize inputs
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}

		// Sanitize inputs
		if (authProfile == null) {
			throw new IllegalArgumentException("authProfile cannot be null");
		}

		//build an xml Request
		String xmlRequest = buildSSOTokenRequest(userId, password, authProfile)	;	
		System.out.println("Request :"+xmlRequest);
		// Send request to EA and get response...
		String response = sendRequestReceiveResponse(xmlRequest);
		
		System.out.println("Response : " + response);

		Message msg = new Message();
		msg.parse(response);
		String responseId = msg.getMsgID();
		String responseMsg = msg.getMsgText();
		
		//System.out.println("response id :"+ responseId+ " ,\n ResponseMsg : " +responseMsg );
		
		

		String lmsg = null;
		if (responseId != null && (responseId.equals(VALIDATE_TOKEN_RESPONSE) || responseId.endsWith("I"))) {
			
			System.out.println("Token : " +msg.getToken());
			return msg.getToken();
		} else {
			if (responseMsg != null)
				lmsg = responseMsg;
			else
				lmsg = "Could not authenticate user : " + userId;
			throw new Exception(lmsg);
		}
	}

	/**
	 * validate a SEAS generated SAML token
	 * 
	 * @param userId
	 * @param token
	 * @param refreshIfAboutToExpire
	 * @return boolean
	 * @throws Exception
	 */
	public boolean validateSSOToken(String userId, String token,
			boolean refreshIfAboutToExpire) throws Exception {
		// Sanitize inputs
		if (token == null) {
			throw new IllegalArgumentException("token cannot be null");
		}

		// Sanitize inputs
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}

		//build an xml Request
		String xmlRequest = buildValidateSSOTokenRequest(userId, token, refreshIfAboutToExpire)	;	
		// Send request to EA and get response...
		String response = sendRequestReceiveResponse(xmlRequest);
		
		Message msg = new Message();
		msg.parse(response);
		String responseId = msg.getMsgID();
		String responseMsg = msg.getMsgText();

		String lmsg = null;
		if (responseId != null && (responseId.equals(VALIDATE_TOKEN_RESPONSE) || responseId.endsWith("I"))) {
			return true;
		} else {
			if (responseMsg != null)
				lmsg = responseMsg;
			else
				lmsg = "Could not validate token : " + token + " for user "
						+ userId;
			throw new Exception(lmsg);
		}
	}

	/***
	 * Invalidate SSO token on EA server.
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public boolean invalidateSSOToken(String token) throws Exception {
		// Sanitize inputs
		if (token == null) {
			throw new IllegalArgumentException("token cannot be null");
		}

		//build an xml Request
		String xmlRequest = buildInValidateSSOTokenRequest(token);	
		String response = sendRequestReceiveResponse(xmlRequest);
		
		Message msg = new Message();
		msg.parse(response);
		String responseId = msg.getMsgID();
		String responseMsg = msg.getMsgText();

		String lmsg = null;

		if (responseId != null && (responseId.equals(INVALIDATE_TOKEN_RESPONSE) || responseId.endsWith("I"))) {
			return true;

		} else {
			if (responseMsg != null)
				lmsg = responseMsg;
			else
				lmsg = "Could not invalidate token : " + token;
			throw new Exception(lmsg);
		}
	}

	/**
	 * 
	 * @param userId
	 * @param token
	 * @param refreshIfAboutToExpire
	 * @return String
	 */
	private String buildValidateSSOTokenRequest(String userId, String token,
			boolean refreshIfAboutToExpire) 
	{
		String xmlRequest = null;
		if(userId != null && token != null){
			
	        xmlRequest =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	                 "<idmbDoc version=\"1.1\">\n" + 
	        		"<ssoValidateTokenRequest> \n" +
	                 "<subject>" + userId + "</subject> \n" +
	        		"<token><![CDATA[" + token + "]]></token> \n" +
	        		"<refreshIfAboutToExpire>" + refreshIfAboutToExpire + "</refreshIfAboutToExpire> \n" +
	                 "</ssoValidateTokenRequest>\n" + 
	        		"</idmbDoc>\n";
		}
		return xmlRequest;
	}

	/**
	 * 
	 * @param userId
	 * @param password
	 * @param authProfile
	 * @return String
	 */
	private String buildSSOTokenRequest(String userId, String password,
			String authProfile) 
	{
		String xmlRequest = null;
		if(userId != null && authProfile != null){
			
	        xmlRequest =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	                 "<idmbDoc version=\"1.1\">\n" + 
        		"<ssoAuthRequest> \n" +
				"<authRequest>\n"+
				"<profile>"+ authProfile + "</profile>\n"+
				"<userId>" + userId + "</userId>\n"+
				"<password>" + password + "</password>\n"+
				"</authRequest>\n"+
			     "</ssoAuthRequest>\n" + 
        		"</idmbDoc>\n";
		}
		return xmlRequest;
	}

	
	/**
	 * 
	 * @param token
	 * @return String
	 */
	private String buildInValidateSSOTokenRequest(String token) 
	{
		String xmlRequest = null;
		if(token != null){
			
	        xmlRequest =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	                 "<idmbDoc version=\"1.1\">\n" + 
	        		"<ssoInvalidateTokenRequest> \n" +
	        		"<token>" + token + "</token> \n" +
	                 "</ssoInvalidateTokenRequest>\n" + 
	        		"</idmbDoc>\n";
		}
		return xmlRequest;
	}
	
	
	/***
	 * Send request to SEAS and receive response.
	 * 
	 * @param xmlRequest
	 * @return xmlResponse
	 * @throws Exception
	 */
	private String sendRequestReceiveResponse(String xmlRequest) throws Exception {
		server.sendRequest(xmlRequest);
		return server.receiveResponse();
	}

	/***
	 * Connect to SEAS.
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception {
		server.connect(params);
	}

	/***
	 * Disconnect SEAS.
	 */
	public void disconnect() {
		server.disconnect();
	}

	public  String SEAS_Response(String user ,String password , String authProfile ) {

		GetToken_SEAS sai = null;
		String token=null;
		try {
			
			sai = new GetToken_SEAS();
	        sai.connect();
		
        
			//authenticate and generate token
			//String token = sai.requestSSOToken("iikonne", "Password01", "authProfile");
	        token = sai.requestSSOToken(user, password, authProfile);
			//boolean validated = sai.validateSSOToken(user, token, false);
			//System.out.println("Validated : " + validated);
			

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (sai != null)
				sai.disconnect();
		}
		return token;
		
		
	}

}
