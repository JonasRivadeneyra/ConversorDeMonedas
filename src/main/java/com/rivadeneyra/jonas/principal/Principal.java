package com.rivadeneyra.jonas.principal;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcion = 0, contar = 0, eleccion ;

        do {
            if (contar == 0) {
                System.out.println("""
                        ***************************************************
                        
                        ***** SEA BIENVENIDO/A AL CONVERSOR DE MONEDAS ****
                        *********** ELIJA UNA OPCIÓN VÁLIDA: *************
                        
                        *******************************************
                        1) CONVERTIR DÓLAR --->  ( PESO ARGENTINO )
                        2) CONVERTIR PESO ARGENTINO ---> ( DÓLAR )
                        3) CONVERTIR DÓLAR ---> ( REAL BRASILEÑO )
                        4) CONVERTIR REAL BRASILEÑO ---> ( DÓLAR )
                        5) CONVERTIR DÓLAR ---> ( PESO COLOMBIANO )
                        6) CONVERTIR PESO COLOMBIANO ---> ( DÓLAR )
                        7) CONVERTIR DÓLAR ---> ( SOL PERUANO )
                        8) ** QUIERO SALIR **
                        *******************************************
                        """);
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1 -> ApiClient.convertir("USD", "ARS");
                    case 2 -> ApiClient.convertir("ARS", "USD");
                    case 3 -> ApiClient.convertir("USD", "BRL");
                    case 4 -> ApiClient.convertir("BRL", "USD");
                    case 5 -> ApiClient.convertir("USD", "COP");
                    case 6 -> ApiClient.convertir("COP", "USD");
                    case 7 -> ApiClient.convertir("USD", "PEN");
                    case 8 -> System.out.println("***+Gracias por usar el conversor. ¡Hasta pronto!*******");
                    default -> System.out.println("Error Opción no válida. Intente de nuevo.");
                }
                contar++;

            } else {
                System.out.println("""
                        1) ** SEGUIR CONVIRTIENDO **
                        2) ** QUIERO SALIR **
                        """);
                eleccion = scanner.nextInt();
                if (eleccion > 0 && eleccion < 3) {
                    if (eleccion == 1) {
                        contar = 0;
                    } else {
                        System.out.println("***+Gracias por usar el conversor. ¡Hasta pronto!*******");
                        break;
                    }
                } else {
                    System.out.println("*********************");
                    System.out.println("ERROR : ENTRADA INVALIDA");
                    System.out.println("*********************");
                }
            }
        } while (opcion != 8 );

    }
}
