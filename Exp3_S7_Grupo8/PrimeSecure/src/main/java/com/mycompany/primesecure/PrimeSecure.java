package com.mycompany.primesecure;

public class PrimeSecure {
    public static void main(String[] args) {
        System.out.println("🛡️ PrimeSecure - Verificación con hilos\n");

        PrimesList lista = new PrimesList();
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
                e.printStackTrace();
            }
        }

        System.out.println("\n🔢 Primos encontrados: " + lista);
        System.out.println("🧮 Total: " + lista.getPrimesCount());
    }
}