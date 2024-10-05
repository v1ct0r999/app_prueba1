package cl.contreras.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OpcionesAgendaActivity extends AppCompatActivity {

    private TextView agendaNameTextView;
    private Button btnVerDetalles, btnEditar, btnEliminar, BotonAtrasAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_agenda);

        // Obtener el nombre de la agenda desde el Intent
        String agendaName = getIntent().getStringExtra("agenda_name");

        // Referencias a los elementos de la vista
        agendaNameTextView = findViewById(R.id.agendaNameTextView);
        btnVerDetalles = findViewById(R.id.btnVerDetalles);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        BotonAtrasAgenda = findViewById(R.id.BotonAtrasAgenda);

        // Mostrar el nombre de la agenda en el TextView
        if (agendaName != null) {
            agendaNameTextView.setText(agendaName);
        }

        // Aquí después agregaremos la lógica para cada botón
        btnVerDetalles.setOnClickListener(v -> {
            // ir a la pantalla para ver detalles
        });

        btnEditar.setOnClickListener(v -> {
            // ir a la pantalla para editar la agenda
        });

        btnEliminar.setOnClickListener(v -> {
            // aun nose si ir sa pantalla o hacer una alerta para eliminar la agenda

        });

        BotonAtrasAgenda.setOnClickListener(v -> {
            Intent intent = new Intent(OpcionesAgendaActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
