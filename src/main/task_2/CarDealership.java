import java.util.*;
import java.util.stream.*;

public class CarDealership {
    private Set<Car> cars = new HashSet<>();

    public boolean addCar(Car car) {
        return cars.add(car);
    }

    public List<Car> findByManufacturer(String manufacturer) {
        return cars.stream()
                .filter(car -> car.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());
    }

    public double averagePriceByType(Car.Type type) {
        return cars.stream()
                .filter(car -> car.getType() == type)
                .mapToDouble(Car::getCost)
                .average()
                .orElse(0);
    }

    public List<Car> sortedByYear() {
        return cars.stream()
                .sorted(Comparator.comparing(Car::getYear).reversed())
                .collect(Collectors.toList());
    }

    public Map<Car.Type, Long> countByType() {
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getType, Collectors.counting()));
    }

    public Optional<Car> getOldestCar() {
        return cars.stream().min(Comparator.comparing(Car::getYear));
    }

    public Optional<Car> getNewestCar() {
        return cars.stream().max(Comparator.comparing(Car::getYear));
    }

    public Set<Car> getAllCars() {
        return cars;
    }
}
