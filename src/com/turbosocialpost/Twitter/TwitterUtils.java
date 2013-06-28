package com.turbosocialpost.Twitter;

import android.content.SharedPreferences;
import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class TwitterUtils {

    private static String userNameTwitter;

	public static boolean isAuthenticated(SharedPreferences prefs) {

		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		
		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);

        long userId;
        try {
            userId = twitter.getId();
            User user = twitter.showUser(userId);
            userNameTwitter = user.getName();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        if (token == null || token.length() == 0 || secret == null || secret.length() == 0) {
            return false;
        } else {
            return true;
        }
	}
	
	public static void sendTweet(SharedPreferences prefs, String msg) throws Exception {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);
        twitter.updateStatus(msg);
	}

    public static String getUserNameTwitter() {
        return userNameTwitter;
    }
}
