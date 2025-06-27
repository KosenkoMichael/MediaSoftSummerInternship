public class Car implements Comparable<Car> {
    public enum Type {
        SEDAN, SUV, ELECTRIC
    };

    private int _VIN;
    private String _model;
    private String _manufacturer;
    private int _year;
    private double _mileage;
    private double _cost;
    private Type _type;

    public Car(int VIN, String model, String manufacturer, int year, double mileage, double cost, Type type) {
        _VIN = VIN;
        _model = model;
        _manufacturer = manufacturer;
        _year = year;
        _mileage = mileage;
        _cost = cost;
        _type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Car car = (Car) obj;
        return _VIN == car._VIN;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(_VIN);
    }

    @Override
    public String toString() {
        return _VIN + " | " + _manufacturer + " " + _model + " (" + _year + "), " +
                _mileage + " km, $" + _cost + " " + _type;
    }

    @Override
    public int compareTo(Car other) {
        return Integer.compare(other._year, _year);
    }

    public int getNIN() {
        return _VIN;
    }

    public String getModel() {
        return _model;
    }

    public String getManufacturer() {
        return _manufacturer;
    }

    public int getYear() {
        return _year;
    }

    public double getMileage() {
        return _mileage;
    }

    public double getCost() {
        return _cost;
    }

    public Type getType() {
        return _type;
    }
}
