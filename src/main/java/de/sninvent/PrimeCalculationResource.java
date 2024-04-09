package de.sninvent;

import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/prime-calculation")
public class PrimeCalculationResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello(@QueryParam("startNumber") Long startNumber, @QueryParam("count") int count) {
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
