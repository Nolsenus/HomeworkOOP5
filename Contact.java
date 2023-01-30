import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Contact {
    private final static String NO_NUMBER = "В контакте нет указанного номера.";
    private final static String DUPLICATE_NUMBER = "Номер уже есть в этом контакте.";
    private String name;
    private List<PhoneNumber> numbers;

    public String getName() {
        return name;
    }

    public List<PhoneNumber> getNumbers() {
        return numbers;
    }

    public Contact(String name, List<PhoneNumber> numbers) throws EmptyContactException {
        if (numbers.size() == 0) {
            throw new EmptyContactException("Нельзя создать контакт без номеров.");
        }
        this.name = name;
        this.numbers = new LinkedList<>(numbers);
    }

    public Contact(String name, PhoneNumber number) {
        this.name = name;
        this.numbers = new LinkedList<>(Collections.singletonList(number));
    }

    public void addNumber(PhoneNumber number) throws NumberAlreadyInContactException {
        if (numbers.contains(number)) {
            throw new NumberAlreadyInContactException(DUPLICATE_NUMBER);
        }
        numbers.add(number);
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void removeNumber(PhoneNumber number) throws RemovingOnlyNumberException, NumberDoesNotExistException {
        if (!numbers.contains(number)) {
            throw new NumberDoesNotExistException(NO_NUMBER);
        }
        if (numbers.size() == 1) {
            throw new RemovingOnlyNumberException("Нельзя удалять единственный номер в контакте.");
        }
        numbers.remove(number);
    }

    public void updateNumber(PhoneNumber oldNumber, PhoneNumber newNumber)
            throws NumberDoesNotExistException, NumberAlreadyInContactException {
        if (!numbers.contains(oldNumber)) {
            throw new NumberDoesNotExistException(NO_NUMBER);
        }
        if (numbers.contains(newNumber)) {
            throw new NumberAlreadyInContactException(DUPLICATE_NUMBER);
        }
        int index = numbers.indexOf(oldNumber);
        numbers.remove(oldNumber);
        numbers.add(index, newNumber);
    }

    public void updateFirstNumber(PhoneNumber newNumber) throws NumberAlreadyInContactException {
        if (numbers.contains(newNumber)) {
            throw new NumberAlreadyInContactException(DUPLICATE_NUMBER);
        }
        numbers.remove(0);
        numbers.add(0, newNumber);
    }

    public String asString(boolean allNumbersOnSeparateLines) {
        String start = String.format("%s: ", name);
        String indent = String.format("%" + start.length() + "s", "");
        StringBuilder sb = new StringBuilder(start);
        String separator = allNumbersOnSeparateLines ? ";\n" + indent : ", ";
        for (int i = 0; i < numbers.size() - 1; i++) {
            sb.append(numbers.get(i));
            sb.append(separator);
        }
        sb.append(numbers.get(numbers.size() - 1));
        sb.append('.');
        return sb.toString();
    }

    @Override
    public String toString() {
        return asString(false);
    }

}
