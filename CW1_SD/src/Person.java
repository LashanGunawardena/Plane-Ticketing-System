public class Person {
    private String name;
    private String surname;
    private String email;
    public Person(String name, String surname, String email) {//Constructor
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    //Getters
    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

    public String getEmail(){
        return this.email;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void printPerson(){//Prints the person's information
        System.out.println("Person Name: " + this.name);
        System.out.println("Person Surname: " + this.surname);
        System.out.println("Person Email: " + this.email);
    }
}
