import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import util.TrippleDes;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	private int eid;
	private String fname;
	private String lname;
	private int salary;
	private String secretKey;
	private TrippleDes td;
	
	public Employee(String fname, String lname, int salary, String secretKey) {
		this.fname = fname;
		this.lname = lname;
		this.salary = salary;
		this.secretKey = secretKey;
	}
	
	public String getDecryptedSecretKey(){
		this.td = new TrippleDes();
		return td.decrypt(this.secretKey);
	}
	
	

}
