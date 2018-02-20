import java.io.*;

public class Person implements Serializable{
	String ClassName;
	String Surname;
	String SudentID;

	public Person(String ClassName, String Surname, String SudentID){
		this.ClassName = ClassName;
		this.Surname = Surname;
		this.SudentID = SudentID;
	}

	public String getClassName(){
		return ClassName;
	}

	public String getSurname(){
		return Surname;
	}

	public int getSudentID(){
		return SudentID;
	}
}