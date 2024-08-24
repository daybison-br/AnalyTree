package com.daybison.analytree_plus.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daybison.analytree_plus.R;
import com.daybison.analytree_plus.controller.PortionController;
import com.daybison.analytree_plus.entities.Portion;

import java.util.concurrent.Executors;

public class AddPortionActivity extends AppCompatActivity {

    ImageButton btnBackScreenPortion;
    Button btnSavePortion;
    EditText inputNamePortion;

    PortionController portionController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_portion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        portionController = new PortionController(AddPortionActivity.this);

        btnBackScreenPortion = findViewById(R.id.btnBackScreenPortion);
        btnSavePortion = findViewById(R.id.btnSavePortion);
        inputNamePortion = findViewById(R.id.inputNamePortion);

        btnBackScreenPortion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSavePortion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputNamePortion.getText().toString().trim();
                if (name.isEmpty()) {
                    // Exibir mensagem de erro se o campo estiver vazio
                    Toast.makeText(AddPortionActivity.this, "Os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Portion newPortion = new Portion(name.toUpperCase(), false);

                String nameCaptalize = name.toUpperCase();
                Toast.makeText(AddPortionActivity.this,nameCaptalize+" salva com sucesso!", Toast.LENGTH_SHORT).show();
                inputNamePortion.setText("");

                // Usar uma thread separada para a operação de inserção
                Executors.newSingleThreadExecutor().execute(() -> {
                    //portionDao.insert(newPortion);
                    portionController.save(newPortion);
                });
            }
        });
    }
}