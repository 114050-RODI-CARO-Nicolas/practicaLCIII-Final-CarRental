package com.example.rentacar.services.utils;

import com.example.rentacar.domain.Rent;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProcessUtils {

    public static boolean checkIfCurrentlyRented(List<Rent> rents) {

        if ( rents.size()==0) {
            return false;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Rent rent:rents) {
            if(currentDateTime.isAfter(rent.getStartRent()) && currentDateTime.isBefore(rent.getEndRent()) )
            {   return true;

            }
        }
        return false;

    };




    public static boolean CheckIfRequestedRentPeriodCollidesWithExistingRents(List<Rent> rents, LocalDateTime requestedStartDate, LocalDateTime requestedEndDate)
    {
        if ( rents.size()==0) {
            return false;
        };
        List<Map<String, LocalDateTime>> existingDatePeriods = new ArrayList<>();
        for(Rent existingRent:rents)
        {
            Map<String, LocalDateTime> dateTimeMap = new HashMap<>();
            dateTimeMap.put("startDate", existingRent.getStartRent());
            dateTimeMap.put("endDate", existingRent.getEndRent());
            existingDatePeriods.add(dateTimeMap);


        }

        for(Map<String, LocalDateTime> timePeriod: existingDatePeriods)
        {
            LocalDateTime beginningDate = timePeriod.get("startDate");
            LocalDateTime endDate = timePeriod.get("endDate");

            if( !(requestedEndDate.isBefore(beginningDate) || requestedStartDate.isAfter((endDate)))     )
            {
                return true; //Collision within the same map
            }
        }

        return false;

    }

    public static boolean checkIfRentedAtParticularDate(List<Rent> rents, LocalDateTime intendedStartDate )
    {
        /*
            La fecha que paso por parametro se evalua si esta entre medio de las fechas de inicio y de fin
            de cada alquiler.

            Pero..., como saber si la fecha de fin que se va a setear no colisiona con la fecha de inicio del alquiler siguiente?
            Necesito escribir otro mas complejo que contenga todas esos requerimientos.

         */

        if ( rents.size()==0) {
            return false;
        };

        for (Rent rent:rents) {
            if(intendedStartDate.isAfter(rent.getStartRent()) && intendedStartDate.isBefore(rent.getEndRent()) )
            {   return true;

            }
        }
        return false;



    }

}


