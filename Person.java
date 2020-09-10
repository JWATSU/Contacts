package contacts;

import java.time.LocalDate;

public class Person extends Contact
{
    private LocalDate birthday;
    private Gender gender;
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName, String phoneNumber, LocalDate birthday, Gender gender, boolean isPerson)
    {
        super(phoneNumber, isPerson);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
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
    String getFullName()
    {
        return firstName + " " + lastName;
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
                if (b.abbreviation.equalsIgnoreCase(abbreviation))
                {
                    return b;
                }
            }
            return null;
        }
    }
}
