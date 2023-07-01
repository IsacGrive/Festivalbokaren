package com.isacgrive;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

public class FestivalBokarenTest extends TestCase {
    private List<Ticket> bookedTickets;

    protected void setUp() {
        bookedTickets = new ArrayList<>();
    }

    public void testBookTicket_ValidTicketType_SuccessfullyBooked() {
        TicketBookingSystem.bookTicket(new FakeScanner("2\nJohn\nDoe\n1990\n"), bookedTickets);

        Assert.assertEquals(1, bookedTickets.size());
        Ticket bookedTicket = bookedTickets.get(0);
        Assert.assertEquals("standardPlus", bookedTicket.getTicketType());
        Assert.assertEquals("John", bookedTicket.getFirstName());
        Assert.assertEquals("Doe", bookedTicket.getLastName());
        Assert.assertEquals(1990, bookedTicket.getBirthYear());
    }

    public void testShowBookedTickets_NoTicketsBooked_DisplayNoTicketsMessage() {
        FakePrintStream fakePrintStream = new FakePrintStream();
        System.setOut(fakePrintStream);

        TicketBookingSystem.showBookedTickets(bookedTickets);

        Assert.assertEquals("No tickets have been booked yet.\n", fakePrintStream.getOutput());
    }

    public void testShowBookedTickets_BookedTicketsExist_DisplayBookedTickets() {
        bookedTickets.add(new Ticket("standard", "John", "Doe", 1990));
        bookedTickets.add(new Ticket("VIP", "Jane", "Smith", 1985));
        FakePrintStream fakePrintStream = new FakePrintStream();
        System.setOut(fakePrintStream);

        TicketBookingSystem.showBookedTickets(bookedTickets);

        Assert.assertEquals("Booked Tickets:\n" +
                "John Doe, Birth Year: 1990\n" +
                "Jane Smith, Birth Year: 1985\n", fakePrintStream.getOutput());
    }

    public void testShowTicketCount_BookedTicketsExist_DisplayTicketCounts() {
        bookedTickets.add(new Ticket("standard", "John", "Doe", 1990));
        bookedTickets.add(new Ticket("VIP", "Jane", "Smith", 1985));
        bookedTickets.add(new Ticket("standard", "Alice", "Johnson", 1995));
        bookedTickets.add(new Ticket("standardPlus", "Bob", "Williams", 1992));
        FakePrintStream fakePrintStream = new FakePrintStream();
        System.setOut(fakePrintStream);

        TicketBookingSystem.showTicketCount(bookedTickets);

        Assert.assertEquals("Ticket Counts:\n" +
                "standard: 2\n" +
                "standardPlus: 1\n" +
                "VIP: 1\n" +
                "Fuktioner: 0\n", fakePrintStream.getOutput());
    }

    public static TestSuite suite() {
        return new TestSuite(TicketBookingSystemTest.class);
    }

    private static class FakeScanner extends java.util.Scanner {
        private final String input;

        public FakeScanner(String input) {
            super("");
            this.input = input;
        }

        @Override
        public String nextLine() {
            return input;
        }
    }

    private static class FakePrintStream extends java.io.PrintStream {
        private final StringBuilder output;

        public FakePrintStream() {
            super(new java.io.ByteArrayOutputStream());
            this.output = new StringBuilder();
        }

        @Override
        public void println(String x) {
            output.append(x).append("\n");
        }

        public String getOutput() {
            return output.toString();
        }
    }
}
