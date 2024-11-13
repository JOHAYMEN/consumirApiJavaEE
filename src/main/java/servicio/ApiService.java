package servicio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import modelo.ApiResponse;

public class ApiService {
    private static final String API_URL = "https://sisedevco.ikeasistencia.com/api-swagger/api/v1/controller/test?name=";

    public ApiResponse getNombreResponse(String name) {
        ApiResponse apiResponse = new ApiResponse();
        String urlString = API_URL + name;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 302) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseContent = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    responseContent.append(line);
                }
                in.close();

                // Parseo de JSON usando una biblioteca como Jackson o Gson
                String output = responseContent.toString();
                String[] parts = output.split("\"nombre\":\"");
                if (parts.length > 1) {
                    String nombreValue = parts[1].split("\"")[0];
                    apiResponse.setNombre(nombreValue);
                    apiResponse.setFecha(new java.util.Date().toString());
                } else {
                    apiResponse.setNombre("Formato de respuesta inesperado.");
                }
            } else if (responseCode == 404) {
                apiResponse.setNombre("Sin resultados.");
            } else {
                apiResponse.setNombre("Error inesperado. Código de respuesta: " + responseCode);
            }
        } catch (Exception e) {
            apiResponse.setNombre("Ocurrió un error: " + e.getMessage());
            apiResponse.setFecha("Sin resultados.");
        }
        return apiResponse;
    }
}

