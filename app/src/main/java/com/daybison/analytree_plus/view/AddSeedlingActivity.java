package com.daybison.analytree_plus.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daybison.analytree_plus.R;
import com.daybison.analytree_plus.controller.SeedlingController;
import com.daybison.analytree_plus.entities.Seedling;

public class AddSeedlingActivity extends AppCompatActivity {

    SeedlingController seedlingController;

    RadioGroup radioStatusSeedlings;
    RadioGroup radioGroupSeedlings;
    RadioButton checkboxStatusLife;
    RadioButton checkboxStatusDeath;
    RadioButton checkboxGroupRecobr;
    RadioButton checkboxGroupDiversity;
    Button btnSaveAltSeedling;
    Button btnClearFormSeedling;
    ImageButton btnBackScreenSeedling;
    EditText inputIndividualNumber;
    EditText inputPopularName;
    EditText inputCientificName;
    EditText inputHeightSeedling;
    EditText inputCAPSeedling;
    EditText inputObservationSeedling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_seedling);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        seedlingController = new SeedlingController(AddSeedlingActivity.this);


        btnSaveAltSeedling = findViewById(R.id.btnSaveAltSeedling);
        radioStatusSeedlings = findViewById(R.id.radioStatusSeedlings);
        radioGroupSeedlings = findViewById(R.id.radioGroupSeedlings);
        btnBackScreenSeedling = findViewById(R.id.btnBackScreenSeedling);
        btnClearFormSeedling = findViewById(R.id.btnClearFormSeedling);


        inputIndividualNumber = findViewById(R.id.inputIndividualNumber);
        inputPopularName = findViewById(R.id.inputPopularName);
        inputCientificName = findViewById(R.id.inputCientificName);
        inputHeightSeedling = findViewById(R.id.inputHeightSeedling);
        inputCAPSeedling = findViewById(R.id.inputCAPSeedling);
        inputObservationSeedling = findViewById(R.id.inputObservationSeedling);

        btnClearFormSeedling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        btnBackScreenSeedling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        btnSaveAltSeedling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String individualNumber;
                String popularName;
                String cientificName;
                String heightSeedling;
                String CAPSeedling;
                String observationSeedling;
                String groupValue = "";

                // Obter o ID do RadioButton selecionado no radioStatusSeedlings
                int selectedStatusId = radioStatusSeedlings.getCheckedRadioButtonId();
                int selectedGroupId = radioGroupSeedlings.getCheckedRadioButtonId();
                individualNumber = inputIndividualNumber.getText().toString().trim().toUpperCase();
                // Verificar se algum RadioButton foi selecionado
                if (selectedStatusId != -1 && !individualNumber.isEmpty()) {
                    // Obter os RadioButtons selecionados
                    RadioButton selectedStatusRadioButton = findViewById(selectedStatusId);
                    RadioButton selectedGroupRadioButton = findViewById(selectedGroupId);

                    // Obter os valores dos RadioButtons utilizando a propriedade tag
                    String statusValue = (String) selectedStatusRadioButton.getTag();
                    String portionId = getIntent().getStringExtra("PORTION_ID_SEEDLING");


                    if(statusValue.equals("Morto")){
                        groupValue = " ";
                        popularName = "Muda Morta";
                        cientificName = " ";
                        heightSeedling = " ";
                        CAPSeedling = " ";
                        observationSeedling = " ";
                    } else {
                        groupValue = (String) selectedGroupRadioButton.getTag();
                        popularName = inputPopularName.getText().toString().trim().toUpperCase();
                        cientificName = inputCientificName.getText().toString().trim().toUpperCase();
                        heightSeedling = inputHeightSeedling.getText().toString().trim().toUpperCase();
                        CAPSeedling = inputCAPSeedling.getText().toString().trim().toUpperCase();
                        observationSeedling = inputObservationSeedling.getText().toString().trim().toUpperCase();
                    }


                    Seedling newSeedling = new Seedling(individualNumber, popularName, cientificName, heightSeedling, CAPSeedling, statusValue, groupValue, observationSeedling, portionId);

                    seedlingController.save(newSeedling);
                    Toast.makeText(AddSeedlingActivity.this, "Muda salva com sucesso!", Toast.LENGTH_SHORT).show();
                    clear();

                } else {
                    Toast.makeText(AddSeedlingActivity.this, "Status e Numero individuo são Obrigatórios!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clear() {

        // Obter o ID do RadioButton selecionado no radioStatusSeedlings
        inputIndividualNumber.setText("");
        inputPopularName.setText("");
        inputCientificName.setText("");
        inputHeightSeedling.setText("");
        inputCAPSeedling.setText("");
        inputObservationSeedling.setText("");
    }
}