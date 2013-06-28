package com.turbosocialpost.Twitter;

public class Constants {

	public static final String CONSUMER_KEY = "S8HPP7n1UlFh4WYlxLFA";
	public static final String CONSUMER_SECRET= "ZEWJmeOh2LD55wGEMoKWjF371koBrrjO8KTrVxmx0";
	
	public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
	
	public static final String	OAUTH_CALLBACK_SCHEME = "x-oauthflow-twitter";
	public static final String	OAUTH_CALLBACK_HOST = "callback";
	public static final String	OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

}

