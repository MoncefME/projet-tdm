package com.example.projettdm.notifications
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService: FirebaseMessagingService()  {

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
       // super.onNewToken(token)

        //UPDATE SERVER
        Log.d("token", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        //HANDLE NOTIFICATION - respond to received message
        remoteMessage.notification?.let { message ->
           // sendNotification(message)
        }
    }






}