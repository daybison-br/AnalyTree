package com.daybison.analytree_plus.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormatDataUtils {

    // Método para obter a data formatDada
    public static String getFormattedCreatedIn() {
        long currentTime = System.currentTimeMillis();
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(currentTime);
    }
}
