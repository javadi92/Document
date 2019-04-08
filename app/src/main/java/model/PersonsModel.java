package model;

public class PersonsModel {

    private int id;
    private String personName;
    private  int documentNumber;
    private byte[] personImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public byte[] getPersonImage() {
        return personImage;
    }

    public void setPersonImage(byte[] personImage) {
        this.personImage = personImage;
    }
}
