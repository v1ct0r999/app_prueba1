package cl.contreras.agenda;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;

public class AddAgendaActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText editTextName, editTextTime, editTextDay, editTextFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextNombre);
        editTextTime = findViewById(R.id.editTextDosis);
        editTextDay = findViewById(R.id.editTextStock);
        editTextFrequency = findViewById(R.id.editTextFrecuencia);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String time = editTextTime.getText().toString();
            String day = editTextDay.getText().toString();
            String frequencyText = editTextFrequency.getText().toString();

            if (name.isEmpty() || time.isEmpty() || day.isEmpty() || frequencyText.isEmpty()) {
                Toast.makeText(AddAgendaActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                int frequency = Integer.parseInt(frequencyText);
                boolean isInserted = dbHelper.addAgenda(name, time, day, frequency);
                if (isInserted) {
                    // Programar recordatorio
                    scheduleReminder(name, frequency);
                    Toast.makeText(AddAgendaActivity.this, "Agenda guardada", Toast.LENGTH_SHORT).show();
                    finish(); // Volver a la actividad principal
                } else {
                    Toast.makeText(AddAgendaActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para programar el recordatorio
    private void scheduleReminder(String name, int frequency) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(AddAgendaActivity.this, ReminderBroadcastReceiver.class);
        intent.putExtra("event_name", name);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Programar la alarma para que se active cada X horas
        long triggerTime = Calendar.getInstance().getTimeInMillis() + frequency * 60000; // 60000  3600000

        // Configurar una alarma repetitiva
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, frequency * 60000, pendingIntent);
    }
}