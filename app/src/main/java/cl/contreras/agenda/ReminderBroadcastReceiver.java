package cl.contreras.agenda;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.os.Build;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Obtener el nombre del evento desde el Intent
        String eventName = intent.getStringExtra("event_name");

        // Crear el NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Verificar si el NotificationManager no es null
        if (notificationManager == null) {
            return; // Si es null, no se puede continuar
        }

        // Crear un canal de notificación si la versión de Android es Oreo o superior
        String channelId = "agenda_reminder_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Agenda Reminders", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Canal para recordatorios de la agenda");
            notificationManager.createNotificationChannel(channel);
        }

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)  // Ícono de la notificación
                .setContentTitle("Recordatorio de Agenda")         // Título de la notificación
                .setContentText("No olvides: " + eventName)        // Texto de la notificación (evento)
                .setPriority(NotificationCompat.PRIORITY_HIGH)     // Alta prioridad para que se muestre inmediatamente
                .setAutoCancel(true);                             // La notificación se cancelará cuando el usuario la toque

        // Enviar la notificación (el segundo argumento es un ID único para la notificación)
        notificationManager.notify(1, builder.build());
    }
}