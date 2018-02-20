package ie.dit.comp.student;
import java.util.Scanner;
 
public class GetStudentDetails
{
    public GetStudentDetails()
    {
    	String name;
        int StudentNo;
        int programmingMark;

      	Scanner scan = new Scanner(System.in);
           
        System.out.print("Enter Name: ");
        name=scan.nextLine();
        System.out.print("Enter studentNo: ");
        StudentNo=scan.nextInt();
        System.out.print("Enter marks in Programming: ");
        programmingMark=scan.nextInt();

        System.out.println("\n\nStudent Number:" + StudentNo);
        System.out.println("Name: " + name);
        System.out.println("Mark in programming: " +programmingMark);
    }
}