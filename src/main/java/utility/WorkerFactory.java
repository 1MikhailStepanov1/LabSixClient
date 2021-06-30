package utility;

import data.Coordinates;
import data.Person;
import data.Position;
import data.Worker;
import exceptions.IncorrectValueException;
import exceptions.NullFieldException;
import exceptions.ValidationException;


import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


/**
 * This class is for creating new instances of Worker class
 */
public class WorkerFactory {
    private Long id;
    private Console console;

    public Long getId() {
        return id;
    }

    /**
     * @param startId - start point for id counter
     */
    public WorkerFactory(Long startId) {
        this.id = startId;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public Console getConsole() {
        return console;
    }

    /**
     * Creates new worker with new id and creationDate
     *
     * @param name        - worker's name
     * @param coordinates - worker's coordinates
     * @param salary      - worker's salary
     * @param startDate   - worker's startDate
     * @param endDate     - worker's endDate
     * @param position    - worker's position
     * @param person      - worker's height and weight
     * @return woker instance
     * @throws NullFieldException      if field is null, when is shouldn't be null
     * @throws IncorrectValueException - if value of the field contains wrong data, which is not allowed in this field
     */
    public Worker createWorker(String name, Coordinates coordinates, double salary, ZonedDateTime startDate, ZonedDateTime endDate, Position position, Person person) throws NullFieldException, IncorrectValueException {
        return createWorkerWithIdAndCreationDate(++id, name, coordinates, new Date(), salary, startDate, endDate, position, person);
    }

    /**
     * Create worker with given id and creationDate
     *
     * @param _id            worker's id
     * @param name           - worker's name
     * @param coordinates    - worker's coordinates
     * @param excreationDate - worker's creationDate
     * @param salary         - worker's salary
     * @param startDate      - worker's startDate
     * @param endDate        - worker's endDate
     * @param position       - worker's position
     * @param person         - worker's height and weight
     * @return worker instance
     * @throws NullFieldException      if field is null, when is shouldn't be null
     * @throws IncorrectValueException - if value of the field contains wrong data, which is not allowed in this field
     */
    public Worker createWorkerWithIdAndCreationDate(Long _id, String name, Coordinates coordinates, Date excreationDate, double salary, ZonedDateTime startDate, ZonedDateTime endDate, Position position, Person person) throws NullFieldException, IncorrectValueException {
        if (name == null || name.length() == 0) {
            throw new NullFieldException("Name");
        }
        if (coordinates == null) {
            throw new NullFieldException("Coordinates");
        }

        if (salary <= 0) {
            throw new IncorrectValueException("Salary", "This field should be more than 0.");
        }
        if (startDate == null) {
            throw new NullFieldException("StartDate");
        }

        Instant instant = excreationDate.toInstant();
        ZonedDateTime creationDate = instant.atZone(ZoneId.systemDefault());

        return new Worker(_id, name, coordinates, creationDate, salary, startDate, endDate, position, person);
    }

    /**
     * read worker from console
     *
     * @return worker instance
     * @throws NullFieldException      if field is null, when is shouldn't be null
     * @throws IncorrectValueException - if value of the field contains wrong data, which is not allowed in this field
     */
    public Worker getWorkerFromConsole() throws IncorrectValueException, NullFieldException {
        String name = null;
        Long x = null;
        Integer y = null;
        Double salary = null;
        ZonedDateTime startDate = null;
        ZonedDateTime endDate;
        Position position;
        Long height = null;
        Integer weight = null;

        FieldCheckerForConsole fieldCheckerForConsole = new FieldCheckerForConsole(console);
        while (name == null) {
            name = fieldCheckerForConsole.readAndCheckName();
        }
        while (x == null) {
            x = fieldCheckerForConsole.readAndCheckX();
        }
        while (y == null) {
            y = fieldCheckerForConsole.readAndCheckY();
        }
        while (salary == null) {
            salary = fieldCheckerForConsole.readAndCheckSalary();
        }
        while (startDate == null) {
            startDate = fieldCheckerForConsole.readAndCheckStartDate();
        }
        endDate = fieldCheckerForConsole.readAndCheckEndDate();
        position = fieldCheckerForConsole.readAndCheckPos();
        while (height == null) {
            height = fieldCheckerForConsole.readAndCheckHeight();
        }
        while (weight == null) {
            weight = fieldCheckerForConsole.readAndCheckWeight();
        }

        return createWorker(name, new Coordinates(x, y), salary, startDate, endDate, position, new Person(height, weight));
    }

    public Worker getWorkerFromScript(String[] parameters) {
        String name;
        Long x;
        Integer y;
        Double salary;
        ZonedDateTime startDate;
        ZonedDateTime endDate;
        Position position;
        Long height;
        Integer weight;
        Worker tempWorker = null;
        FieldCheckerForScript fieldCheckerForScript = new FieldCheckerForScript();
        try {
            name = fieldCheckerForScript.readAndCheckName(parameters[0]);
        } catch (NullFieldException | IncorrectValueException e) {
            System.out.println(e.getMessage());
            return null;
        }
        try {
            x = fieldCheckerForScript.readAndCheckX(parameters[1]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Coordinate X can't be parsed.");
            return null;
        }
        try {
            y = fieldCheckerForScript.readAndCheckY(parameters[2]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Coordinate Y can't be parsed.");
            return null;
        }
        try {
            salary = fieldCheckerForScript.readAndCheckSalary(parameters[3]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Salary can't be parsed.");
            return null;
        }
        try {
            startDate = fieldCheckerForScript.readAndCheckStartDate(parameters[4]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (DateTimeParseException e) {
            System.out.println("Start date can't be parsed.");
            return null;
        }
        try {
            endDate = fieldCheckerForScript.readAndCheckEndDate(parameters[5]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (DateTimeParseException e) {
            System.out.println("End date can't be parsed.");
            return null;
        }

        try {
            position = fieldCheckerForScript.readAndCheckPos(parameters[6]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        }
        try {
            height = fieldCheckerForScript.readAndCheckHeight(parameters[7]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Height can't be parsed.");
            return null;
        }
        try {
            weight = fieldCheckerForScript.readAndCheckWeight(parameters[8]);
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Weight can't be parsed.");
            return null;
        }
        try {
            tempWorker = createWorker(name, new Coordinates(x, y), salary, startDate, endDate, position, new Person(height, weight));
        } catch (NullFieldException | IncorrectValueException e) {
            System.out.println(e.getMessage());
        }
        return tempWorker;
    }

    /**
     * Set new start point for id counter
     *
     * @param id1 - indicated start point for id counter
     */
    public void setStartId(long id1) {
        id = id1;
    }
}
