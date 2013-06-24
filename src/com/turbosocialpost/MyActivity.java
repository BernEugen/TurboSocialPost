package com.turbosocialpost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.model.GraphUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyActivity extends Activity {

    Button postButton;
    EditText postMessage;
    TextView loginFacebookStatus;
    TextView loginTwitterStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        postMessage = (EditText) findViewById(R.id.postField);
        loginFacebookStatus = (TextView) findViewById(R.id.login_status_facebook);
        loginTwitterStatus = (TextView) findViewById(R.id.login_status_twitter);

        postButton = (Button) findViewById(R.id.postFacebook);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToFacebook();
            }
        });
    }

    private void postToFacebook() {

        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            String message = postMessage.getText().toString();
            Bundle postParams = new Bundle();
            postParams.putString("message", message);

            postMessage.setText("");
            Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_SHORT).show();

            Request request = new Request(session, "me/feed", postParams, HttpMethod.POST);
            RequestAsyncTask task = new RequestAsyncTask(request);
            task.execute();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
        }
    }

    public void openSessionFacebook() {

        Session.openActiveSession(this, true, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                loginFacebookStatus.setText(" " + user.getName());
                            }
                        }
                    });
                }
            }
        });

//        Session session = Session.getActiveSession();
//        Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("publish_actions"));
//        session.requestNewPublishPermissions(newPermissionsRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_login_facebook:
                openSessionFacebook();
                break;

            case R.id.menu_logout_facebook:
                Session session = Session.getActiveSession();
                if (!session.isClosed()) {
                    session.closeAndClearTokenInformation();
                    loginFacebookStatus.setText(R.string.login_status_facebook);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}



































