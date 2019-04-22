package core.model;

import javax.persistence.Entity;

@Entity
public class Student extends BaseEntity<Long> {
    private String serialnumber;
    private String name;
    private int gr;

    public Student() {
    }

    public Student(int gr, String name, String serialnumber) {
        this.serialnumber = serialnumber;
        this.name = name;
        this.gr = gr;
    }

    public String getSerialNumber() {
        return serialnumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialnumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGr() {
        return gr;
    }

    public void setGr(int gr) {
        this.gr = gr;
    }

    @Override
    public String toString() {
        return "Student{\n Serial number: " + serialnumber +
                "\n Name: " + name + "\n Group: " + gr +
                "\n       }" + super.toString();
    }
}