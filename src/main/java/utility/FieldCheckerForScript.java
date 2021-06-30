package utility;

import data.Position;
import exceptions.IncorrectValueException;
import exceptions.NullFieldException;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class FieldCheckerForScript {

    public FieldCheckerForScript() {

    }

    public <T> T readAndCheckField(String field, String FieldName, String error, FieldCheckerHelp<T> rule) throws DateTimeParseException, NullFieldException, IncorrectValueException {
        T temp = null;
        temp = rule.check(field);
        return temp;
    }


    public String readAndCheckName(String name) throws NullFieldException, IncorrectValueException {
        FieldCheckerHelp<String> tempInterface = str -> {
            if (str == null || str.equals("")) {
                throw new NullFieldException("name");
            }
            return str;
        };
        return readAndCheckField(name,"name", "", tempInterface);
    }

    public Long readAndCheckX(String coordinateX) throws NumberFormatException, IncorrectValueException, NullFieldException {
        FieldCheckerHelp<Long> tempInterface = str -> {
            Long result = null;
            if (str != null) {
                result = Long.parseLong(str);
                if (result > 768) {
                    throw new IncorrectValueException("coordinate X", "(Reminder: Coordinate X can't be more than 768.)");
                }
            }else throw new NullFieldException("coordinate X");
            return result;
        };
        return readAndCheckField(coordinateX,"coordinate X", "(Reminder: Coordinate X can't be more than 768.)", tempInterface);
    }

    public Integer readAndCheckY(String coordinateY) throws NumberFormatException, IncorrectValueException, NullFieldException {
        FieldCheckerHelp<Integer> tempInterface = str -> {
            Integer result = null;
            if (str != null) {
                result = Integer.parseInt(str);
            } else throw new NullFieldException("coordinate Y");
            return result;
        };
        return readAndCheckField(coordinateY,"coordinate Y", "", tempInterface);
    }

    public Double readAndCheckSalary(String salary) throws NumberFormatException, IncorrectValueException, NullFieldException {
        FieldCheckerHelp<Double> tempInterface = str -> {
            Double result;
            if (str != null) {
                result = Double.parseDouble(str);
                if (result <= 0) {
                    throw new IncorrectValueException("salary", "(Reminder: Salary should be more than 0.)");
                }
            }else {
                throw new NullFieldException("salary");
            }
            return result;
        };
        return readAndCheckField(salary,"salary", "(Reminder: Salary should be more than 0.)", tempInterface);
    }

    public ZonedDateTime readAndCheckStartDate(String startDate) throws DateTimeParseException, IncorrectValueException, NullFieldException {
        FieldCheckerHelp<ZonedDateTime> tempInterface = str -> {
            ZonedDateTime result = null;
            if (str != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
                result = ZonedDateTime.parse(str, formatter);
            } else throw new NullFieldException("start date");
            return result;
        };
        return readAndCheckField(startDate,"start date", "", tempInterface);
    }

    public ZonedDateTime readAndCheckEndDate(String endDate) throws DateTimeParseException, IncorrectValueException, NullFieldException {
        FieldCheckerHelp<ZonedDateTime> tempInterface = str -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
            if (str == null || str.equals("")) {
                return null;
            } else {
                return ZonedDateTime.parse(str, formatter);
            }
        };
        return readAndCheckField(endDate,"end date", "", tempInterface);
    }

    public Position readAndCheckPos(String position) throws IncorrectValueException, NullFieldException {
        FieldCheckerHelp<Position> tempInterface = str -> {
            if (str == null || str.equals("")) {
                return null;
            } else {
                return Position.valueOf(str.toUpperCase());
            }
        };
        return readAndCheckField(position,"position", "", tempInterface);
    }

    public Long readAndCheckHeight(String height) throws NumberFormatException, IncorrectValueException, NullFieldException {
        FieldCheckerHelp<Long> tempInterface = str -> {
            Long result = null;
            if (str != null) {
                result = Long.parseLong(str);
                if (result <= 0) {
                    throw new IncorrectValueException("height", "(Reminder: Height should be more than 0.)");
                }
            }
            return result;
        };
        return readAndCheckField(height,"height", "(Reminder: Height should be more than 0.)", tempInterface);
    }

    public Integer readAndCheckWeight(String weight) throws NumberFormatException, IncorrectValueException, NullFieldException {
        FieldCheckerHelp<Integer> tempInterface = str -> {
            Integer result = null;
            if (str != null) {
                result = Integer.parseInt(str);
                if (result <= 0) {
                    throw new IncorrectValueException("weight", "(Reminder: Weight should be more than 0.)");
                }
            }
            return result;
        };
        return readAndCheckField(weight,"weight", "(Reminder: Weight should be more than 0.)", tempInterface);
    }
}
