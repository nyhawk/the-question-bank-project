import java.util.Scanner;

/**
 * the main class from which the application will run
 */
public class Main {
    private Scanner userInp = new Scanner(System.in);
    private final Menu menu = new Menu();

    public void runApp() {
        int menuOpt;

        System.out.println("********** The Question Bank **********");

        while (true) {
           // menu.printMenu();
            /**
             * outputs the menu
             */

                System.out.println("\n Select an option \n " +
                        "1 - Add empty question bank \n 2 - Add question \n " +
                        "3 - Show question banks \n 4 - Show questions \n " +
                        "5 - Delete question bank \n 6 - Delete question \n " +
                        "7 - Take quiz \n 8 - Exit \n");

            menuOpt = userInp.nextInt();
            userInp.nextLine(); // consume newline character

            menu.manageMenu(menuOpt);

            if (menuOpt==8){
                System.exit(0);
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
