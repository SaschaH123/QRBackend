package org.sascha.qrbackend.User.DTO;

/**
 * DTO für Company Registration Request
 * Keine JPA Annotationen! Das ist nur für Datenübertragung
 */
public class CompanyRequestDTO {

    private String companyName;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private Integer plz;
    private String companyEmail;
    private String companyPassword;

    // ========== KONSTRUKTOR ==========
    public CompanyRequestDTO() {
    }

    public CompanyRequestDTO(String companyName, String firstName, String lastName,
                             String street, String city, Integer plz,
                             String companyEmail, String companyPassword) {
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.plz = plz;
        this.companyEmail = companyEmail;
        this.companyPassword = companyPassword;
    }

    // ========== GETTER & SETTER ==========
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }
}