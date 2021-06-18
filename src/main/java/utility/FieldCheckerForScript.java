package utility;

import data.Position;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class FieldCheckerForScript {
    private final ArrayList<String> parameters;

    public FieldCheckerForScript(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public <T> T readAndCheckField(String FieldName, String error, FieldCheckerHelp<T> rule) throws NullPointerException, DateTimeParseException, IllegalArgumentException {
        T temp = null;

        for (String line : parameters) {
            temp = rule.check(line);
        }
        return temp;
    }


    public String readAndCheckName() throws NullPointerException {
        FieldCheckerHelp<String> tempInterface = str -> {
            if (str == null || str.equals("")) {
                throw new NullPointerException();
            }
            return str;
        };
        return readAndCheckField("name", "", tempInterface);
    }

    public Long readAndCheckX() throws NumberFormatException {
        FieldCheckerHelp<Long> tempInterface = str -> {
            long result = Long.parseLong(str);
            if (result > 768) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("coordinate X", "(Reminder: Coordinate X can't be more than 768.)", tempInterface);
    }

    public Integer readAndCheckY() {
        FieldCheckerHelp<Integer> tempInterface = Integer::parseInt;
        return readAndCheckField("coordinate Y", "", tempInterface);
    }

    public Double readAndCheckSalary() throws NumberFormatException {
        FieldCheckerHelp<Double> tempInterface = str -> {
            double result = Double.parseDouble(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("salary", "(Reminder: Salary should be more than 0.)", tempInterface);
    }

    public ZonedDateTime readAndCheckStartDate() {
        FieldCheckerHelp<ZonedDateTime> tempInterface = str -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
            return ZonedDateTime.parse(str, formatter);
        };
        return readAndCheckField("start date", "", tempInterface);
    }

    public ZonedDateTime readAndCheckEndDate() {
        FieldCheckerHelp<ZonedDateTime> tempInterface = str -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
            if (str == null) {
                return null;
            } else {
                return ZonedDateTime.parse(str, formatter);
            }
        };
        return readAndCheckField("end date", "", tempInterface);
    }

    public Position readAndCheckPos() {
        FieldCheckerHelp<Position> tempInterface = str -> {
            if (str == null) {
                return null;
            } else {
                return Position.valueOf(str.toUpperCase());
            }
        };
        return readAndCheckField("position", "", tempInterface);
    }

    public Long readAndCheckHeight() throws NumberFormatException {
        FieldCheckerHelp<Long> tempInterface = str -> {
            long result = Long.parseLong(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("height", "(Reminder: Height should be more than 0.)", tempInterface);
    }

    public Integer readAndCheckWeight() throws NumberFormatException {
        FieldCheckerHelp<Integer> tempInterface = str -> {
            int result = Integer.parseInt(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("weight", "(Reminder: Weight should be more than 0.)", tempInterface);
    }
}
