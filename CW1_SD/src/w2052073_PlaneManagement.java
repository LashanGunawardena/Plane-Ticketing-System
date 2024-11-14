/*
  I confirm that I understand what plagiarism / collusion / contract cheating is and have read and understood the section on Assessment Offences in the Essential Information for Students.
  The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.

  References;
  .isLetter - https://www.javatpoint.com/post/java-character-isletter-method
  .contains - https://www.w3schools.com/java/ref_string_contains.asp
 */

import java.util.Scanner;
import java.util.InputMismatchException;
public class w2052073_PlaneManagement{
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args){
        //Jagged Array for saving the ticket details
        Ticket[][] ticketsList = new Ticket[4][];
        ticketsList[0] = new Ticket[14];
        ticketsList[1] = new Ticket[12];
        ticketsList[2] = new Ticket[12];
        ticketsList[3] = new Ticket[14];

        //Jagged Array to save the rows and seats
        int[][] Row_Array = new int[4][];
        Row_Array[0] = new int[14];
        Row_Array[1] = new int[12];
        Row_Array[2] = new int[12];
        Row_Array[3] = new int[14];

        System.out.println();
        System.out.println("Welcome to the Plane Management application");
        while (true) {
            try {
                System.out.println();
                System.out.println("**********************************************");
                System.out.println("*                 MENU OPTIONS               *");
                System.out.println("**********************************************");
                System.out.println("1) Buy a seat \n2) Cancel a seat \n3) Find first available seat \n4) Show seating plan \n5) Print tickets information and total sales \n6) Search ticket \n0) Quit");
                System.out.println("**********************************************");
                System.out.println();
                System.out.print("Please select an option: ");
                int choice = input.nextInt();
                System.out.println();
                switch (choice) {
                    case 1:
                        buy_seat(Row_Array, ticketsList, input);
                        break;
                    case 2:
                        cancel_seat(Row_Array, ticketsList);
                        break;
                    case 3:
                        find_first_available(Row_Array);
                        break;
                    case 4:
                        show_seating_plan(Row_Array);
                        break;
                    case 5:
                        print_tickets_info(ticketsList);
                        break;
                    case 6:
                        search_ticket(Row_Array, ticketsList);
                        break;
                    case 0:
                        input.close();//Closes the scanner
                        System.out.println("Have a nice day!....");
                        System.exit(0);//Terminates the program and exits
                    default:
                        System.out.println("Option is out of range!");

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                input.next();//Clear the invalid output
            }
        }
    }

    /**
     * This method buys a seat after getting an input for row letter and seat number.
     * @param Row_Array - saves booked row and seat
     * @param ticketsList - saves booked ticket details
     */
    public static void buy_seat(int[][] Row_Array,Ticket[][] ticketsList, Scanner input){
        try {
            System.out.print("Enter row letter[A-D]: ");
            String rowLetter = input.next().toUpperCase();
            System.out.print("Enter seat number: ");
            int seatNumber = input.nextInt();
            int rowNumber = check_row_seat(rowLetter, seatNumber);//Gives the relative row number for the row letter

            if(rowNumber == -1){
                return;
            }

            int ticketPrice = calculate_price(seatNumber);//Gives the price of the respective seat

            if (Row_Array[rowNumber][seatNumber - 1] == 0) {//Checks whether the seat is booked
                Row_Array[rowNumber][seatNumber - 1] = 1;

            } else {
                System.out.println("Seat is already booked!");
                return;
            }

            System.out.print("Enter first name: ");
            String firstName = input.next();
            firstName = check_str(firstName, "first name");
            System.out.print("Enter surname: ");
            String surName = input.next();
            surName = check_str(surName, "surname");
            System.out.print("Enter email: ");
            String eMail = input.next();
            eMail = check_email(eMail);

            System.out.println("Ticket booking is successful");
            Person person = new Person(firstName, surName, eMail);//Creates object
            Ticket ticket = new Ticket(rowLetter, seatNumber, ticketPrice, person);//Creates object
            ticket.save();//@see Ticket #save()
            ticketsList[rowNumber][seatNumber - 1] = ticket;//Saves the details of the booked seat in the ticketsList array
        } catch (InputMismatchException e) {
            System.out.println("Invalid seat number!");
            input.next();//Clear the invalid output
        }
    }

    /**
     * This method cancels a seat after getting an input for row letter and seat number.
     * @param Row_Array - saves booked row and seat
     * @param ticketsList - saves booked ticket details
     */
    public static void cancel_seat(int[][] Row_Array,Ticket[][] ticketsList) {
        try {
            System.out.print("Enter row letter[A-D]: ");
            String rowLetter = input.next().toUpperCase();
            System.out.print("Enter seat number: ");
            int seatNumber = input.nextInt();
            int rowNumber = check_row_seat(rowLetter, seatNumber);//Gives the relative row number for the row letter

            if (rowNumber == -1) {
                return;
            }

            if (Row_Array[rowNumber][seatNumber - 1] == 1) {
                Row_Array[rowNumber][seatNumber - 1] = 0;
                System.out.println("Ticket is cancelled successfully");
                ticketsList[rowNumber][seatNumber - 1] = null;//Removes the details from the array
                Ticket ticket = new Ticket(rowLetter, seatNumber);//Creates object
                ticket.delete();//@see Ticket #delete()
            } else {
                System.out.println("The seat is already available!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid seat number!");
            input.next();//Clear the invalid output
        }
    }

    /**
     * This method find the first available seat from row A to row D.
     * @param Row_Array - saves booked row and seat
     */
    public static void find_first_available(int[][] Row_Array){
        String[] Row_Letters = {"A","B","C","D"};
        for(int i=0;i<Row_Array.length;i++){
            for(int j=0;j<Row_Array[i].length;j++){
                if(Row_Array[i][j] == 0) {
                    System.out.printf("Row %s seat number %d is available\n",Row_Letters[i],(j+1));
                    return;
                }
            }
        }
        System.out.println("No seats are available!");
    }

    /**
     * This method shows the seating plan.
     * @param Row_Array - saves booked row and seat
     */
    public static void show_seating_plan(int[][] Row_Array) {
        for (int[] row_element : Row_Array) {
            for (int seat_element : row_element) {
                if (seat_element == 0) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    /**
     * This method prints the information of the tickets that has been booked upto that instance.
     * @param ticketsList - saves booked ticket details
     */
    public static void print_tickets_info(Ticket[][] ticketsList) {
        int total = 0;
        for (Ticket[] tickets : ticketsList) {
            for (Ticket ticket : tickets) {
                if (ticket != null) {
                    ticket.printInfo();//@see Ticket #printInfo()
                    int currentPrice = ticket.getPrice();//@see Ticket #getPrice()
                    total += currentPrice;
                    System.out.println();
                }
            }
        }
        if (total == 0) {
            System.out.println("No tickets have been booked!");
        }
        else {
            System.out.println("Total amount = £" + total);
        }
    }

    /**
     * This method searches a specific ticket and prints its information after getting an input for row letter and seat number.
     * @param Row_Array - saves booked row and seat
     * @param ticketsList - saves booked ticket details
     */
    public static void search_ticket(int[][] Row_Array, Ticket[][] ticketsList) {
        try {
            System.out.print("Enter row letter[A-D]: ");
            String rowLetter = input.next().toUpperCase();
            System.out.print("Enter seat number: ");
            int seatNumber = input.nextInt();
            System.out.println();
            int rowNumber = check_row_seat(rowLetter, seatNumber);//Gives the relative row number for the row letter

            if (rowNumber == -1) {
                return;
            }

            if (Row_Array[rowNumber][seatNumber - 1] == 1) {
                ticketsList[rowNumber][seatNumber - 1].printInfo();//Prints the tickets information
            } else {
                System.out.println("The seat is available!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid seat number!");
            input.next();//Clear the invalid output
        }
    }

    /**
     * This method calculates the price for the respective seat.
     * @param seat - ask for seat number
     * @return price
     */
    public static int calculate_price(int seat){
        int price;
        if (seat >= 1 && seat <= 5) {
            price = 200;
        } else if (seat >= 6 && seat <= 9) {
            price = 150;
        } else {
            price = 180;
        }
        return price;
    }

    /**
     * This method validates the row letter, seat number and returns the respective row number.
     * @param row - ask for row letter
     * @param seat - ask for seat number
     * @return rowNumber
     */
    public static int check_row_seat(String row, int seat) {
        switch (row) {
            case "A":
                if (seat <= 14 && seat > 0) {
                    return 0;
                } else {
                    System.out.println("Invalid seat number!");
                    System.out.println();
                    return -1;
                }
            case "B":
                if (seat <= 12 && seat > 0) {
                    return 1;
                } else {
                    System.out.println("Invalid seat number!");
                    System.out.println();
                    return -1;
                }
            case "C":
                if (seat <= 12 && seat > 0) {
                    return 2;
                } else {
                    System.out.println("Invalid seat number!");
                    System.out.println();
                    return -1;
                }
            case "D":
                if (seat <= 14 && seat > 0) {
                    return 3;
                } else {
                    System.out.println("Invalid seat number!");
                    System.out.println();
                    return -1;
                }
            default:
                System.out.println("Invalid row letter!");
                System.out.println();
                return -1;

        }
    }

    /**
     * This method validates the name.
     * @param name - ask for name
     * @param nameType - ask whether the name is firstname or surname
     * @return name
     */
    public static String check_str(String name, String nameType){
        while(true){
            boolean isValid = true;//boolean flag
            for (int i = 0; i < name.length(); i++) {//Checks the string, character by character to find out whether there are characters except for letters
                char letter = name.charAt(i);
                if(!(Character.isLetter(letter))){
                    isValid = false;
                    break;
                }
            }
            if(isValid){
                return name;
            }
            else{
                System.out.println("Invalid "+ nameType +"!");
                System.out.println();
                System.out.println("Enter "+ nameType +": ");
                name = input.next();
            }
        }
    }

    /**
     * This method validates the email.
     * @param email - ask for email
     * @return email
     */
    public static String check_email(String email){
        while(!(email.contains(".")&& email.contains("@"))){
            System.out.println("Invalid email!");
            System.out.println();
            System.out.println("Enter email: ");
            email = input.next();
        }
        return email;
    }
}

