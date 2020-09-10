package contacts;

import java.time.temporal.ChronoUnit;

public class Organization extends Contact
{
    private String name;
    private String address;

    public Organization(String phoneNumber, String name, String address, boolean isPerson)
    {
        super(phoneNumber, isPerson);
        this.name = name;
        this.address = address;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    String getFullName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Organization name: " + name + "\n" +
                "Address: " + address + "\n" +
                super.toString();
    }
}
