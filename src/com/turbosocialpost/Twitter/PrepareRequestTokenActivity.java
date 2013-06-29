package com.turbosocialpost.Twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.turbosocialpost.MyActivity;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import twitter4j.auth.AccessToken;

public class PrepareRequestTokenActivity extends Activity {

    private OAuthConsumer consumer;
    private OAuthProvider provider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	try {
    		this.consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
    	    this.provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL,Constants.ACCESS_URL,Constants.AUTHORIZE_URL);
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		new OAuthRequestTokenTask(this, consumer, provider).execute();
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent); 
//		SharedPreferences preferencesIntent = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			new RetrieveAccessTokenTask(this, consumer, provider).execute(uri);
			finish();	
		}
	}
	
	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

		private Context	context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;

		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
		}

		@Override
		protected Void doInBackground(Uri...params) {
			final Uri uri = params[0];
			final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);

                SharedPreferences preferencesBackground = PreferenceManager.getDefaultSharedPreferences(context);
				final Editor edit = preferencesBackground.edit();
                edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
                edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
                edit.commit();

				String token = preferencesBackground.getString(OAuth.OAUTH_TOKEN, "");
				String secret = preferencesBackground.getString(OAuth.OAUTH_TOKEN_SECRET, "");

				consumer.setTokenWithSecret(token, secret);
				context.startActivity(new Intent(context, MyActivity.class));

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	
}
