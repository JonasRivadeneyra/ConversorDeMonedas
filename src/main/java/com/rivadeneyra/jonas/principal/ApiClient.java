package com.rivadeneyra.jonas.principal;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiClient {
    // Declaramos la API_KEY pero no la inicializamos directamente
    private static final String API_KEY;
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    // Bloque estático para cargar la API_KEY desde un archivo externo
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            API_KEY = properties.getProperty("API_KEY");
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la API Key desde el archivo config.properties", e);
        }
    }

    //metoodoo llmar api
    public static void convertir(String monedaBase, String monedaDestino) {
        Scanner scanner = new Scanner(System.in);


        try {
            System.out.println("Ingrese la cantidad que desea convertir de " + monedaBase + " a "
                    + monedaDestino + ": ");
            double cantidad = scanner.nextDouble();

            // Construcción de la URL completa
            String url = API_URL + API_KEY + "/latest/" + monedaBase;

            // Crear cliente y solicitud HTTP
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Enviar solicitud y recibir respuesta
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());


            // Parsear JSON
            JSONObject json = new JSONObject(response.body());

            if (json.getString("result").equals("success")) {
                double tasaConversion = json.getJSONObject("conversion_rates").getDouble(monedaDestino);
                double resultado = cantidad * tasaConversion;
                System.out.println("*****************************************");
                System.out.printf("%.2f %s corresponden al valor final de ==>> %.2f %s%n", cantidad,
                        monedaBase, resultado, monedaDestino);
                System.out.println(" ");
                System.out.printf("Sepa que La tasa de cambio actual es: 1 %s ==>> %.2f %s%n",
                        monedaBase, tasaConversion, monedaDestino);
                System.out.println(" ");
                System.out.println("*****************************************");
            } else {
                System.out.println("Error al obtener datos de la API: " + json.getString("error-type"));
            }


        //catch para posibles errores  por si el usuario no ingresa un número válido
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido para la cantidad.");
        } catch (HttpTimeoutException e) {
            System.out.println("Error: La solicitud tardó demasiado en responder.");
        } catch (IOException e) {
            System.out.println("Error de red o al acceder a la URL: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Error: La solicitud fue interrumpida.");
            Thread.currentThread().interrupt(); //Marca el hilo actual como interrumpido (no lo detiene)
        } catch (JSONException e) {
            System.out.println("Error al procesar la respuesta JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }





    }
}