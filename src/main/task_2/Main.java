import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        CarDealership dealership = new CarDealership();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                clearConsole();
                System.out.println("\n--- Menu ---");
                System.out.println("1. Add car");
                System.out.println("2. Find by manufacturer");
                System.out.println("3. Average by type");
                System.out.println("4. Car list from older to newer");
                System.out.println("5. Statistic");
                System.out.println("6. Show tasks 1,2,3,4");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        try {
                            System.out.print("VIN: ");
                            int vin = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print("Model: ");
                            String model = scanner.nextLine();

                            System.out.print("Manufacturer: ");
                            String manufacturer = scanner.nextLine();

                            System.out.print("Year: ");
                            int year = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print("Mileage: ");
                            double mileage = Double.parseDouble(scanner.nextLine().trim());

                            System.out.print("Price: ");
                            double cost = Double.parseDouble(scanner.nextLine().trim());

                            System.out.print("Type (SEDAN, SUV, ELECTRIC): ");
                            String typeInput = scanner.nextLine().trim().toUpperCase();

                            Car.Type type = Car.Type.valueOf(typeInput);

                            boolean added = dealership
                                    .addCar(new Car(vin, model, manufacturer, year, mileage, cost, type));
                            System.out.println(added ? "Car added." : "Car with such VIN already exists");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: incorrect type or incorrect input");
                        }
                        System.out.println("Press Enter to continue...");
                        new Scanner(System.in).nextLine();
                        break;

                    case 2:
                        System.out.print("Input manufacturer: ");
                        String brand = scanner.nextLine();
                        dealership.findByManufacturer(brand)
                                .forEach(System.out::println);
                        System.out.println("Press Enter to continue...");
                        new Scanner(System.in).nextLine();
                        break;

                    case 3:
                        System.out.print("Input type: ");
                        try {
                            Car.Type typeAvg = Car.Type.valueOf(scanner.nextLine().trim().toUpperCase());
                            double avg = dealership.averagePriceByType(typeAvg);
                            System.out.println("Average cost: $" + avg);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: incorrect type");
                        }
                        System.out.println("Press Enter to continue...");
                        new Scanner(System.in).nextLine();
                        break;

                    case 4:
                        dealership.sortedByYear()
                                .forEach(System.out::println);
                        System.out.println("Press Enter to continue...");
                        new Scanner(System.in).nextLine();
                        break;

                    case 5:
                        System.out.println("Count cars by typr:");
                        dealership.countByType()
                                .forEach((t, c) -> System.out.println(t + ": " + c));

                        System.out.println("Most new car:");
                        dealership.getNewestCar().ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("No cars in list."));

                        System.out.println("Most old car:");
                        dealership.getOldestCar().ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("No car in list."));
                        System.out.println("Press Enter to continue...");
                        new Scanner(System.in).nextLine();
                        break;
                    case 6:
                        Task1to4 task = new Task1to4();
                        task.show();
                        System.out.println("Press Enter to continue...");
                        new Scanner(System.in).nextLine();
                        break;
                    case 0:
                        System.out.println("Exit...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Incorrect choice. Try again");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error. Input correct number");
            }
        }
    }
}
