package com.daybison.analytree_plus.view;

import android.Manifest;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import com.daybison.analytree_plus.adapter.PortionAdapter;
import com.daybison.analytree_plus.controller.PortionAndSeedlingsController;
import com.daybison.analytree_plus.controller.PortionController;
import com.daybison.analytree_plus.databinding.ActivityMainBinding;
import com.daybison.analytree_plus.entities.Portion;
import com.daybison.analytree_plus.entities.PortionAndSeedlings;
import com.daybison.analytree_plus.entities.Seedling;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100;
    private static final int REQUEST_CODE_MANAGE_EXTERNAL_STORAGE = 101;
    private static final int REQUEST_CODE_CREATE_FILE = 1;

    PortionController portionController;
    PortionAndSeedlingsController portionAndSeedlingsController;

    Button btnAddPortion;
    Button btnExportAllPortions;
    private ActivityMainBinding binding;
    private PortionAdapter portionAdapter;
    private ArrayList<Portion> portionList = new ArrayList<>();
    private ArrayList<PortionAndSeedlings> portionAndSeedlingsList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            EdgeToEdge.enable(this);
            setContentView(binding.getRoot());
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // Configura o SwipeRefreshLayout
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Atualiza os dados quando o gesto de puxar para baixo é detectado
                    refreshData();
                }
            });

            // Inicialize o controlador e obtenha os dados
            portionController = new PortionController(MainActivity.this);
            portionList = portionController.getAllPortions();

            portionAndSeedlingsController = new PortionAndSeedlingsController(MainActivity.this);
            portionAndSeedlingsList = portionAndSeedlingsController.getAllPortionAndSeedlings();


            RecyclerView recyclerViewPortion = binding.RecyclerViewPortion;
            recyclerViewPortion.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewPortion.setHasFixedSize(true);

            portionAdapter = new PortionAdapter(portionList, this, new PortionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Portion portion) {
                    Intent intent = new Intent(MainActivity.this, PortionSelectedActivity.class); // Substitua DetailActivity pela sua Activity de destino
                    intent.putExtra("PORTION_ID", String.valueOf(portion.getId()));// Passe qualquer dado necessário para a nova Activity
                    startActivity(intent);
                }
            });
            recyclerViewPortion.setAdapter(portionAdapter);


            btnAddPortion = findViewById(R.id.btnSaveAltSeedling);
            btnExportAllPortions = findViewById(R.id.btnExportAllPortions);

            btnAddPortion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent MainScreen = new Intent(MainActivity.this, AddPortionActivity.class);
                    startActivity(MainScreen);
                }
            });

            btnExportAllPortions.setOnClickListener(v -> {
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
            Log.e("MainActivity", "Exception in onCreate: " + e.getMessage());
        }


    }

    // Método para atualizar os dados
    private void refreshData() {
        // Atualize os dados do controlador
        portionList.clear();
        portionList.addAll(portionController.getAllPortions());

        portionAndSeedlingsList.clear();
        portionAndSeedlingsList.addAll(portionAndSeedlingsController.getAllPortionAndSeedlings());

        portionAdapter.notifyDataSetChanged();
        // Finalize a animação de carregamento
        swipeRefreshLayout.setRefreshing(false);
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
                    writeXlsxToUri(uri, portionAndSeedlingsList); // Atualizado para usar o método XLSX
                }
            }
        }
    }

    private void exportDataToXlsx() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.putExtra(Intent.EXTRA_TITLE, "portions.xlsx");
        startActivityForResult(intent, REQUEST_CODE_CREATE_FILE);
    }


    private void writeXlsxToUri(Uri uri, ArrayList<PortionAndSeedlings> portionAndSeedlings) {
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            if (outputStream != null) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Portions");

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
                for (PortionAndSeedlings seedlingPortion : portionAndSeedlings) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(seedlingPortion.getNamePortion());
                    row.createCell(1).setCellValue(seedlingPortion.getStatusSeedling());
                    row.createCell(2).setCellValue(seedlingPortion.getGroupSeedling());
                    row.createCell(3).setCellValue(seedlingPortion.getIndividualNumber());
                    row.createCell(4).setCellValue(seedlingPortion.getPopularName());
                    row.createCell(5).setCellValue(seedlingPortion.getPopularScientific());
                    row.createCell(6).setCellValue(seedlingPortion.getHeightSeedling());
                    row.createCell(7).setCellValue(seedlingPortion.getCapSeedling());
                    row.createCell(8).setCellValue(seedlingPortion.getObservationSeedling());
                    row.createCell(9).setCellValue(seedlingPortion.getCreatedInSeedling());
                }

                workbook.write(outputStream);
                workbook.close();

                Toast.makeText(this, "Parcelas exportadas com sucesso!", Toast.LENGTH_SHORT).show();
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

        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
            refreshData();
        }
    }

}