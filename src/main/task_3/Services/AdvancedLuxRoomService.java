package Services;
import Rooms.LuxRoom;

public class AdvancedLuxRoomService<T extends LuxRoom> implements LuxRoomService<T> {
    @Override
    public void clean(T room) {
        System.out.println("Lux cleaning for " + room);
    }

    @Override
    public void reserve(T room) {
        if (room.isReserved()) {
            throw new RoomAlreadyReservedException("Lux room " + room.getNumber() + " is already reserved.");
        }
        room.setReserved(true);
        System.out.println("Reserved lux room " + room);
    }

    @Override
    public void free(T room) {
        room.setReserved(false);
        System.out.println("Freed lux room " + room);
    }

    @Override
    public void foodDelivery(T room) {
        System.out.println("Food delivered to " + room);
    }
}
