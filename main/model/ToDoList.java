package model;


import model.exceptions.TaskAlreadyInListException;
import model.exceptions.TaskDoesNotExistException;


import java.util.HashMap;
import java.io.*;
import java.util.*;


public class ToDoList implements Iterable<Category> {

    private Map<Task, Category> listMap;
    private List<Category> categories = new ArrayList<>();
    private int numTasks;

    public Map<Task, Category> getListMap() {
        return listMap;
    }

    //REQUIRES: valid string filename
    //MODIFIES: this
    //EFFECTS: saves all of the fields as a file with the given name
    public void save(String fileName)
            throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        //http://beginwithjava.blogspot.com/2011/04/java-file-save-and-file-load-objects.html

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        //http://beginwithjava.blogspot.com/2011/04/java-file-save-and-file-load-objects.html

        objectOutputStream.writeObject((listMap));
        objectOutputStream.writeObject(numTasks);
        objectOutputStream.writeObject(categories);

        //http://beginwithjava.blogspot.com/2011/04/java-file-save-and-file-load-objects.html

        objectOutputStream.close();
        //http://beginwithjava.blogspot.com/2011/04/java-file-save-and-file-load-objects.html
    }

    //REQUIRES: valid string filename
    //MODIFIES: this
    //EFFECTS: loads the file of the given name if it exists
    public void load(String fileName)
            throws IOException, ClassNotFoundException {

        File toDoListSaveFile = new File(fileName);
        //https://stackoverflow.com/questions/9894959/java-how-to-check-if-file-exists-and-open-it

        if (toDoListSaveFile.exists()) {
            //https://stackoverflow.com/questions/9894959/java-how-to-check-if-file-exists-and-open-it

            FileInputStream toDoListSave = new FileInputStream(toDoListSaveFile);

            ObjectInputStream toDoList = new ObjectInputStream(toDoListSave);

            listMap = (Map<Task, Category>) toDoList.readObject();
            numTasks = (int) toDoList.readObject();

            categories = (List<Category>) toDoList.readObject();

            toDoList.close();
        }
    }

    //EFFECTS: Constructs an empty ToDolist with the user's name
    public ToDoList() {
        listMap = new HashMap<>();
       // this.user = user;
        numTasks = 0;
        Category school = new Category("School");
        Category chores = new Category("Chores");
        Category errands = new Category("Errands");
        Category personal = new Category("Personal");
        Category miscellaneous = new Category("Miscellaneous");
        categories.addAll(Arrays.asList(school, chores, errands, personal, miscellaneous));
        // above code taken from
        // https://stackoverflow.com/questions/15213974/add-multiple-items-to-already-initialized-arraylist-in-java

    }

    //EFFECTS: returns true if the todolist is empty
    public boolean isEmpty() {
        return listMap.isEmpty();
    }



    // REQUIRES: valid command
    // EFFECTS: returns the category that is represented by the command
    public Category getCategory(String command) {
        if (command.equalsIgnoreCase("school")) {
            return categories.get(0);
        } else if (command.equalsIgnoreCase("chores")) {
            return categories.get(1);
        } else if (command.equalsIgnoreCase("errands")) {
            return categories.get(2);
        } else if (command.equalsIgnoreCase("personal")) {
            return categories.get(3);
        } else if (command.equalsIgnoreCase("miscellaneous")) {
            return categories.get(4);
        }
        return null;
    }


    //REQUIRES: an existing ToDoList
    //MODIFIES: this
    //EFFECTS: adds the given task and category into the hashmap
    //         If it is already in the list, throw a TaskAlreadyInListException
    public void addInList(Task task, Category category) throws TaskAlreadyInListException {
        if (listMap.containsKey(task)) {
            throw new TaskAlreadyInListException();
        } else {
            listMap.put(task, category);
            category.addTasks(task);
        }
    }

    //MODIFIES: this
    //EFFECTS: removes the given task from the list if it is in the list and complete
    //         If the task is not in the list, throws TaskDoesNotExistException
    public void removeTask(String taskName) throws TaskDoesNotExistException {
        if (null == getTask(taskName)) {
            throw new TaskDoesNotExistException();
        } else {
            Task task = getTask(taskName);
            task.getCategory().removeTasks(task);
            listMap.remove(task);
        }
    }



    //EFFECTS: produces true if the given category is in the hash set, false otherwise
    public boolean containsCategory(Category category) {
        return listMap.containsValue(category);
    }



    //EFFECTS: returns the task with the given task name
    public Task getTask(String taskName) {
        for (Map.Entry<Task, Category> entry : listMap.entrySet()) {
            Task key = entry.getKey();
            //above code taken from:
            //https://stackoverflow.com/questions/4234985/how-to-for-each-the-hashmap
            if (key.equals(new RegularTask(taskName, "Whenever",
                    new Category("Category")))) {
                return key;
            }
        }
        return null;
    }


    //EFFECTS: returns true if a given task is in the list, false if not
    public boolean containsTask(Task task) {
        return listMap.containsKey(task);
    }

    //EFFECTS: returns the number of items in the ToDolist
    public int listSize() {
        return listMap.size();
    }

    //REQUIRES: an existing ToDoList
    //EFFECTS: returns a category with the given name if it's in the ToDolLst
    public Category getCategoryFromList(Category category) {
        for (Map.Entry<Task, Category> entry : listMap.entrySet()) {
            Category value = entry.getValue();
            if (value.equals(category)) {
                return value;
            }
        }
        return null;
    }

    //EFFECTS: returns numTasks
    public int getNumTasks() {
        return numTasks;
    }



    @Override
    //EFFECTS: returns the iterator for categories
    public Iterator<Category> iterator() {
        return categories.iterator();
    }
}
