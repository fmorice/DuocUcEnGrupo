package com.mycompany.primesecure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PrimeSecure {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrimesList lista = new PrimesList();
        System.out.println("🛡️ PrimeSecure - Sistema de mensajeria segura\n");

        while(true){
            System.out.println("::::::::::::: MENU PRINCIPAL :::::::::::::");
            System.out.println("1. Verificar numeros impares almacenados");
            System.out.println("2. Ingresar numero de forma manual");
            System.out.println("3. Mostrar numeros primos almacenados");
            System.out.println("4. Eliminar un numero primo");
            System.out.println("5. Salir");
            System.out.println("Selecciona una opcion (1-5)");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion){
                    case 1-> verificarNumeros(lista);
                    case 2 -> ingresarNumeros(scanner, lista);
                    case 3 -> mostrarNumerosPrimos (lista);
                    case 4 -> eliminarNumero (scanner, lista);
                    case 5 -> { 
                        System.out.println("Saliendo del sistema...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Opcion invalida. Intenta nuevamente ingresando una opcion del 1 al 5");
                }
            }catch (NumberFormatException e){
                System.out.println("Error. Debes ingresar un numero valido");
            }
        }
    }
    private static void verificarNumeros (PrimesList lista){
        System.out.println("\nLos numeros almacenados son: \n");
        int[] numeros = {2, 3, 4, 5, 15, 17, 20, 23, 24, 29};
        
        Thread[] hilos = new Thread[numeros.length];
        for (int i = 0; i < numeros.length; i++) {
            hilos[i] = new PrimeCheckerThread(numeros[i], lista);
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error: Hilo interrumpido");
                Thread.currentThread().interrupt();
            }
        }

        List<Integer> primos = new ArrayList<>(lista.getPrimes());
        Collections.sort(primos);
        
        System.out.println("\n🔢 Primos encontrados: " + primos);
        System.out.println("🧮 Total: " + lista.getPrimesCount());
    }
    private static void ingresarNumeros (Scanner scanner, PrimesList lista){
        System.out.println("Ingresa los numeros separados por espacios");
        String entrada = scanner.nextLine();
        String[] numerosNuevos = entrada.split(" ");
        
        Thread[] hilos = new Thread[numerosNuevos.length];
        
        for (int i = 0; i < numerosNuevos.length; i++) {
            try{
                int num = Integer.parseInt(numerosNuevos[i]);
                hilos[i] = new PrimeCheckerThread(num, lista);
                hilos[i].start();
            }catch(NumberFormatException e){
                System.out.println("' "+numerosNuevos[i]+" '"+" no es un numero valido");
            }
        }
        for(Thread hilo : hilos){
            try{
                if(hilo != null) hilo.join();
            }catch(InterruptedException e){
                System.out.println("Error: Hilo interrumpido");
                Thread.currentThread().interrupt();
            }
        }
    }    
    private static void mostrarNumerosPrimos(PrimesList lista){    
        System.out.println("\nLista de numeros primos almacenados:\n");
        if (lista.isEmpty()) {
            System.out.println("La lista esta vacia");
        }else{
            List<Integer> primosOrdenados = new ArrayList<>(lista.getPrimes());
            Collections.sort(primosOrdenados);
            System.out.println(primosOrdenados);
            System.out.println("Total: "+lista.getPrimesCount());
        }
    }
    private static void eliminarNumero (Scanner scanner, PrimesList lista){
        System.out.println("\nIngresa el numero primo que deseas eliminar: ");
        try{
            int num = Integer.parseInt(scanner.nextLine());
            if(lista.remove((Integer)num)){
                System.out.println("Numero "+num+" eliminado correctamente");
            }else{
                System.out.println("El numero "+num+" no esta en la lista o no es un numero primo");
            }
        }catch(NumberFormatException e){
            System.out.println("Error: Debes ingresar un numeor valido");
        }
    }
}
