package com.daybison.analytree_plus.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daybison.analytree_plus.R;
import com.daybison.analytree_plus.controller.SeedlingController;
import com.daybison.analytree_plus.entities.Seedling;

public class ViewSeedlingActivity extends AppCompatActivity {
    SeedlingController seedlingController;
    TextView txtTitleMainView;
    ImageButton btnBackScreenSeedlingView;
    RadioGroup radioStatusSeedlingsView;
    RadioGroup radioGroupSeedlingsView;
    RadioButton checkboxStatusLifeView;
    RadioButton checkboxStatusDeathView;
    RadioButton checkboxGroupRecobrView;
    RadioButton checkboxGroupDiversityView;

    EditText inputIndividualNumberView;
    EditText inputPopularNameView;
    EditText inputCientificNameView;
    EditText inputHeightSeedlingView;
    EditText inputCAPSeedlingView;
    EditText inputObservationSeedlingView;

    Button btnSaveAltSeedlingView;
    Button btnDeleteSeedlingView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_seedling);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        seedlingController = new SeedlingController(this);

        String SEEDLING_ID = getIntent().getStringExtra("SEEDLING_ID");
        String SEEDLING_STATUS = getIntent().getStringExtra("SEEDLING_STATUS");
        String SEEDLING_GROUP = getIntent().getStringExtra("SEEDLING_GROUP");
        String SEEDLING_INDIVIDUAL_NUMBER = getIntent().getStringExtra("SEEDLING_INDIVIDUAL_NUMBER");
        String SEEDLING_POPULAR = getIntent().getStringExtra("SEEDLING_POPULAR");
        String SEEDLING_CIENTIFIC = getIntent().getStringExtra("SEEDLING_CIENTIFIC");
        String SEEDLING_HEIGHT = getIntent().getStringExtra("SEEDLING_HEIGHT");
        String SEEDLING_CREATE_IN = getIntent().getStringExtra("SEEDLING_CREATE_IN");
        String SEEDLING_CAP = getIntent().getStringExtra("SEEDLING_CAP");
        String SEEDLING_OBSERVATION = getIntent().getStringExtra("SEEDLING_OBSERVATION");
        String SEEDLING_PORTION_ID = getIntent().getStringExtra("SEEDLING_PORTION_ID");

        txtTitleMainView = findViewById(R.id.txtTitleMainView);
        txtTitleMainView.setText(SEEDLING_POPULAR.toUpperCase());
        inputIndividualNumberView = findViewById(R.id.inputIndividualNumberView);
        inputIndividualNumberView.setText(SEEDLING_INDIVIDUAL_NUMBER);
        inputPopularNameView = findViewById(R.id.inputPopularNameView);
        inputPopularNameView.setText(SEEDLING_POPULAR);
        inputCientificNameView = findViewById(R.id.inputCientificNameView);
        inputCientificNameView.setText(SEEDLING_CIENTIFIC);
        inputHeightSeedlingView = findViewById(R.id.inputHeightSeedlingView);
        inputHeightSeedlingView.setText(SEEDLING_HEIGHT);
        inputCAPSeedlingView = findViewById(R.id.inputCAPSeedlingView);
        inputCAPSeedlingView.setText(SEEDLING_CAP);
        inputObservationSeedlingView = findViewById(R.id.inputObservationSeedlingView);
        inputObservationSeedlingView.setText(SEEDLING_OBSERVATION);


        btnBackScreenSeedlingView = findViewById(R.id.btnBackScreenSeedlingView);
        btnSaveAltSeedlingView = findViewById(R.id.btnSaveAltSeedlingView);
        btnDeleteSeedlingView = findViewById(R.id.btnDeleteSeedlingView);


        radioStatusSeedlingsView = findViewById(R.id.radioStatusSeedlingsView);
        checkboxStatusLifeView = findViewById(R.id.checkboxStatusLifeView);
        checkboxStatusDeathView = findViewById(R.id.checkboxStatusDeathView);
        radioGroupSeedlingsView = findViewById(R.id.radioGroupSeedlingsView);
        checkboxGroupRecobrView = findViewById(R.id.checkboxGroupRecobrView);
        checkboxGroupDiversityView = findViewById(R.id.checkboxGroupDiversityView);


        if (SEEDLING_STATUS.equals("Vivo")) {
            checkboxStatusLifeView.setChecked(true);
        } else if (SEEDLING_STATUS.equals("Morto")) {
            checkboxStatusDeathView.setChecked(true);
        }

        if (SEEDLING_GROUP.equals("Recobrimento")) {
            checkboxGroupRecobrView.setChecked(true);
        } else if (SEEDLING_GROUP.equals("Diversidade")) {
            checkboxGroupDiversityView.setChecked(true);
        }

        btnBackScreenSeedlingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeleteSeedlingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Confirmar Exclusão")
                        .setMessage("Tem certeza de que deseja excluir a muda: " + inputPopularNameView.getText().toString().toUpperCase() + "?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
// Obter o ID do RadioButton selecionado no radioStatusSeedlings
                                int selectedStatusId = radioStatusSeedlingsView.getCheckedRadioButtonId();
                                int selectedGroupId = radioGroupSeedlingsView.getCheckedRadioButtonId();
                                // Obter os RadioButtons selecionados
                                RadioButton selectedStatusRadioButton = findViewById(selectedStatusId);
                                RadioButton selectedGroupRadioButton = findViewById(selectedGroupId);

                                // Obter os valores dos RadioButtons utilizando a propriedade tag
                                String statusValue = (String) selectedStatusRadioButton.getTag();
                                String groupValue = (String) selectedGroupRadioButton.getTag();

                                Seedling Seedling = new Seedling();
                                Seedling.setId(SEEDLING_ID);
                                Seedling.setStatusSeedling(statusValue);
                                Seedling.setGroupSeedling(groupValue);
                                Seedling.setIndividualNumber(inputPopularNameView.getText().toString().toUpperCase());
                                Seedling.setPopularName(inputPopularNameView.getText().toString().toUpperCase());
                                Seedling.setPopularscientific(inputCientificNameView.getText().toString().toUpperCase());
                                Seedling.setPortions_id(SEEDLING_PORTION_ID);
                                Seedling.setCreated_in(SEEDLING_CREATE_IN);
                                Seedling.setHeight(inputHeightSeedlingView.getText().toString().toUpperCase());
                                Seedling.setCap(inputCAPSeedlingView.getText().toString().toUpperCase());
                                Seedling.setObservation(inputObservationSeedlingView.getText().toString().toUpperCase());

                                seedlingController.delete(Seedling);

                                Toast.makeText(ViewSeedlingActivity.this, "Muda deletada com sucesso!", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();

            }
        });


        btnSaveAltSeedlingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    // Obter o ID do RadioButton selecionado no radioStatusSeedlings
                    int selectedStatusId = radioStatusSeedlingsView.getCheckedRadioButtonId();
                    int selectedGroupId = radioGroupSeedlingsView.getCheckedRadioButtonId();
                    // Obter os RadioButtons selecionados
                    RadioButton selectedStatusRadioButton = findViewById(selectedStatusId);
                    RadioButton selectedGroupRadioButton = findViewById(selectedGroupId);

                    // Obter os valores dos RadioButtons utilizando a propriedade tag
                    String statusValue = (String) selectedStatusRadioButton.getTag();
                    String groupValue = (String) selectedGroupRadioButton.getTag();

                    Seedling newSeedling = new Seedling();
                    newSeedling.setId(SEEDLING_ID);
                    newSeedling.setStatusSeedling(statusValue);
                    newSeedling.setGroupSeedling(groupValue);
                    newSeedling.setIndividualNumber(inputPopularNameView.getText().toString().toUpperCase());
                    newSeedling.setPopularName(inputPopularNameView.getText().toString().toUpperCase());
                    newSeedling.setPopularscientific(inputCientificNameView.getText().toString().toUpperCase());
                    newSeedling.setPortions_id(SEEDLING_PORTION_ID);
                    newSeedling.setCreated_in(SEEDLING_CREATE_IN);
                    newSeedling.setHeight(inputHeightSeedlingView.getText().toString().toUpperCase());
                    newSeedling.setCap(inputCAPSeedlingView.getText().toString().toUpperCase());
                    newSeedling.setObservation(inputObservationSeedlingView.getText().toString().toUpperCase());


                    seedlingController.update(newSeedling);
                    Toast.makeText(ViewSeedlingActivity.this, "Muda salva com sucesso!", Toast.LENGTH_SHORT).show();

                    finish();
                } catch (Exception e) {

                }
            }
        });


    }
}