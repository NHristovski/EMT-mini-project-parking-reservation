package nikola.hristovski.parking.domain.exception;

public class FailedToReserveParkingSpotException extends Exception{

    public FailedToReserveParkingSpotException(String message){
        super(message);
    }

    public FailedToReserveParkingSpotException(String message, Throwable throwable){
        super(message,throwable);
    }
}
