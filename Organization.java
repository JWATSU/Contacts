package contacts;

public class Organization extends Contact
{
    private String name;
    private String address;

    public Organization(String phoneNumber, String name, String address)
    {
        super(phoneNumber);
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

    public String getName()
    {
        return name;
    }

    @Override
    public String getFullName()
    {
        return name;
    }

    @Override
    public String[] getEditableFields()
    {
        return new String[]{"name", "address", "number"};
    }

    @Override
    public void updateEditableField(String fieldToUpdate, String newValue)
    {
        String field = fieldToUpdate.toLowerCase();
        switch (field)
        {
            case "name":
                setName(newValue);
                break;
            case "address":
                setAddress(newValue);
                break;
            case "number":
                setPhoneNumber(newValue);
                break;
        }
    }

    @Override
    public String getEditableFieldValue(String editableField)
    {
        String field = editableField.toLowerCase();
        switch (field)
        {
            case "name":
                return getName();
            case "address":
                return getAddress();
            case "number":
                return getPhoneNumber();
            default:
                return null;
        }
    }

    public String getAddress()
    {
        return address;
    }

    @Override
    public String toString()
    {
        return "Organization name: " + name + "\n" +
                "Address: " + address + "\n" +
                super.toString();
    }
}
