package com.isacgrive;
import java.io.*;
import java.util.*;

public class FestivalBokaren {
    private static final String FILE_NAME = "booked_tickets.csv";
    private static final String[] TICKET_TYPES = {"standard", "standardPlus", "VIP", "Fuktioner"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Ticket> bookedTickets = loadBookedTickets();

        boolean stop = false;
        while (!stop) {
            System.out.println("Menu:");
            System.out.println("1. Book ticket");
            System.out.println("2. View booked tickets");
            System.out.println("3. View ticket count");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    bookTicket(scanner, bookedTickets);
                    break;
                case 2:
                    showBookedTickets(bookedTickets);
                    break;
                case 3:
                    showTicketCount(bookedTickets);
                    break;
                case 4:
                    stop = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        saveBookedTickets(bookedTickets);
        System.out.println("Program terminated.");
    }

     // book ticket method 
    private static void bookTicket(Scanner scanner, List<Ticket> bookedTickets) {
        System.out.println("Ticket Types:");
        for (int i = 0; i < TICKET_TYPES.length; i++) {
            System.out.println((i + 1) + ". " + TICKET_TYPES[i]);
        }

        System.out.print("Enter ticket type number: ");
        int ticketTypeNumber = scanner.nextInt();
        scanner.nextLine(); 

        if (ticketTypeNumber < 1 || ticketTypeNumber > TICKET_TYPES.length) {
            System.out.println("Invalid ticket type number.");
            return;
        }

        String ticketType = TICKET_TYPES[ticketTypeNumber - 1];

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter birth year: ");
        int birthYear = scanner.nextInt();
        scanner.nextLine(); 

        Ticket ticket = new Ticket(ticketType, firstName, lastName, birthYear);
        bookedTickets.add(ticket);

        System.out.println("Ticket booked successfully.");
    }

    // method to display the list of booked tickets
    private static void showBookedTickets(List<Ticket> bookedTickets) {
        if (bookedTickets.isEmpty()) {
            System.out.println("No tickets have been booked yet.");
            return;
        }

        System.out.println("Booked Tickets:");
        for (Ticket ticket : bookedTickets) {
            System.out.println(ticket.getFirstName() + " " + ticket.getLastName() + ", Birth Year: " + ticket.getBirthYear());
        }
    }

    // method for count of each ticket
    private static void showTicketCount(List<Ticket> bookedTickets) {
        Map<String, Integer> ticketCount = new HashMap<>();
        for (Ticket ticket : bookedTickets) {
            ticketCount.put(ticket.getTicketType(), ticketCount.getOrDefault(ticket.getTicketType(), 0) + 1);
        }

        System.out.println("Ticket Counts:");
        for (String ticketType : TICKET_TYPES) {
            int count = ticketCount.getOrDefault(ticketType, 0);
            System.out.println(ticketType + ": " + count);
        }
    }

    // method to save booked tickets to file
    private static void saveBookedTickets(List<Ticket> bookedTickets) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Ticket ticket : bookedTickets) {
                writer.println(ticket.getTicketType() + "," +
                        ticket.getFirstName() + "," +
                        ticket.getLastName() + "," +
                        ticket.getBirthYear());
            }
        } catch (IOException e) {
            System.out.println("Failed to save booked tickets: " + e.getMessage());
        }
    }

    // method for viewing the booked tickets from a file
    private static List<Ticket> loadBookedTickets() {
        List<Ticket> bookedTickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String ticketType = data[0];
                    String firstName = data[1];
                    String lastName = data[2];
                    int birthYear = Integer.parseInt(data[3]);
                    Ticket ticket = new Ticket(ticketType, firstName, lastName, birthYear);
                    bookedTickets.add(ticket);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No booked tickets file found. Starting with an empty list.");
        } catch (IOException e) {
            System.out.println("Failed to load booked tickets: " + e.getMessage());
        }

        return bookedTickets;
    }
}

// class to represent a ticket
class Ticket {
    private String ticketType;
    private String firstName;
    private String lastName;
    private int birthYear;

    public Ticket(String ticketType, String firstName, String lastName, int birthYear) {
        this.ticketType = ticketType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    // getters for ticket 
    public String getTicketType() {
        return ticketType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    //  representation of the ticket in string form
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketType='" + ticketType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
