package contacts;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ContactHandler
{
    private final Scanner scanner = new Scanner(System.in);
    private final List<Contact> contacts = new ArrayList<>();
    private final Map<String, Runnable> menuChoices;

    public ContactHandler()
    {
        menuChoices = new TreeMap<>();
        menuChoices.put("add", this::addContact);
        menuChoices.put("edit", this::editContact);
        menuChoices.put("remove", this::removeContact);
        menuChoices.put("count", this::displayCountOfContacts);
        menuChoices.put("list", this::displayListOfContacts);
        menuChoices.put("info", this::displayInfoAboutContact);
    }

    public void start()
    {
        while (true)
        {
            System.out.printf("\nEnter action (%s, exit): ", String.join(", ", menuChoices.keySet()));
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("exit") || !menuChoices.containsKey(choice))
            {
                break;
            }
            menuChoices.get(choice).run();
        }
    }

    private void displayInfoAboutContact()
    {
        if (!contacts.isEmpty())
        {
            displayListOfContacts();
            System.out.print("Enter index to show info: ");
            int index = Integer.parseInt(scanner.nextLine())-1;
            boolean legalIndex = index >= 0 && index < contacts.size();
            if (legalIndex)
            {
                Contact contact = contacts.get(index);
                System.out.println(contact);
            }
        } else
        {
            System.out.println("There are no records to display.");
        }
    }

    private void addContact()
    {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine().toLowerCase();
        if (type.equals("person"))
        {
            addPerson();
        } else if (type.equals("organization"))
        {
            addOrganization();
        }
    }

    private void addPerson()
    {
        System.out.print("Enter the first name of the person: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter the last name of the person: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter the birth date: ");
        String birthdate = scanner.nextLine();
        LocalDate birthday = Validator.validateBirthDate(birthdate) ? LocalDate.parse(birthdate) : null;

        System.out.print("Enter the gender (M,F): ");
        String genderEntered = scanner.nextLine().toUpperCase();
        Person.Gender genderOfPerson = Person.Gender.stringToGender(genderEntered);
        if (genderOfPerson == null)
        {
            System.out.println("Illegal gender entered.");
        }

        System.out.print("Enter the phone number: ");
        String phoneNumber = scanner.nextLine();
        String phoneNumberOfPerson = Validator.validatePhoneNumber(phoneNumber) ? phoneNumber : null;

        Person person = new Person(firstName, lastName, phoneNumberOfPerson, birthday, genderOfPerson, true);
        contacts.add(person);

        System.out.println("The record was added.");
    }

    private void addOrganization()
    {
        System.out.print("Enter the organization name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the address: ");
        String address = scanner.nextLine();
        System.out.print("Enter the number: ");
        String phoneNumber = scanner.nextLine();
        String organizationPhoneNumber = Validator.validatePhoneNumber(phoneNumber) ? phoneNumber : null;

        Organization organization = new Organization(organizationPhoneNumber, name, address, false);
        contacts.add(organization);

        System.out.println("The record was added.");
    }

    private void removeContact()
    {
        if (contacts.isEmpty())
        {
            System.out.println("No records to remove!");
        } else
        {
            displayListOfContacts();
            System.out.print("Select a record: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (contacts.remove(index) != null)
            {
                System.out.println("The record was removed!");
            } else
            {
                System.out.println("Not a valid index. No record removed!");
            }
        }
    }

    private void editPerson(Contact contact)
    {
        Person person = (Person) contact;
        System.out.print("Select a field (first name, last name, birth, gender, number): ");
        String answer = scanner.nextLine();
        boolean wasUpdated = true;

        switch (answer)
        {
            case "first name":
                System.out.print("Enter new first name: ");
                String newFirstName = scanner.nextLine();
                person.setFirstName(newFirstName);
                break;
            case "last name":
                System.out.print("Enter new last name: ");
                String newLastName = scanner.nextLine();
                person.setLastName(newLastName);
                break;
            case "birth":
                System.out.print("Enter new birthday: ");
                String newBirthday = scanner.nextLine();
                if (Validator.validateBirthDate(newBirthday))
                {
                    person.setBirthday(LocalDate.parse(newBirthday));
                } else
                {
                    wasUpdated = false;
                }
                break;
            case "gender":
                System.out.print("Enter new gender (M/F): ");
                String newGender = scanner.nextLine();
                Person.Gender genderOfPerson = Person.Gender.stringToGender(newGender);
                if (!(genderOfPerson == null))
                {
                    person.setGender(genderOfPerson);
                } else
                {
                    System.out.println("Illegal gender entered.");
                    wasUpdated = false;
                }
                break;
            case "number":
                System.out.print("Enter new number: ");
                String newNumber = scanner.nextLine();
                if (Validator.validatePhoneNumber(newNumber))
                {
                    person.setPhoneNumber(newNumber);
                } else
                {
                    wasUpdated = false;
                }
                break;
        }
        if (wasUpdated)
        {
            System.out.println("The record updated!");
            person.setLastEditDate(LocalDateTime.now());
        }
    }

    private void editOrganization(Contact contact)
    {
        Organization organization = (Organization) contact;
        boolean wasUpdated = true;
        System.out.print("Select a field (organization name, address, number): ");
        String answer = scanner.nextLine();
        switch (answer)
        {
            case "organization name":
                System.out.print("New organization name: ");
                String newName = scanner.nextLine();
                organization.setName(newName);
                break;
            case "address":
                System.out.print("New address: ");
                String newAddress = scanner.nextLine();
                organization.setAddress(newAddress);
                break;
            case "number":
                System.out.print("Enter new number: ");
                String newNumber = scanner.nextLine();
                if (Validator.validatePhoneNumber(newNumber))
                {
                    organization.setPhoneNumber(newNumber);
                } else
                {
                    wasUpdated = false;
                }
                break;
        }
        if (wasUpdated)
        {
            System.out.println("The record updated!");
            organization.setLastEditDate(LocalDateTime.now());
        }
    }

    private void editContact()
    {
        if (contacts.isEmpty())
        {
            System.out.println("No records to edit.");
        } else
        {
            displayListOfContacts();
            System.out.println("Select a record: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            Contact contact = contacts.get(index);
            if (contact.isPerson())
            {
                editPerson(contact);
            } else
            {
                editOrganization(contact);
            }
        }
    }

    private void displayListOfContacts()
    {
        if (contacts.isEmpty())
        {
            System.out.println("There are no records to display.");
        } else
        {
            for (int i = 0; i < contacts.size(); i++)
            {
                System.out.println(i + 1 + ". " + contacts.get(i).getFullName());
            }
        }
    }

    private void displayCountOfContacts()
    {
        System.out.println("The Phone Book has " + contacts.size() + " records.");
    }
}
