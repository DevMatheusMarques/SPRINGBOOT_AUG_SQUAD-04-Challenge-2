package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.InvalidEntryCancelException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.InvalidExitCancelException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.UnauthorizedCancelAccessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CancelServiceTest {

    @Test
    public void testAllowEntry_PassengerCar_ValidCancel() {
        assertTrue(CancelService.allowEntry(TypeVehicle.PASSENGER_CAR, 1));
        assertTrue(CancelService.allowEntry(TypeVehicle.PASSENGER_CAR, 2));
        assertTrue(CancelService.allowEntry(TypeVehicle.PASSENGER_CAR, 3));
        assertTrue(CancelService.allowEntry(TypeVehicle.PASSENGER_CAR, 4));
        assertTrue(CancelService.allowEntry(TypeVehicle.PASSENGER_CAR, 5));
    }

    @Test
    public void testAllowEntry_PassengerCar_InvalidCancel() {
        Exception exception = assertThrows(InvalidEntryCancelException.class, () -> {
            CancelService.allowEntry(TypeVehicle.PASSENGER_CAR, 6);
        });
        assertEquals("Vehicles cannot enter through this cancel. Please use cancels 1, 2, 3, 4, or 5.", exception.getMessage());
    }

    @Test
    public void testAllowEntry_Motorcycle_ValidCancel() {
        assertTrue(CancelService.allowEntry(TypeVehicle.MOTORCYCLE, 5));
    }

    @Test
    public void testAllowEntry_Motorcycle_InvalidCancel() {
        Exception exception = assertThrows(UnauthorizedCancelAccessException.class, () -> {
            CancelService.allowEntry(TypeVehicle.MOTORCYCLE, 1);
        });
        assertEquals("This type of vehicle cannot enter through this cancel. Please use cancel 5.", exception.getMessage());
    }

    @Test
    public void testAllowEntry_PublicService_ValidCancel() {
        assertTrue(CancelService.allowEntry(TypeVehicle.PUBLIC_SERVICE, 1));
        assertTrue(CancelService.allowEntry(TypeVehicle.PUBLIC_SERVICE, 2));
        assertTrue(CancelService.allowEntry(TypeVehicle.PUBLIC_SERVICE, 3));
        assertTrue(CancelService.allowEntry(TypeVehicle.PUBLIC_SERVICE, 4));
        assertTrue(CancelService.allowEntry(TypeVehicle.PUBLIC_SERVICE, 5));
    }

    @Test
    public void testAllowEntry_PublicService_InvalidCancel() {
        Exception exception = assertThrows(InvalidEntryCancelException.class, () -> {
            CancelService.allowEntry(TypeVehicle.PUBLIC_SERVICE, 6);
        });
        assertEquals("Vehicles cannot enter through this cancel. Please use cancels 1, 2, 3, 4, or 5.", exception.getMessage());
    }


    @Test
    public void testAllowEntry_DeliveryTruck_ValidCancel() {
        assertTrue(CancelService.allowEntry(TypeVehicle.DELIVERY_TRUCK, 1));
    }

    @Test
    public void testAllowEntry_DeliveryTruck_InvalidCancel() {
        Exception exception = assertThrows(UnauthorizedCancelAccessException.class, () -> {
            CancelService.allowEntry(TypeVehicle.DELIVERY_TRUCK, 2);
        });
        assertEquals("This type of vehicle cannot enter through this cancel. Please use cancel 1.", exception.getMessage());
    }

    @Test
    public void testAllowExit_PassengerCar_ValidCancel() {
        assertTrue(CancelService.allowExit(TypeVehicle.PASSENGER_CAR, 6));
        assertTrue(CancelService.allowExit(TypeVehicle.PASSENGER_CAR, 7));
        assertTrue(CancelService.allowExit(TypeVehicle.PASSENGER_CAR, 8));
        assertTrue(CancelService.allowExit(TypeVehicle.PASSENGER_CAR, 9));
        assertTrue(CancelService.allowExit(TypeVehicle.PASSENGER_CAR, 10));
    }

    @Test
    public void testAllowExit_PassengerCar_InvalidCancel() {
        Exception exception = assertThrows(InvalidExitCancelException.class, () -> {
            CancelService.allowExit(TypeVehicle.PASSENGER_CAR, 5);
        });
        assertEquals("Vehicles cannot exit through this cancel. Please use cancels 6, 7, 8, 9, or 10.", exception.getMessage());
    }

    @Test
    public void testAllowExit_Motorcycle_ValidCancel() {
        assertTrue(CancelService.allowExit(TypeVehicle.MOTORCYCLE, 10));
    }

    @Test
    public void testAllowExit_Motorcycle_InvalidCancel() {
        Exception exception = assertThrows(UnauthorizedCancelAccessException.class, () -> {
            CancelService.allowExit(TypeVehicle.MOTORCYCLE, 6);
        });
        assertEquals("This type of vehicle cannot exit through this cancel. Please use cancel 10.", exception.getMessage());
    }

    @Test
    public void testAllowExit_PublicService_ValidCancel() {
        assertTrue(CancelService.allowExit(TypeVehicle.PUBLIC_SERVICE, 6));
        assertTrue(CancelService.allowExit(TypeVehicle.PUBLIC_SERVICE, 7));
        assertTrue(CancelService.allowExit(TypeVehicle.PUBLIC_SERVICE, 8));
        assertTrue(CancelService.allowExit(TypeVehicle.PUBLIC_SERVICE, 9));
        assertTrue(CancelService.allowExit(TypeVehicle.PUBLIC_SERVICE, 10));
    }

    @Test
    public void testAllowExit_PublicService_InvalidCancel() {
        Exception exception = assertThrows(InvalidExitCancelException.class, () -> {
            CancelService.allowExit(TypeVehicle.PUBLIC_SERVICE, 5);
        });
        assertEquals("Vehicles cannot exit through this cancel. Please use cancels 6, 7, 8, 9, or 10.", exception.getMessage());
    }

    @Test
    public void testAllowExit_DeliveryTruck_ValidCancel() {
        assertTrue(CancelService.allowExit(TypeVehicle.DELIVERY_TRUCK, 6));
    }

    @Test
    public void testAllowExit_DeliveryTruck_InvalidCancel() {
        Exception exception = assertThrows(InvalidExitCancelException.class, () -> {
            CancelService.allowExit(TypeVehicle.DELIVERY_TRUCK, 5);
        });
        assertEquals("Vehicles cannot exit through this cancel. Please use cancels 6, 7, 8, 9, or 10.", exception.getMessage());
    }
}
