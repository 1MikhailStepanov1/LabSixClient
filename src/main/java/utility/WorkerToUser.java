package utility;

import data.Worker;

import java.time.format.DateTimeFormatter;

/**
 * This class is used for describing the Worker class instance
 */
public class WorkerToUser {
    /**
     * Describe worker in console
     * @param worker - worker class instance to be described
     */
    public void workerToConsole(Worker worker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
        System.out.println();
        System.out.println("Worker");
        System.out.println("Id: " + worker.getId());
        System.out.println("Name: " + worker.getName());
        System.out.println("Coordinates: X-" + worker.getCoordinates().getCoordinateX() + " Y-" + worker.getCoordinates().getCoordinateY());
        System.out.println("Creation Date: " + worker.getCreationDate().format(formatter));
        System.out.println("Salary: " + worker.getSalary());
        System.out.println("Start Date: " + worker.getStartDate().format(formatter));
        if (worker.getEndDate() == null){
            System.out.println("End Date: empty");
        } else {System.out.println("End Date: " + worker.getEndDate().format(formatter));}
        if (worker.getPosition() == null){
            System.out.println("Position: empty");
        } else {System.out.println("Position: " + worker.getPosition());}
        System.out.print("Person:");
        if (worker.getPerson().getHeight()==null){
            System.out.print("Height - empty");
        } else {System.out.print("Height - " + worker.getPerson().getHeight() + " ");}
        if (worker.getPerson().getWeight() == null){
            System.out.println("Weight - empty");
        } else {System.out.println("Weight - "+ worker.getPerson().getWeight());
        }
    }
}
