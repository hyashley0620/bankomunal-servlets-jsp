package com.bankomunal.model;

/**
 * Modelo que representa un usuario de Bankomunal.
 * Se mapea a la tabla `users` del esquema bankomunal.sql.
 */
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String tipoDocumento;
    private String identificationNumber;
    private String phone;
    private String status;

    public User() {
    }

    public User(String firstName, String lastName, String email, String passwordHash,
                String tipoDocumento, String identificationNumber, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.tipoDocumento = tipoDocumento;
        this.identificationNumber = identificationNumber;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNombreCompleto() {
        return firstName + " " + lastName;
    }
}
