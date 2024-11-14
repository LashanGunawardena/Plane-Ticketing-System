import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seat;
    private int price;
    private Person person;
    public Ticket(String row, int seat, int price, Person person) {//Constructor to be used in buy seat
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public Ticket(String row, int seat) {//Constructor to be used in cancel seat
        this.row = row;
        this.seat = seat;
    }

    //Getters
    public String getRow(){
        return this.row;
    }

    public int getSeat(){
        return this.seat;
    }

    public int getPrice(){
        return this.price;
    }//Gets the price of the current ticket's seat

    public Person getPerson(){
        return this.person;
    }

    //Setters
    public void setRow(String row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void printInfo(){//Prints the tickets information
        person.printPerson();
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: " + price);

    }

    public void save(){//Saves the details of the booked seat in a text file
        try {
            String fileName = row + seat +".txt";
            FileWriter my_file = new FileWriter(fileName);
            my_file.write("Row: " + row
                    +"\nSeat: " + seat
                    +"\nPrice: " + price
                    +"\nPerson Name: " + person.getName()
                    +"\nPerson Surname: " + person.getSurname()
                    +"\nPerson Email: " + person.getEmail());
            my_file.close();
        }
        catch (IOException e) {
            System.out.println("Error occurred!!!");
        }
    }

    public void delete(){//Deletes the text file
        String fileName = row + seat +".txt";
        File my_file = new File(fileName);
        if(my_file.exists()) {
            my_file.delete();
            System.out.println("File deleted successfully.");
        }
        else{
            System.out.println("File does not exist!");
        }
    }

}
