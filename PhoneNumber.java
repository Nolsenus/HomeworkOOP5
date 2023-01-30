public class PhoneNumber {
    private final String number;

    private boolean checkNumber(String number) {
        return number.matches("((\\+7)|8)[0-9]{10}");
    }

    public PhoneNumber(String number) throws NotANumberException {
        if (!checkNumber(number)) {
            throw new NotANumberException("Строка не является номером телефона.");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PhoneNumber) {
            return this.number.equals(((PhoneNumber) obj).number);
        }
        return false;
    }
}
