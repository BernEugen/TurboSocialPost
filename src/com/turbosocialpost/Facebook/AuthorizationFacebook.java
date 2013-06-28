package com.turbosocialpost.Facebook;

import android.app.Activity;
import android.app.Application;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.turbosocialpost.MyActivity;
import com.turbosocialpost.R;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AuthorizationFacebook extends Activity {

    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

    private String userName;

    public void loginFacebook() {
        Session.openActiveSession(this, true, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                userName = user.getName();
//                                loginFacebookStatus.setText(" " + user.getName());
                            }
                        }
                    });
                }
            }
        });

        checkStatusPermission();
    }

    private void checkStatusPermission() {
        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            List<String> permissions = session.getPermissions();
            if (!hasPublishPermissionFacebook(PERMISSIONS, permissions)) {
                Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, PERMISSIONS);
                session.requestNewPublishPermissions(newPermissionsRequest);
            }
        }
    }

    private boolean hasPublishPermissionFacebook(Collection<String> listStatus, Collection<String> listPermissions) {
        for (String permissionStatus : listStatus) {
            if (!listPermissions.contains(permissionStatus)) {
                return false;
            }
        }
        return true;
    }

    public void logoutFacebook() {
        Session session = Session.getActiveSession();
        if (session != null && !session.isClosed()) {
            session.closeAndClearTokenInformation();
//            loginFacebookStatus.setText(R.string.login_status_facebook);
        }
    }
}
