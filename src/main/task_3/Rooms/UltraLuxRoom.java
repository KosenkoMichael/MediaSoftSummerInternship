package Rooms;

public class UltraLuxRoom extends LuxRoom {
    public UltraLuxRoom(int number) {
        super(number);
        this.pricePerNight = Prices.ULTRA_LUX.getPrice();
    }
}
