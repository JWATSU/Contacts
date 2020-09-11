package contacts;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ContactHandler
{
    private final Scanner scanner = new Scanner(System.in);
    private final List<Contact> contacts = new ArrayList<>();
    private final Map<String, Runnable> mainMenuChoices;

    public ContactHandler()
    {
        mainMenuChoices = new TreeMap<>();
        mainMenuChoices.put("add", this::addContact);
        mainMenuChoices.put("list", this::listMenu);
        mainMenuChoices.put("search", this::searchForContact);
        mainMenuChoices.put("count", this::displayCountOfContacts);
    }

    public void start()
    {
        while (true)
        {
            System.out.printf("\n[menu] Enter action (%s, exit): ", String.join(", ", mainMenuChoices.keySet()));
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("exit") || !mainMenuChoices.containsKey(choice))
            {
                break;
            }
            mainMenuChoices.get(choice).run();
        }
    }

    private void searchForContact()
    {

    }

    private void displayInfoAboutContact()
    {
        if (!contacts.isEmpty())
        {
            printListOfContacts();
            System.out.print("Enter index to show info: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
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
        Contact contact;
        if ("person".equals(type))
        {
            contact = new Person();
        } else if ("organization".equals(type))
        {
            contact = new Organization();
        } else
        {
            System.out.println("Not a valid type.");
            return;
        }
        for (String value : contact.getEditableFields())
        {
            System.out.print("Enter the " + value + ": ");
            contact.updateEditableField(value, scanner.nextLine());
        }

        contacts.add(contact);
        System.out.println("The record was added.");
    }

    private void removeContact()
    {
        if (contacts.isEmpty())
        {
            System.out.println("No records to remove!");
        } else
        {
            printListOfContacts();
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

    private void editContact(Contact contact)
    {
        /*if (contacts.isEmpty())
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
        }*/
    }

    private void listMenu()
    {
        printListOfContacts();
        if (contacts.isEmpty())
        {
            return;
        }
        System.out.print("\n[list] Enter action ([number], back): ");
        String userChoice = scanner.nextLine();
        if (!userChoice.matches("\\d+"))
        {
            return;
        }
        int index = Integer.parseInt(userChoice) - 1;
        boolean isLegalIndex = index >= 0 && index < contacts.size();
        if (!isLegalIndex)
        {
            return;
        }
        Contact contact = contacts.get(index);
        modifyContactMenu(contact);
    }

    private void modifyContactMenu(Contact contact)
    {
        System.out.println(contact);
        boolean continueLoop = true;
        while (continueLoop)
        {
            System.out.print("\n[record] Enter action (edit, delete, menu): ");
            String userChoice = scanner.nextLine().toLowerCase();
            switch (userChoice)
            {
                case "edit":
                    editContact(contact);
                    break;
                case "delete":
                    deleteContact(contact);
                    break;
                case "menu":
                    continueLoop = false;
                    break;
                default:
                    System.out.println("Illegal action.");
            }
        }
    }

    private void deleteContact(Contact contact)
    {

    }

    private void printListOfContacts()
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
