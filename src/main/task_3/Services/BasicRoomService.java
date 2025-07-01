package Services;
import Rooms.Room;

public class BasicRoomService<T extends Room> implements RoomService<T> {
    @Override
    public void clean(T room) {
        System.out.println("Cleaning " + room);
    }

    @Override
    public void reserve(T room) {
        if (room.isReserved()) {
            throw new RoomAlreadyReservedException("Room " + room.getNumber() + " is already reserved.");
        }
        room.setReserved(true);
        System.out.println("Reserved " + room);
    }

    @Override
    public void free(T room) {
        room.setReserved(false);
        System.out.println("Freed " + room);
    }
}
