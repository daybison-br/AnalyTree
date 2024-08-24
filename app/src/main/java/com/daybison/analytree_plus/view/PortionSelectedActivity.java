package com.daybison.analytree_plus.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daybison.analytree_plus.R;
import com.daybison.analytree_plus.adapter.SeedlingAdapter;
import com.daybison.analytree_plus.controller.PortionController;
import com.daybison.analytree_plus.controller.SeedlingController;
import com.daybison.analytree_plus.databinding.ActivityPortionSelectedBinding;
import com.daybison.analytree_plus.entities.Portion;
import com.daybison.analytree_plus.entities.Seedling;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.ArrayList;

public class PortionSelectedActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100;
    private static final int REQUEST_CODE_MANAGE_EXTERNAL_STORAGE = 101;
    private static final int REQUEST_CODE_CREATE_FILE = 1;

    private ActivityPortionSelectedBinding binding;
    private ArrayList<Seedling> seedlingList = new ArrayList<>();
    SeedlingAdapter seedlingAdapter;

    PortionController portionController;
    SeedlingController seedlingController;
    Portion portionSelected;

    private SwipeRefreshLayout swipeRefreshLayoutSeedling;

    ImageButton btnBackScreenPortionSelected;
    TextView txtTitlePortionSelected;
    Button btnAddSeedling;
    Button btnExportSeedlingPortion;
    Button btnDeletePortion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            binding = ActivityPortionSelectedBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.PortionSelectedMain), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            swipeRefreshLayoutSeedling = findViewById(R.id.swipeRefreshLayoutSeedling);
            swipeRefreshLayoutSeedling.setOnRefreshListener(() -> {
                String portionsIdRefresh = getIntent().getStringExtra("PORTION_ID");
                refreshDataSeedling(portionsIdRefresh);
            });


            String portionsId = getIntent().getStringExtra("PORTION_ID");
            seedlingController = new SeedlingController(PortionSelectedActivity.this);
            seedlingList = seedlingController.getAllSeedlingsByPortionId(portionsId);

            RecyclerView recyclerViewSeedling = binding.RecyclerViewSeedling;
            recyclerViewSeedling.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewSeedling.setHasFixedSize(true);

            seedlingAdapter = new SeedlingAdapter(seedlingList, this, seedling -> {
                Intent intent = new Intent(PortionSelectedActivity.this, ViewSeedlingActivity.class);
                intent.putExtra("SEEDLING_ID", String.valueOf(seedling.getId()));
                intent.putExtra("SEEDLING_STATUS", String.valueOf(seedling.getStatusSeedling()));
                intent.putExtra("SEEDLING_GROUP", String.valueOf(seedling.getGroupSeedling()));
                intent.putExtra("SEEDLING_INDIVIDUAL_NUMBER", String.valueOf(seedling.getIndividualNumber()));
                intent.putExtra("SEEDLING_POPULAR", String.valueOf(seedling.getPopularName()));
                intent.putExtra("SEEDLING_CIENTIFIC", String.valueOf(seedling.getPopularscientific()));
                intent.putExtra("SEEDLING_HEIGHT", String.valueOf(seedling.getHeight()));
                intent.putExtra("SEEDLING_CAP", String.valueOf(seedling.getCap()));
                intent.putExtra("SEEDLING_OBSERVATION", String.valueOf(seedling.getObservation()));
                intent.putExtra("SEEDLING_CREATE_IN", String.valueOf(seedling.getCreated_in()));
                intent.putExtra("SEEDLING_PORTION_ID", getIntent().getStringExtra("PORTION_ID"));
                startActivity(intent);
            });
            recyclerViewSeedling.setAdapter(seedlingAdapter);

            String portionId = getIntent().getStringExtra("PORTION_ID");
            portionController = new PortionController(PortionSelectedActivity.this);
            portionSelected = portionController.getPortionsById(portionId);

            btnBackScreenPortionSelected = findViewById(R.id.btnBackScreenPortionSelected);
            txtTitlePortionSelected = findViewById(R.id.txtTitlePortionSelected);
            btnAddSeedling = findViewById(R.id.btnAddSeedling);
            btnDeletePortion = findViewById(R.id.btnDeletePortion);
            btnExportSeedlingPortion = findViewById(R.id.btnExportSeedlingPortion);

            btnDeletePortion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Confirmar Exclusão")
                            .setMessage("Tem certeza de que deseja excluir a Parcela: "+portionSelected.getName().toUpperCase()+"?")
                            .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    portionController.delete(portionId);
                                    Toast.makeText(PortionSelectedActivity.this, "Parcela e Mudas deletadas com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();


                }
            });

            btnBackScreenPortionSelected.setOnClickListener(v -> finish());
            btnAddSeedling.setOnClickListener(v -> {
                Intent MainScreen = new Intent(PortionSelectedActivity.this, AddSeedlingActivity.class);
                MainScreen.putExtra("PORTION_ID_SEEDLING", portionId);
                startActivity(MainScreen);
            });

            txtTitlePortionSelected.setText(portionSelected.getName().toUpperCase());

            btnExportSeedlingPortion.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // Para Android 11 (API 30) até a versão mais recente (incluindo Android 14)
                    if (Environment.isExternalStorageManager()) {
                        // Permissão concedida
                        exportDataToXlsx();
                    } else {
                        // Solicita permissão de gerenciamento de armazenamento
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, REQUEST_CODE_MANAGE_EXTERNAL_STORAGE);
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Para Android 8 (API 26) até Android 10 (API 29)
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Solicita a permissão de gravação em armazenamento externo
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
                    } else {
                        // Permissão concedida
                        exportDataToXlsx();
                    }
                } else {
                    // Para versões anteriores ao Android 8
                    exportDataToXlsx(); // Nenhuma permissão adicional é necessária
                }
            });

            checkAndRequestPermissions();

        } catch (Exception e) {
            Log.e("PortionSelectedActivity", "Exception in onCreate: " + e.getMessage());
        }
    }

    public void refreshDataSeedling(String portions_id) {
        seedlingList.clear();
        seedlingList.addAll(seedlingController.getAllSeedlingsByPortionId(portions_id));
        seedlingAdapter.notifyDataSetChanged();
        swipeRefreshLayoutSeedling.setRefreshing(false);
    }

    private void checkAndRequestPermissions() {
        // A verificação e solicitação de permissões já estão tratadas no clique do botão de exportação
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportDataToXlsx(); // Atualizado para exportar como XLSX
            } else {
                Log.e("Permission", "Permissão de gravação negada.");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MANAGE_EXTERNAL_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                exportDataToXlsx(); // Atualizado para exportar como XLSX
            } else {
                Log.e("Permission", "Permissão de gerenciamento de arquivos negada.");
            }
        } else if (requestCode == REQUEST_CODE_CREATE_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    writeXlsxToUri(uri, seedlingList); // Atualizado para usar o método XLSX
                }
            }
        }
    }

    private void exportDataToXlsx() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.putExtra(Intent.EXTRA_TITLE, "seedlings.xlsx");
        startActivityForResult(intent, REQUEST_CODE_CREATE_FILE);
    }


    private void writeXlsxToUri(Uri uri, ArrayList<Seedling> seedlings) {
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            if (outputStream != null) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Seedlings");

                // Criar o cabeçalho
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("NomeParcela");
                header.createCell(1).setCellValue("Status");
                header.createCell(2).setCellValue("Grupo");
                header.createCell(3).setCellValue("NumeroIndividuo");
                header.createCell(4).setCellValue("Nome Popular");
                header.createCell(5).setCellValue("Nome Científico");
                header.createCell(6).setCellValue("Altura");
                header.createCell(7).setCellValue("Cap");
                header.createCell(8).setCellValue("Observação");
                header.createCell(9).setCellValue("Data Registro");

                // Adicionar dados dos seedlings
                int rowNum = 1;
                for (Seedling seedling : seedlings) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(portionSelected.getName());
                    row.createCell(1).setCellValue(seedling.getStatusSeedling());
                    row.createCell(2).setCellValue(seedling.getGroupSeedling());
                    row.createCell(3).setCellValue(seedling.getIndividualNumber());
                    row.createCell(4).setCellValue(seedling.getPopularName());
                    row.createCell(5).setCellValue(seedling.getPopularscientific());
                    row.createCell(6).setCellValue(seedling.getHeight());
                    row.createCell(7).setCellValue(seedling.getCap());
                    row.createCell(8).setCellValue(seedling.getObservation());
                    row.createCell(9).setCellValue(seedling.getCreated_in());
                }

                workbook.write(outputStream);
                workbook.close();

                Toast.makeText(this, "Mudas exportadas com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao criar o arquivo Excel.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ExportExcel", "Erro ao exportar Excel: " + e.getMessage());
            Toast.makeText(this, "Erro ao exportar Excel", Toast.LENGTH_SHORT).show();
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        // Escapa aspas e caracteres de nova linha
        return "\"" + value.replace("\"", "\"\"").replace("\n", " ") + "\"";
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!swipeRefreshLayoutSeedling.isRefreshing()) {

            swipeRefreshLayoutSeedling.setRefreshing(true);

            String portionsId = getIntent().getStringExtra("PORTION_ID");
            refreshDataSeedling(portionsId);
        }
    }

}
