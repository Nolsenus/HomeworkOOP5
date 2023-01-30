import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ContactBook {
    private final static String NO_CONTACT = "Не найден контакт с именем ";
    private final static String DUPLICATE_CONTACT = "Повторное имя контакта.";
    private List<Contact> contacts;

    public ContactBook(List<Contact> contacts) {
        this.contacts = new LinkedList<>(contacts);
    }

    public ContactBook() {
        this.contacts = new LinkedList<>();
    }

    public int indexOf(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (name.equals(contacts.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    private int checkNameExists(String name) throws ContactDoesNotExistException {
        int index = indexOf(name);
        if (index == -1) {
            throw new ContactDoesNotExistException(NO_CONTACT);
        }
        return index;
    }

    private int checkNameNotExists(String name) throws ContactNameOccupiedException {
        int index = indexOf(name);
        if (index != -1) {
            throw new ContactNameOccupiedException(DUPLICATE_CONTACT);
        }
        return index;
    }

    public void add(Contact contact) throws ContactNameOccupiedException {
        checkNameNotExists(contact.getName());
        contacts.add(contact);
    }

    public void addContactNumber(String name, PhoneNumber number)
            throws ContactDoesNotExistException, NumberAlreadyInContactException {
        int index = checkNameExists(name);
        contacts.get(index).addNumber(number);
    }

    public void updateContactName(String oldName, String newName)
            throws ContactDoesNotExistException, ContactNameOccupiedException {
        int index = checkNameExists(oldName);
        checkNameNotExists(newName);
        contacts.get(index).updateName(newName);
    }

    public void updateContactNumber(String name, PhoneNumber oldNumber, PhoneNumber newNumber)
            throws ContactDoesNotExistException, NumberDoesNotExistException, NumberAlreadyInContactException {
        int index = checkNameExists(name);
        contacts.get(index).updateNumber(oldNumber, newNumber);
    }

    public void updateContactFirstNumber(String name, PhoneNumber newNumber)
            throws ContactDoesNotExistException, NumberAlreadyInContactException {
        int index = checkNameExists(name);
        contacts.get(index).updateFirstNumber(newNumber);
    }

    public void removeContactNumber(String name, PhoneNumber number)
            throws ContactDoesNotExistException, NumberDoesNotExistException, RemovingOnlyNumberException {
        int index = checkNameExists(name);
        contacts.get(index).removeNumber(number);
    }

    public void removeContact(String name)
            throws ContactDoesNotExistException {
        int index = checkNameExists(name);
        contacts.remove(index);
    }

    public void exportToTxt(String filename, boolean allNumbersOnSeparateLines, boolean overwriteIfExists)
            throws IOException {
        String filepath = filename + ".txt";
        File file = new File(filepath);
        if (file.createNewFile() || overwriteIfExists) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(asString(allNumbersOnSeparateLines));
            bw.close();
        }
    }

    public void importFromTxt(String filepath)
            throws IOException, NotANumberException, ContactNameOccupiedException, EmptyContactException {
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);
        StringBuilder entireFileBuilder = new StringBuilder();
        while(scanner.hasNextLine()) {
            entireFileBuilder.append(scanner.nextLine());
        }
        String entireFile = entireFileBuilder.toString().replaceAll(" ", "");
        String[] entries = entireFile.split("\\.");
        for (String s : entries) {
            int colon = s.indexOf(':');
            String name = s.substring(0, colon);
            String[] numbers = s.substring(colon + 1).split(",");
            List<PhoneNumber> nums = new LinkedList<>();
            for (String num : numbers) {
                nums.add(new PhoneNumber(num));
            }
            add(new Contact(name, nums));
        }
    }

    public String asString(boolean allNumbersOnSeparateLines) {
        if (contacts.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contacts.size() - 1; i++) {
            sb.append(contacts.get(i).asString(allNumbersOnSeparateLines));
            sb.append('\n');
        }
        sb.append(contacts.get(contacts.size() - 1).asString(allNumbersOnSeparateLines));
        return sb.toString();
    }

    @Override
    public String toString() {
        return asString(false);
    }
}
