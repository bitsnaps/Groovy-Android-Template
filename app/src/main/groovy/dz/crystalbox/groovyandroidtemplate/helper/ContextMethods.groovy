package dz.crystalbox.groovyandroidtemplate.helper

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.content.SharedPreferences
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AlertDialog
import groovy.transform.CompileStatic

@CompileStatic
class ContextMethods {

    static NotificationManagerCompat getCompatNotificationManager(Context self){
        NotificationManagerCompat.from(self)
    }
    static Notification notification(Context self, @DelegatesTo(NotificationCompat.Builder) Closure notificationSpec){
        def builder = new NotificationCompat.Builder(self)
        builder.with(notificationSpec)
        builder.build()
    }
    static notify(Context self, int notificationId, Notification notification){
        getCompatNotificationManager(self).notify(notificationId, notification)
    }
    static notify(Context self, int notificationId, @DelegatesTo(NotificationCompat.Builder) Closure notificationSpec){
        notify(self, notificationId, notification(self, notificationSpec))
    }

    static alert(Context self, @DelegatesTo(AlertDialog.Builder) Closure alertDialogSpec){
        def builder = new AlertDialog.Builder(self)
        builder.with(alertDialogSpec)
        builder.show()
    }

    static prefs(Activity self, int mode = android.content.Context.MODE_PRIVATE, @DelegatesTo(SharedPreferences.Editor) Closure sharedPreferencesSpec){
        SharedPreferences.Editor editor = prefs(self, mode).edit()
        editor.with(sharedPreferencesSpec)
        editor.commit()
    }
    static SharedPreferences prefs(Activity self, int mode = android.content.Context.MODE_PRIVATE){
        self.getPreferences(mode)
    }


}
