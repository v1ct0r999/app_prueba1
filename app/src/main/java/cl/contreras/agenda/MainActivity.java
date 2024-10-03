package cl.contreras.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 100;
    DatabaseHelper dbHelper;
    LinearLayout agendaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verificar y solicitar permiso de notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_PERMISSION_CODE);
            }
        }

        dbHelper = new DatabaseHelper(this);
        agendaLayout = findViewById(R.id.agendaLayout); // Asegúrate de haber cambiado el layout en activity_main.xml
        Button btnAddAgenda = findViewById(R.id.btnAddAgenda);

        btnAddAgenda.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAgendaActivity.class);
            startActivity(intent);
        });

        loadAgendas();
    }

    private void loadAgendas() {
        agendaLayout.removeAllViews(); // Limpiar el layout antes de cargar los datos nuevos
        Cursor cursor = dbHelper.getAllAgendas();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String time = cursor.getString(2);
                String day = cursor.getString(3);

                // Crear un nuevo botón grande
                Button agendaButton = new Button(this);
                agendaButton.setText(name + " - " + time + " - " + day);
                agendaButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                agendaButton.setPadding(20, 20, 20, 20);
                agendaButton.setTextSize(18);

                // Aquí puedes agregar la lógica futura para editar, eliminar o ver detalles

                // Agregar el botón al layout dinámico
                agendaLayout.addView(agendaButton);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No hay agendas disponibles", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAgendas(); // Recargar la lista cuando regrese a la actividad principal
    }

    // Gestión del resultado del permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos de notificación concedidos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permisos de notificación denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
