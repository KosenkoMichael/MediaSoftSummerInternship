import Rooms.EconomyRoom;
import Rooms.LuxRoom;
import Rooms.Room;
import Rooms.StandardRoom;
import Rooms.UltraLuxRoom;
import Services.AdvancedLuxRoomService;
import Services.BasicRoomService;
import Services.RoomAlreadyReservedException;

public class Main {
    public static void main(String[] args) {
        // ====== Room creation ======
        Room economy = new EconomyRoom(101);
        Room standard = new StandardRoom(102);
        LuxRoom lux = new LuxRoom(103);
        UltraLuxRoom ultra = new UltraLuxRoom(104);

        System.out.println("=== Rooms ===");
        System.out.println(economy.toString());
        System.out.println(standard.toString());
        System.out.println(lux.toString());
        System.out.println(ultra.toString());

        // ====== BasicRoomService ======
        System.out.println("\n=== BasicRoomService ===");
        BasicRoomService<Room> roomService = new BasicRoomService<>();

        roomService.clean(economy);
        roomService.reserve(economy);

        try {
            roomService.reserve(economy); // twice reserve try
        } catch (RoomAlreadyReservedException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        roomService.free(economy);

        // ====== AdvancedLuxRoomService ======
        System.out.println("\n=== AdvancedLuxRoomService ===");
        AdvancedLuxRoomService<LuxRoom> luxService = new AdvancedLuxRoomService<>();

        luxService.clean(lux);
        luxService.reserve(lux);
        luxService.foodDelivery(lux);
        luxService.free(lux);

        System.out.println("\n=== UltraLuxRoom by LuxRoomService ===");
        luxService.clean(ultra);
        luxService.reserve(ultra);
        luxService.foodDelivery(ultra);
        luxService.free(ultra);

        // ====== Проверка ограничений компиляции ======
        // Room room = new Room(999, 2, 100); // Ошибка компиляции
        // ProRoom pro = new ProRoom(888, 3, 150); // Ошибка компиляции
        // luxService.foodDelivery((LuxRoom) economy); // Ошибка компиляции/классовая ошибка
    }
}
