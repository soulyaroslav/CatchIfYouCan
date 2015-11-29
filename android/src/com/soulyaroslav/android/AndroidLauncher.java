package com.soulyaroslav.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.soulyaroslav.facebook.FacebookResolver;
import com.soulyaroslav.game.QGame;

import java.util.Arrays;

public class AndroidLauncher extends AndroidApplication implements FacebookResolver {

    private AccessToken accessToken;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private Profile profile;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new QGame(this), config);

        setupFacebook();
	}

    private void setupFacebook(){
        // first initialize facebook sdk
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        // create callback manager
        callbackManager = CallbackManager.Factory.create();
        // create access token
        accessToken = AccessToken.getCurrentAccessToken();
        // create login manager
        loginManager = LoginManager.getInstance();
        // create callback for login manager
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Your app code goes here for when a login is successful.
                // here I update the access token with the login result.
                accessToken.setCurrentAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // your app code goes here for when the login is cancelled
                // either by the user or by the Facebook SDK
            }

            @Override
            public void onError(FacebookException e) {
                // your app code goes here for a login exception error
                // such as permissions were denied or another error.
            }
        });
    }

    @Override
    public void connectFacebook() {
        loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    @Override
    public void disconnectFacebook() {
        loginManager.logOut();
    }

    @Override
    public boolean isConnectedFacebook() {
        if(accessToken.getCurrentAccessToken() != null){
            return true;
        } else {
            return false;
        }
    }
}
