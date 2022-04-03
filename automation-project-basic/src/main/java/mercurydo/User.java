//http://www.jsonschema2pojo.org/

package mercurydo;

public class User {
	
	public String firstName;
	public String lastName;
	public String phone;
	public String email;
	public String address1;
	public String address2;
	public String city;
	public String state;
	public String postalCode;
	public String country;
	public String userName;
	public String password;
	public String confirmPassword;
	
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + 
        		", lastName=" + lastName + "]";
    }
	
}

