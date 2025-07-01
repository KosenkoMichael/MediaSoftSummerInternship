package Services;
import Rooms.Room;

public interface RoomService<T extends Room> {
    void clean(T room);
    void reserve(T room);
    void free(T room);
}
