package com.daybison.analytree_plus.configs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.daybison.analytree_plus.entities.Portion;
import com.daybison.analytree_plus.entities.PortionAndSeedlings;
import com.daybison.analytree_plus.entities.Seedling;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "analytree";
    private static final int DB_VERSION = 2;

    Cursor cursor;
    SQLiteDatabase db;

    //Definição das tabelas utilizadas pelo APP
    public static final String CREATE_PORTION_TABLE =
            "CREATE TABLE portions (\n" +
                    "    id TEXT PRIMARY KEY NOT NULL,\n" +
                    "    name TEXT,\n" +
                    "    deleted INTEGER,\n" +
                    "    created_in TEXT,\n" +
                    "    qtySeedling INTEGER\n" +
                    ")";

    public static final String CREATE_SEEDLING_TABLE =
            "CREATE TABLE seedlings (\n" +
                    "    id TEXT PRIMARY KEY,\n" +
                    "    individualNumber TEXT,\n" +
                    "    popularName TEXT,\n" +
                    "    popularScientific TEXT,\n" +
                    "    height TEXT,\n" +
                    "    cap TEXT,\n" +
                    "    statusSeedling TEXT,\n" +
                    "    groupSeedling TEXT,\n" +
                    "    observation TEXT,\n" +
                    "    portions_id TEXT,\n" +
                    "    created_in TEXT,\n" +
                    "    FOREIGN KEY (portions_id) REFERENCES portions(id)\n" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PORTION_TABLE);
        db.execSQL(CREATE_SEEDLING_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void saveData(String tableName, ContentValues data){
        db.insert(tableName, null, data);
    }

    public Portion findPortionById(String id) {
        Portion portion = null;
        Cursor cursor = null;

        try {
            // Consulta SQL corrigida
            String querySql = "SELECT * FROM portions WHERE id = ?";

            // Executa a consulta
            cursor = db.rawQuery(querySql, new String[]{id});

            // Verifica se há resultados e itera sobre eles
            if (cursor != null && cursor.moveToFirst()) {
                // Cria uma nova instância de Portion
                portion = new Portion();

                // Obtém os índices das colunas
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int deletedIndex = cursor.getColumnIndex("deleted");
                int createdInIndex = cursor.getColumnIndex("created_in");
                int qtySeedlingIndex = cursor.getColumnIndex("qtySeedling");

                // Verifica se os índices são válidos
                if (idIndex != -1 && nameIndex != -1 && deletedIndex != -1 &&
                        createdInIndex != -1 && qtySeedlingIndex != -1) {
                    // Preenche os campos da instância Portion com os dados do Cursor
                    portion.setId(cursor.getString(idIndex));
                    portion.setName(cursor.getString(nameIndex));
                    portion.setDeleted(cursor.getInt(deletedIndex) == 1); // Converte INTEGER para Boolean
                    portion.setCreated_in(cursor.getString(createdInIndex));
                    portion.setQtySeedling(cursor.getInt(qtySeedlingIndex));
                } else {
                    Log.w("DatabaseWarning", "One or more column indices are invalid.");
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error retrieving data: " + e.getMessage());
        } finally {
            // Garante que o cursor seja fechado para liberar recursos
            if (cursor != null) {
                cursor.close();
            }
        }

        return portion;
    }



    public ArrayList<Portion> findAllPortion() {
        ArrayList<Portion> allPortions = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Consulta SQL corrigida
            String querySql = "SELECT * FROM portions ORDER BY created_in DESC";

            // Executa a consulta
            cursor = db.rawQuery(querySql, null);

            // Verifica se há resultados e itera sobre eles
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Cria uma nova instância de Portion
                    Portion data = new Portion();

                    // Obtém os índices das colunas
                    int idIndex = cursor.getColumnIndex("id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int deletedIndex = cursor.getColumnIndex("deleted");
                    int createdInIndex = cursor.getColumnIndex("created_in");
                    int qtySeedlingIndex = cursor.getColumnIndex("qtySeedling");

                    // Verifica se os índices são válidos
                    if (idIndex != -1 && nameIndex != -1 && deletedIndex != -1 &&
                            createdInIndex != -1 && qtySeedlingIndex != -1) {
                        // Preenche os campos da instância Portion com os dados do Cursor
                        data.setId(cursor.getString(idIndex));
                        data.setName(cursor.getString(nameIndex));
                        data.setDeleted(cursor.getInt(deletedIndex) == 1); // Converte INTEGER para Boolean
                        data.setCreated_in(cursor.getString(createdInIndex));
                        data.setQtySeedling(cursor.getInt(qtySeedlingIndex));

                        // Adiciona a instância à lista
                        allPortions.add(data);
                    } else {
                        Log.w("DatabaseWarning", "One or more column indices are invalid.");
                    }

                } while (cursor.moveToNext()); // Move para o próximo item
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error retrieving data: " + e.getMessage());
        } finally {
            // Garante que o cursor seja fechado para liberar recursos
            if (cursor != null) {
                cursor.close();
            }
        }

        return allPortions;
    }

    public ArrayList<PortionAndSeedlings> findAllPortionAndSeedlings() {
        ArrayList<PortionAndSeedlings> allPortionAndSeedling = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Consulta SQL para recuperar todos os seedlings
            String querySql = "SELECT P.name as nameportion, S.statusSeedling, S.groupSeedling, S.individualNumber, S.popularName, S.popularScientific, S.height, S.cap, S.observation, s.created_in FROM SEEDLINGS S\n" +
                    "JOIN PORTIONS P ON S.portions_id = P.id\n" +
                    "ORDER BY P.name desc";

            // Executa a consulta
            cursor = db.rawQuery(querySql, null);

            // Verifica se há resultados e itera sobre eles
            if (cursor != null && cursor.moveToFirst()) {
                do {


                    // Cria uma nova instância de Seedling
                    PortionAndSeedlings data = new PortionAndSeedlings();

                    // Obtém os índices das colunas
                    int nameportionIndex = cursor.getColumnIndex("nameportion");
                    int statusSeedlingIndex = cursor.getColumnIndex("statusSeedling");
                    int groupSeedlingIndex = cursor.getColumnIndex("groupSeedling");
                    int individualNumberIndex = cursor.getColumnIndex("individualNumber");
                    int popularNameIndex = cursor.getColumnIndex("popularName");
                    int popularScientificIndex = cursor.getColumnIndex("popularScientific");
                    int heightIndex = cursor.getColumnIndex("height");
                    int capIndex = cursor.getColumnIndex("cap");
                    int observationIndex = cursor.getColumnIndex("observation");
                    int created_inIdIndex = cursor.getColumnIndex("created_in");

                    // Verifica se os índices são válidos
                    if (nameportionIndex != -1 && statusSeedlingIndex != -1 && groupSeedlingIndex != -1 &&
                            individualNumberIndex != -1 && popularNameIndex != -1 && popularScientificIndex != -1 &&
                            heightIndex != -1 && capIndex != -1 &&
                            observationIndex != -1 && created_inIdIndex != -1 ) {

                        // Preenche os campos da instância Seedling com os dados do Cursor
                        data.setNamePortion(cursor.getString(nameportionIndex));
                        data.setStatusSeedling(cursor.getString(statusSeedlingIndex));
                        data.setGroupSeedling(cursor.getString(groupSeedlingIndex));
                        data.setIndividualNumber(cursor.getString(individualNumberIndex));
                        data.setPopularName(cursor.getString(popularNameIndex));
                        data.setPopularScientific(cursor.getString(popularScientificIndex));
                        data.setHeightSeedling(cursor.getString(heightIndex));
                        data.setCapSeedling(cursor.getString(capIndex));
                        data.setObservationSeedling(cursor.getString(observationIndex));
                        data.setCreatedInSeedling(cursor.getString(created_inIdIndex));


                        // Adiciona a instância à lista
                        allPortionAndSeedling.add(data);
                    } else {
                        Log.w("DatabaseWarning", "One or more column indices are invalid.");
                    }

                } while (cursor.moveToNext()); // Move para o próximo item
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error retrieving data: " + e.getMessage());
        } finally {
            // Garante que o cursor seja fechado para liberar recursos
            if (cursor != null) {
                cursor.close();
            }
        }

        return allPortionAndSeedling;
    }

    public ArrayList<Seedling> findAllSeedlings() {
        ArrayList<Seedling> allSeedlings = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Consulta SQL para recuperar todos os seedlings
            String querySql = "SELECT * FROM seedlings ORDER BY created_in DESC";

            // Executa a consulta
            cursor = db.rawQuery(querySql, null);

            // Verifica se há resultados e itera sobre eles
            if (cursor != null && cursor.moveToFirst()) {
                do {


                    // Cria uma nova instância de Seedling
                    Seedling data = new Seedling();

                    // Obtém os índices das colunas
                    int idIndex = cursor.getColumnIndex("id");
                    int individualNumberIndex = cursor.getColumnIndex("individualNumber");
                    int popularNameIndex = cursor.getColumnIndex("popularName");
                    int popularscientificIndex = cursor.getColumnIndex("popularScientific");
                    int heightIndex = cursor.getColumnIndex("height");
                    int capIndex = cursor.getColumnIndex("cap");
                    int statusSeedlingIndex = cursor.getColumnIndex("statusSeedling");
                    int groupSeedlingIndex = cursor.getColumnIndex("groupSeedling");
                    int observationIndex = cursor.getColumnIndex("observation");
                    int portionsIdIndex = cursor.getColumnIndex("portions_id");
                    int createdInIndex = cursor.getColumnIndex("created_in");

                    // Verifica se os índices são válidos
                    if (idIndex != -1 && individualNumberIndex != -1 && popularNameIndex != -1 &&
                            popularscientificIndex != -1 && heightIndex != -1 && capIndex != -1 &&
                            statusSeedlingIndex != -1 && groupSeedlingIndex != -1 &&
                            observationIndex != -1 && portionsIdIndex != -1 && createdInIndex != -1) {

                        // Preenche os campos da instância Seedling com os dados do Cursor
                        data.setId(cursor.getString(idIndex));
                        data.setIndividualNumber(cursor.getString(individualNumberIndex));
                        data.setPopularName(cursor.getString(popularNameIndex));
                        data.setPopularscientific(cursor.getString(popularscientificIndex));
                        data.setHeight(cursor.getString(heightIndex));
                        data.setCap(cursor.getString(capIndex));
                        data.setStatusSeedling(cursor.getString(statusSeedlingIndex));
                        data.setGroupSeedling(cursor.getString(groupSeedlingIndex));
                        data.setObservation(cursor.getString(observationIndex));
                        data.setPortions_id(cursor.getString(portionsIdIndex));
                        data.setCreated_in(cursor.getString(createdInIndex));

                        // Adiciona a instância à lista
                        allSeedlings.add(data);
                    } else {
                        Log.w("DatabaseWarning", "One or more column indices are invalid.");
                    }

                } while (cursor.moveToNext()); // Move para o próximo item
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error retrieving data: " + e.getMessage());
        } finally {
            // Garante que o cursor seja fechado para liberar recursos
            if (cursor != null) {
                cursor.close();
            }
        }

        return allSeedlings;
    }



    public ArrayList<Seedling> findAllSeedlingsByPortionId(String portion_id) {
        ArrayList<Seedling> allSeedlings = new ArrayList<>();
        Cursor cursor = null;


        try {
            // Consulta SQL para recuperar todos os seedlings
            String querySql = "SELECT * FROM seedlings WHERE portions_id = ? ORDER BY created_in DESC";
            // Executa a consulta
            cursor = db.rawQuery(querySql, new String[]{portion_id});

            // Verifica se há resultados e itera sobre eles
            if (cursor != null && cursor.moveToFirst()) {
                do {


                    // Cria uma nova instância de Seedling
                    Seedling data = new Seedling();

                    // Obtém os índices das colunas
                    int idIndex = cursor.getColumnIndex("id");
                    int individualNumberIndex = cursor.getColumnIndex("individualNumber");
                    int popularNameIndex = cursor.getColumnIndex("popularName");
                    int popularscientificIndex = cursor.getColumnIndex("popularScientific");
                    int heightIndex = cursor.getColumnIndex("height");
                    int capIndex = cursor.getColumnIndex("cap");
                    int statusSeedlingIndex = cursor.getColumnIndex("statusSeedling");
                    int groupSeedlingIndex = cursor.getColumnIndex("groupSeedling");
                    int observationIndex = cursor.getColumnIndex("observation");
                    int portionsIdIndex = cursor.getColumnIndex("portions_id");
                    int createdInIndex = cursor.getColumnIndex("created_in");

                    // Verifica se os índices são válidos
                    if (idIndex != -1 && individualNumberIndex != -1 && popularNameIndex != -1 &&
                            popularscientificIndex != -1 && heightIndex != -1 && capIndex != -1 &&
                            statusSeedlingIndex != -1 && groupSeedlingIndex != -1 &&
                            observationIndex != -1 && portionsIdIndex != -1 && createdInIndex != -1) {

                        // Preenche os campos da instância Seedling com os dados do Cursor
                        data.setId(cursor.getString(idIndex));
                        data.setIndividualNumber(cursor.getString(individualNumberIndex));
                        data.setPopularName(cursor.getString(popularNameIndex));
                        data.setPopularscientific(cursor.getString(popularscientificIndex));
                        data.setHeight(cursor.getString(heightIndex));
                        data.setCap(cursor.getString(capIndex));
                        data.setStatusSeedling(cursor.getString(statusSeedlingIndex));
                        data.setGroupSeedling(cursor.getString(groupSeedlingIndex));
                        data.setObservation(cursor.getString(observationIndex));
                        data.setPortions_id(cursor.getString(portionsIdIndex));
                        data.setCreated_in(cursor.getString(createdInIndex));

                        // Adiciona a instância à lista
                        allSeedlings.add(data);
                    } else {
                        Log.w("DatabaseWarning", "One or more column indices are invalid.");
                    }

                } while (cursor.moveToNext()); // Move para o próximo item
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error retrieving data: " + e.getMessage());
        } finally {
            // Garante que o cursor seja fechado para liberar recursos
            if (cursor != null) {
                cursor.close();
            }
        }

        return allSeedlings;
    }

    public void updateSeedling(Seedling seedling) {
        // Cria um ContentValues para armazenar os novos valores
        ContentValues values = new ContentValues();
        values.put("individualNumber", seedling.getIndividualNumber());
        values.put("popularName", seedling.getPopularName());
        values.put("popularScientific", seedling.getPopularscientific());
        values.put("height", seedling.getHeight());
        values.put("cap", seedling.getCap());
        values.put("statusSeedling", seedling.getStatusSeedling());
        values.put("portions_id", seedling.getPortions_id());
        values.put("groupSeedling", seedling.getGroupSeedling());
        values.put("observation", seedling.getObservation());
        values.put("portions_id", seedling.getPortions_id());
        values.put("created_in", seedling.getCreated_in());

        try {
            // Executa a atualização com base no ID do Seedling
            int rowsAffected = db.update("seedlings", values, "id = ?", new String[]{seedling.getId()});

            if (rowsAffected > 0) {

                Log.i("DatabaseInfo", "Seedling updated successfully.");
            } else {
                Log.w("DatabaseWarning", "No Seedling found with the given ID.");
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error updating data: " + e.getMessage());
        }
    }

    public void deleteSeedling(Seedling seedling){

        try {
            // Executa a atualização com base no ID do Seedling
            int rowsAffected = db.delete("seedlings","id = ?", new String[]{seedling.getId()});

            if (rowsAffected > 0) {

                Log.i("DatabaseInfo", "Seedling deleted successfully.");
            } else {
                Log.w("DatabaseWarning", "No Seedling found with the given ID.");
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error deleting data: " + e.getMessage());
        }
    }

    public void deletePortionAndSeedlings(String id_portion) {
        try {
            // Inicia uma transação
            db.beginTransaction();

            // Deleta todas as seedlings relacionadas ao idPortion
            db.delete("seedlings", "portions_id = ?", new String[]{String.valueOf(id_portion)});

            // Deleta a porção
            db.delete("portions", "id = ?", new String[]{String.valueOf(id_portion)});

            // Confirma a transação
            db.setTransactionSuccessful();
        } finally {
            // Termina a transação
            db.endTransaction();
            db.close();
        }
    }

}
