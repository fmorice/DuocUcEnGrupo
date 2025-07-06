package com.mycompany.primesecure;

public class PrimeCheckerThread extends Thread {
    private final int numero;
    private final PrimesList lista;

    public PrimeCheckerThread(int numero, PrimesList lista) {
        this.numero = numero;
        this.lista = lista;
    }

    @Override
    public void run() {
        if (lista.isPrime(numero)) {
            synchronized (lista) {
                lista.add(numero);
                System.out.println("âœ… " + numero + " es primo (hilo: " + getName() + ")");
            }
        } else {
            System.out.println("âŒ " + numero + " no es primo (hilo: " + getName() + ")");
        }
    }
}
