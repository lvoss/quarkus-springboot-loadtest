package de.sninvent.springnativebenchmark.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPrimes {

    @GetMapping("/getPrimes")
    public String getPrimesAsString(@RequestParam(name = "startNumber", defaultValue = "10000000000000") long startNumber, @RequestParam(name = "count", defaultValue = "250") long count) {
        long startTime = System.currentTimeMillis();
        List<Long> primeNumbers = new ArrayList<>();
        Long currentNumber = startNumber;
        
        for (int i = 0; i < count;) {
          if (isPrime(currentNumber)) {
            primeNumbers.add(currentNumber);
            i ++;
          }
          currentNumber ++;
        }
        long endTime = System.currentTimeMillis();
        String timeTaken = "time in millis: " + (endTime - startTime);
        String params = "startNumber: " + startNumber + ", count: " + count;
        System.out.println(timeTaken + ", " + params);
        return timeTaken + " " +primeNumbers.toString();
    }

    public static boolean isPrime(Long n) {
        if (n == 1)
          return false;
    
        for (Long i = 2L; i * i <= n; i++) {
          if (n % i == 0)
            return false;
        }
    
        return true;
      }
    
}
