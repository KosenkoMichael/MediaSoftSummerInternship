package Services;

public class RoomAlreadyReservedException extends RuntimeException {
    public RoomAlreadyReservedException() {
        super();
    }

    public RoomAlreadyReservedException(String message) {
        super(message);
    }
}
