package com.example.rentacar.services.utils;

import com.example.rentacar.domain.Rent;

import java.time.LocalDateTime;
import java.util.List;

public final class ProcessUtils {

    public static boolean checkIfCurrentlyRented(List<Rent> rents) {

        if ( rents.size()==0) {
            return false;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Rent rent:rents) {
            if(currentDateTime.isAfter(rent.getStartRent())&& currentDateTime.isBefore(rent.getEndRent()) )
            {   return true;

            }
        }
        return false;

    };

    public static boolean checkIfRentedAtParticularDate(List<Rent> rents, LocalDateTime intendedStartDate )
    {
        if ( rents.size()==0) {
            return false;
        };

        for (Rent rent:rents) {
            if(intendedStartDate.isAfter(rent.getStartRent())&& intendedStartDate.isBefore(rent.getEndRent()) )
            {   return true;

            }
        }
        return false;



    }

}


