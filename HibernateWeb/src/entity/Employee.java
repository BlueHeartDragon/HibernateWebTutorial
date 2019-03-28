package entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
//---------------Hibernate Annotations-----------------------------------
@Entity
@Table(name = "EMPLOYEE")
//---------------For second-level caching. Strategy= READ_WRITE-----------
@Cacheable
@Cache(region="Employee", usage = CacheConcurrencyStrategy.READ_WRITE)

public class Employee {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int eid;
	@Column(name = "first_name", nullable = false)
	private String fname;
	@Column(name = "last_name", nullable = false)
	private String lname;
	@Column(name = "salary", nullable = false, length = 9)
	private int salary;
	@Column(name = "secret", unique = true, nullable = false)
	private String secretKey;

	public Employee(String fname, String lname, int salary, String secretKey) {
		if("".equals(lname.trim())) {
			lname=null;
		}
		if("".equals(fname.trim())) {
			fname=null;
		}
		if("".equals(secretKey.trim())) {
			secretKey=null;
		}
		
		this.fname = fname;
		this.lname = lname;
		this.salary = salary;
		this.secretKey = secretKey;
	}

	public String getDecryptedSecretKey() {
		return TrippleDes.decrypt(this.secretKey);
	}



}
