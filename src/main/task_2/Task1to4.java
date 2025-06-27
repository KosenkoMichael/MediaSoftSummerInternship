import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Task1to4 {
    public void show() {
        /*
         * 
         * 1 Массивы (Работа с парком машин)
         * 
         */
        System.out.println("\nTASK 1\n");
        int[] years = new Random().ints(50, 2000, 2026).toArray();
        int currentYear = LocalDate.now().getYear();
        System.out.print("Year > 2015 : ");
        String result = Arrays.stream(years)
                .filter(y -> y > 2015)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        System.out.println(result);

        System.out.print("Average of array : ");
        double avg = Arrays.stream(years)
                .map(y -> currentYear - y)
                .average()
                .orElse(0);
        System.out.println(avg);
        /*
         * 
         * 2 Коллекции (Управление моделями)
         * 
         */
        System.out.println("\nTASK 2\n");
        List<String> carModels = Arrays.asList(
                "Toyota Camry",
                "BMW X5",
                "Tesla Model 3",
                "Honda Accord",
                "Audi A4",
                "Mercedes-Benz C",
                "Ford Focus",
                "Hyundai Sonata",
                "Tesla Model 3",
                "Toyota Camry");
        System.out.println("---Origin list---");
        carModels.forEach(System.out::println);
        System.out.println("---Distincted reverse sorted set---");

        List<String> processed = carModels.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        processed.forEach(System.out::println);

        Set<String> car_set = new HashSet<>(processed);

        System.out.println("---Tesla to ELECTRO_CAR---");
        processed = processed.stream()
                .map(car -> car.contains("Tesla") ? "ELECTRO_CAR" : car)
                .collect(Collectors.toList());
        processed.forEach(System.out::println);
        /*
         * 
         * 3 equals/hashCode (Сравнение автомобилей)
         * 
         */
        System.out.println("\nTASK 3\n");

        Car car_1 = new Car(11111111, "Toyota Camry", "Japan", 2010, 30000.0, 50000, Car.Type.SUV);
        Car car_2 = new Car(22222222, "BMW X5", "German", 2015, 40000.0, 100000, Car.Type.SEDAN);
        Car car_3 = new Car(33333333, "Tesla Model 3", "USA", 2018, 50000.0, 91000, Car.Type.ELECTRIC);
        Car car_4 = new Car(11111111, "Honda Accord", "Japan", 2019, 60000.0, 43000, Car.Type.SEDAN);
        Car car_5 = new Car(11111111, "Hyundai Sonata", "South Corea", 2008, 70000.0, 12000, Car.Type.SUV);

        Set<Car> carSet = new HashSet<>();
        carSet.add(car_1);
        carSet.add(car_2);
        carSet.add(car_3);
        carSet.add(car_4);
        carSet.add(car_5);

        System.out.println("---5 cars in set (2 of them are duplicates)---");
        for (Car car : carSet) {
            System.out.println(car.toString());
        }

        List<Car> carList = new ArrayList<>();
        carList.add(car_1);
        carList.add(car_2);
        carList.add(car_3);
        carList.add(car_4);
        carList.add(car_5);

        Collections.sort(carList);

        System.out.println("---Sorted by year lists---");
        for (Car car : carList) {
            System.out.println(car.toString());
        }

        /*
         * 
         * 4 Stream API (Анализ автопарка)
         * 
         */
        System.out.println("\nTASK 4\n");

        List<Car> lowMileage = carList.stream()
                .filter(car -> car.getMileage() < 50_000)
                .collect(Collectors.toList());

        System.out.println("cars with mileage < 50_000 km:");
        lowMileage.forEach(System.out::println);

        System.out.println("\nTop 3 most expensive:");
        carList.stream()
                .sorted(Comparator.comparing(Car::getCost).reversed())
                .limit(3)
                .forEach(System.out::println);

        double avgMileage = carList.stream()
                .mapToDouble(Car::getMileage)
                .average()
                .orElse(0);
        System.out.println("\nAverage mileage: " + avgMileage + " km");

        Map<String, List<Car>> carsByManufacturer = carList.stream()
                .collect(Collectors.groupingBy(Car::getManufacturer));

        System.out.println("\nCars by manufacturers:");
        for (Map.Entry<String, List<Car>> entry : carsByManufacturer.entrySet()) {
            System.out.println(entry.getKey() + ":");
            entry.getValue().forEach(System.out::println);
        }
    }
}
