// problem 7
import java.util.*;

public class the10001prime {
    
    public static void main(String[] args) {
        
        int count = 1;
        long num = 2;
        while(count<10001){
            if(isPrime(num)) {count++;
                System.out.println(count+":"+num); 
            }
            num++;
        }
    }
    public static boolean isPrime(long n){
        for(long i=2;i<
        Math.sqrt(n);i++){
            if(n % i ==0)
                return true;
        }
        return false;
    }
    
    
    
}