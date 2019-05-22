package my.vaadin.app;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Customer implements Serializable, Cloneable {

	private Long id;
	private String firstName = "";
	private String lastName = "";
	private LocalDate birthDate;
	private String email = "";
	private String address = "";
	private String city = "";
	private String postalCode = "";

	public Long getId() { return id; }

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() { return address; }

	public void setAddress(String address) { this.address = address; }

	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }

	public String getPostalCode() { return postalCode; }

	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Customer && obj.getClass().equals(getClass())) {
			return this.id.equals(((Customer) obj).id);
		}

		return false;
	}

	@Override
	public Customer clone() throws CloneNotSupportedException {
		return (Customer) super.clone();
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}