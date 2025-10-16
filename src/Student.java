public class Student {
    private int id;
    private String name;
    private int age;
    private boolean status;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.status = true; //initially true
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStatus(String status){
        if(status.equals("active")){
            this.status = true;
        }else{
            this.status = false;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age + " | Status: " + (status ? "Active" : "Inactive");
    }
}
