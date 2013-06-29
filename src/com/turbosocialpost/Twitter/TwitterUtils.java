package com.turbosocialpost.Twitter;

import android.content.SharedPreferences;
import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class TwitterUtils {

	public static boolean isAuthenticated(SharedPreferences prefs) {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

        if (token == null || token.length() == 0 || secret == null || secret.length() == 0) {
            return false;
        }

//        Twitter twitter = getBaseInformation(token, secret);
        return true;
	}
	
	public static void sendTweet(SharedPreferences prefs, String msg) throws Exception {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
        Twitter twitter = getBaseInformation(token, secret);
        twitter.updateStatus(msg);
	}

    public static Twitter getBaseInformation(String token, String secret) {
        AccessToken a = new AccessToken(token, secret);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        twitter.setOAuthAccessToken(a);
        return twitter;
    }

    public static String getUserNameTwitter(SharedPreferences prefs) {
        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
        Twitter twitter = getBaseInformation(token, secret);
        long userId;
        try {
            userId = twitter.getId();
            User user = twitter.showUser(userId);
            return user.getName();

        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
