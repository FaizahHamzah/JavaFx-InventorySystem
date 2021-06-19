
package inventorysystem;

import com.sun.scenario.effect.InvertMask;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InventorySystem {
    
    static ArrayList<User> userList = new ArrayList<>();
    static ArrayList<Product> productList = new ArrayList<>();
    static ArrayList<Category> categoryList = new ArrayList<>();
    static ArrayList<Order> orderList = new ArrayList<>();
    
    public InventorySystem() throws FileNotFoundException {
        
        // read user
        File myObj = new File("user.txt");
        Scanner myReader = new Scanner(myObj);
        while(myReader.hasNextLine()) {
            String temp = myReader.nextLine();
            userList.add(new User(temp.split("\t")[0], temp.split("\t")[1]));
        }
        
        // read category
        myObj = new File("category.txt");
        myReader = new Scanner(myObj);
        while(myReader.hasNextLine()) {
            String temp = myReader.nextLine();
            categoryList.add(new Category(Integer.parseInt(temp.split("\t")[0]), temp.split("\t")[1], temp.split("\t")[2]));
        }
        
        // read product
        myObj = new File("product.txt");
        myReader = new Scanner(myObj);
        while(myReader.hasNextLine()) {
            Category category = null;
            String temp = myReader.nextLine();
            for(int i = 0; i < categoryList.size(); i++) {
                if(categoryList.get(i).getCateId() == Integer.parseInt(temp.split("\t")[6])) {
                    category = categoryList.get(i);
                }
            }
            productList.add(new Product(Integer.parseInt(temp.split("\t")[0]), temp.split("\t")[1], temp.split("\t")[2], Integer.parseInt(temp.split("\t")[3]), temp.split("\t")[4], Double.parseDouble(temp.split("\t")[5]), category));
        }
        
        
        
        // read order
        myObj = new File("order.txt");
        myReader = new Scanner(myObj);
        while(myReader.hasNextLine()) {
            String temp = myReader.nextLine();
            Product product = null;
            for(int i = 0; i < productList.size(); i++) {
                if(productList.get(i).getId() == Integer.parseInt(temp.split("\t")[4])) {
                    product = productList.get(i);
                }
            }
            Category category = null;
            for(int i = 0; i < categoryList.size(); i++) {
                if(categoryList.get(i).getCateId() == Integer.parseInt(temp.split("\t")[5])) {
                    category = categoryList.get(i);
                }
            }
            orderList.add(new Order(Integer.parseInt(temp.split("\t")[0]), temp.split("\t")[1], Integer.parseInt(temp.split("\t")[2]), temp.split("\t")[3], product, category));
        }
    }
    
    public static void login() {
        Scanner s = new Scanner(System.in);
        System.out.print("Input username: ");
        String username = s.nextLine();
        System.out.print("Input password: ");
        String password= s.nextLine();
        
        boolean flag = true;
        for(int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUsername().equals(username) && userList.get(i).getPassword().equals(password)) {
                System.out.println("Login Successfully");
                flag = false;
                break;
            }
        }
        if(flag) {
            System.out.println("Username doesn't exist");
        }
    }
    

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        InventorySystem is = new InventorySystem();
        
        new LoginFrame(userList, productList, categoryList, orderList).setVisible(true);
    }
    
}
