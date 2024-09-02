import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Nia Hawkins
 * @version 5
 * the main class from which the application will run
 */
public class Main {
    private Scanner userInp = new Scanner(System.in);
    private final Menu menu = new Menu();

    public void runApp() {
        int menuOpt;

        System.out.println("********** The Question Bank **********");

        while (true) {
            // show menu
            menu.printMenu();
            try {
                // get user input
                menuOpt = userInp.nextInt();
                userInp.nextLine(); // consume newline character

                // execute the function selected
                menu.manageMenu(menuOpt);

                if (menuOpt==8){
                    System.exit(0);
                }

            } catch (InputMismatchException e){
                System.err.println(e);
                userInp.next();
            }
        }
    }


        /**
         * initialises the main class and runs the application
         *
         * @param args the command line arguments passed to the application
         */
        public static void main(String[]args){
            Main app = new Main();
            app.runApp();
        }
    }
