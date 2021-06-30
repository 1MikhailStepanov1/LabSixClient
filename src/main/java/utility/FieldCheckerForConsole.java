package utility;

import data.Position;
import exceptions.IncorrectValueException;
import exceptions.NullFieldException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class FieldCheckerForConsole {
    private final Console console;

    public FieldCheckerForConsole(Console console) {
        this.console = console;
    }


    public <T> T readAndCheckField(String FieldName, String error, FieldCheckerHelp<T> rule) {
        T temp = null;
        while (true) {
            System.out.println("Enter worker`s " + FieldName + ":");
            try {
                temp = rule.check(console.readln());
            } catch (NumberFormatException exception) {
                System.out.println("Input is incorrect. Please, try again." + error);
                continue;
            } catch (DateTimeParseException exception) {
                System.out.println("Format of input is incorrect. Use dd.mm.yyyy hh:mm:ss +/-hh:mm");
                continue;
            } catch (IllegalArgumentException exception) {
                System.out.println("Input doesn't contain allowed positions. Please, try again.");
                continue;
            } catch (NoSuchElementException exception) {
                continue;
            } catch (IncorrectValueException | NullFieldException e) {
                System.out.println(e.getMessage());
            }
            return temp;
        }
    }


    public String readAndCheckName() {
        FieldCheckerHelp<String> tempInterface = str -> {
            if (str == null || str.equals("")) {
                throw new NullFieldException("name");
            }
            return str;
        };
        return readAndCheckField("name", "", tempInterface);
    }

    public Long readAndCheckX() {
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
        return readAndCheckField("coordinate X", "(Reminder: Coordinate X can't be more than 768.)", tempInterface);
    }

    public Integer readAndCheckY() {
        FieldCheckerHelp<Integer> tempInterface = str -> {
            Integer result = null;
            if (str != null) {
                result = Integer.parseInt(str);
            } else throw new NullFieldException("coordinate Y");
            return result;
        };
        return readAndCheckField("coordinate Y", "", tempInterface);
    }

    public Double readAndCheckSalary() {
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
        return readAndCheckField("salary", "(Reminder: Salary should be more than 0.)", tempInterface);
    }

    public ZonedDateTime readAndCheckStartDate() {
        FieldCheckerHelp<ZonedDateTime> tempInterface = str -> {
            ZonedDateTime result = null;
            if (str != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
                result = ZonedDateTime.parse(str, formatter);
            } else throw new NullFieldException("start date");
            return result;
        };
        return readAndCheckField("start date", "", tempInterface);
    }

    public ZonedDateTime readAndCheckEndDate() {
        FieldCheckerHelp<ZonedDateTime> tempInterface = str -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
            if (str == null || str.equals("")) {
                return null;
            } else {
                return ZonedDateTime.parse(str, formatter);
            }
        };
        return readAndCheckField("end date", "", tempInterface);
    }

    public Position readAndCheckPos() {
        for (Position pos : Position.values()) {
            System.out.println(pos.toString());
        }
        FieldCheckerHelp<Position> tempInterface = str -> {
            if (str == null || str.equals("")) {
                return null;
            } else {
                return Position.valueOf(str.toUpperCase());
            }
        };
        return readAndCheckField("position", "", tempInterface);
    }

    public Long readAndCheckHeight(){
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
        return readAndCheckField("height", "(Reminder: Height should be more than 0.)", tempInterface);
    }

    public Integer readAndCheckWeight(){
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
        return readAndCheckField("weight", "(Reminder: Weight should be more than 0.)", tempInterface);
    }
}