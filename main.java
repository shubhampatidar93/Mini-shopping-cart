import java.util.*;

// Product class using Encapsulation
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}

// Abstract Discount class for Polymorphism
abstract class Discount {
    public abstract double applyDiscount(double amount, List<Product> products);
}

// Festive Discount - 10% off
class FestiveDiscount extends Discount {
    @Override
    public double applyDiscount(double amount, List<Product> products) {
        return amount * 0.90; // 10% off
    }
}

// Bulk Discount - 20% off if quantity > 5 for any item
class BulkDiscount extends Discount {
    @Override
    public double applyDiscount(double amount, List<Product> products) {
        for (Product p : products) {
            if (p.getQuantity() > 5) {
                return amount * 0.80; // 20% off
            }
        }
        return amount;
    }
}

// Payment Interface
interface Payment {
    void pay(double amount);
}

// CashPayment implementation (could be extended for Card, UPI, etc.)
class CashPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.printf("Total Amount Payable: ₹%.2f\n", amount);
    }
}

// Main Class
public class ShoppingCartSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Product> cart = new ArrayList<>();

        // Read number of products
        int n = Integer.parseInt(scanner.nextLine().trim());

        // Read product details
        for (int i = 0; i < n; i++) {
            String[] input = scanner.nextLine().trim().split("\\s+");
            String name = input[0];
            double price = Double.parseDouble(input[1]);
            int quantity = Integer.parseInt(input[2]);

            cart.add(new Product(name, price, quantity));
        }

        // Read discount type
        String discountType = scanner.nextLine().trim().toLowerCase();

        // Calculate total
        double total = 0.0;
        for (Product p : cart) {
            total += p.getTotalPrice();
        }

        // Apply discount
        Discount discount;
        switch (discountType) {
            case "festive":
                discount = new FestiveDiscount();
                break;
            case "bulk":
                discount = new BulkDiscount();
                break;
            default:
                discount = new Discount() {
                    @Override
                    public double applyDiscount(double amount, List<Product> products) {
                        return amount; // No discount
                    }
                };
        }

        double discountedTotal = discount.applyDiscount(total, cart);

        // Print product details
        for (Product p : cart) {
            System.out.printf("Product: %s, Price: %.2f, Quantity: %d\n",
                    p.getName(), p.getPrice(), p.getQuantity());
        }

        // Payment
        Payment payment = new CashPayment();
        payment.pay(discountedTotal);

        scanner.close();
    }
}
