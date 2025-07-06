package com.mycompany.primesecure;
import java.util.ArrayList;
import java.util.Collection;

public class PrimesList extends ArrayList<Integer> {

    public boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;

        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return true;
    }

    @Override
    public boolean add(Integer numero) {
        if (!isPrime(numero)) {
            throw new IllegalArgumentException("❌ El número " + numero + " no es primo.");
        }
        if(this.contains(numero)){
            System.out.println("El numero "+numero+" ya esta en la lista");
            return false;
        }
        return super.add(numero);
    }

    @Override
    public boolean remove(Object numero) {
        if(numero instanceof Integer integer){
            if(!isPrime(integer)){
                throw new IllegalArgumentException("Solo puedes eliminar numero primos");
            }
        }
        return super.remove(numero);
    }

    public int getPrimesCount() {
        return this.size();
    }
    public Collection<Integer> getPrimes(){
        return this;
    }
}
