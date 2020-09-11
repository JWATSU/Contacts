package contacts;

import java.time.LocalDate;

public class Person extends Contact
{
    private LocalDate birthday;
    private Gender gender;
    private String firstName;
    private String lastName;

    public Person()
    {
        super();
    }

    public LocalDate getBirthday()
    {
        return birthday;
    }

    public Gender getGender()
    {
        return gender;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setBirthday(LocalDate birthday)
    {
        this.birthday = birthday;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    @Override
    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    @Override
    public String[] getEditableFields()
    {
        return new String[]{"first name", "last name", "number", "birthday", "gender"};
    }

    @Override
    public boolean updateEditableField(String fieldToUpdate, String newValue)
    {
        boolean fieldWasUpdated = true;
        String field = fieldToUpdate.toLowerCase();
        switch (field)
        {
            case "number":
                if (!setPhoneNumber(newValue))
                {
                    fieldWasUpdated = false;
                }
                break;
            case "first name":
                setFirstName(newValue);
                break;
            case "last name":
                setLastName(newValue);
                break;
            case "birthday":
                if (Validator.validateBirthDate(newValue))
                {
                    LocalDate newBirthday = LocalDate.parse(newValue);
                    setBirthday(newBirthday);
                } else
                {
                    System.out.println("Bad birth date!");
                    fieldWasUpdated = false;
                }
                break;
            case "gender":
                Gender newGender = Gender.stringToGender(newValue);
                if (newGender != null)
                {
                    setGender(newGender);
                } else
                {
                    System.out.println("Invalid gender!");
                    fieldWasUpdated = false;
                }
                break;
            default:
                System.out.println("Invalid selection!");
                fieldWasUpdated = false;
        }
        return fieldWasUpdated;
    }

    @Override
    public String getEditableFieldValue(String editableField)
    {
        String field = editableField.toLowerCase();
        switch (field)
        {
            case "number":
                return getPhoneNumber();
            case "first name":
                return getFirstName();
            case "last name":
                return getLastName();
            case "birthday":
                return getBirthday().toString();
            case "gender":
                return getGender().toString();
            default:
                return null;
        }
    }

    @Override
    public String toString()
    {
        String birthdate = birthday == null ? "[no data]" : getBirthday().toString();
        String genderOfPerson = gender == null ? "[no data]" : getGender().toString();

        return "Name: " + getFirstName() + "\n" +
                "Surname: " + getLastName() + "\n" +
                "Birth date: " + birthdate + "\n" +
                "Gender: " + genderOfPerson + "\n" +
                super.toString();
    }

    public enum Gender
    {
        MALE("M"),
        FEMALE("F");

        private final String abbreviation;

        Gender(String abbreviation)
        {
            this.abbreviation = abbreviation;
        }

        /**
         * @return the Gender(MALE/FEMALE) represented by the abbreviation (M/F), or null if no matching Gender was found
         */
        public static Gender stringToGender(String abbreviation)
        {
            for (Gender b : Gender.values())
            {
                //Input in the form of male/female does not work right now. Only m/f.
                if (b.abbreviation.equalsIgnoreCase(abbreviation))
                {
                    return b;
                }
            }
            return null;
        }

        @Override
        public String toString()
        {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }
}
