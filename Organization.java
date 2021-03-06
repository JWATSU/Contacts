package contacts;

public class Organization extends Contact
{
    private String name;
    private String address;

    public Organization()
    {
        super();
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
    public boolean updateEditableField(String fieldToUpdate, String newValue)
    {
        boolean fieldWasUpdated = true;
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
                if (!setPhoneNumber(newValue))
                {
                    fieldWasUpdated = false;
                }
                break;
        }
        return fieldWasUpdated;
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
