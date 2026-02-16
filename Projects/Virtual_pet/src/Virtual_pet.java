import java.util.Scanner;
public class Virtual_pet {
    public static void main(String[] args) {
        int energy = (int)(Math.random() * 21) + 40;;
        int hygiene = (int)(Math.random() * 21) + 40;;
        int weight = (int)(Math.random() * 21) + 40;;
        int mood = (int)(Math.random() * 21) + 40;;
        System.out.println("Choose your pet (type C for cat, or D for dog): ");
        Scanner sc = new Scanner(System.in);
        String response = sc.nextLine();
        char type = response.charAt(0);
        System.out.println("Choose your pet's name: ");
        String name = sc.nextLine();
        char state = 'a';
        int choices = 0;
        while (state == 'a') {
            System.out.println("--------------------------------");
            System.out.printf("| %-28s |\n", name);
            System.out.println("--------------------------------");
            System.out.printf("| %-22s %5d |\n", "Energy", energy);
            System.out.printf("| %-22s %5d |\n", "Hygiene", hygiene);
            System.out.printf("| %-22s %5d |\n", "Weight", weight);
            System.out.printf("| %-22s %5d |\n", "Mood", mood);
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.printf("| %-28s |\n", "Action menu");
            System.out.println("--------------------------------");
            System.out.printf("| %-22s %5s |\n", "Exit application", "0");
            System.out.printf("| %-22s %5s |\n", "Sleep", "1");
            System.out.printf("| %-22s %5s |\n", "Play", "2");
            System.out.printf("| %-22s %5s |\n", "Eat", "3");
            System.out.printf("| %-22s %5s |\n", "Clean", "4");
            System.out.println("--------------------------------");
            System.out.print("Choose an action: ");
            int action = sc.nextInt();
            if (action == 0) {
                state = 'd';
                System.out.println("Exiting application...");
            }
            switch (action) {
                case 1:
                    if (type == 'd' || type == 'D'){
                        energy += 25;
                        mood += 10;
                    }
                    else {
                        energy += 20;
                        mood += 10;
                    }
                    break;
                case 2:
                    energy -= 20;
                    mood += 20;
                    break;
                case 3:
                    energy += 10;
                    hygiene -= 15;
                    weight += 10;
                    break;
                case 4:
                    if (type == 'd' || type == 'D'){
                        energy -= 10;
                        hygiene += 25;
                        mood -= 15;
                    }
                    else {
                        energy -= 5;
                        hygiene += 25;
                        mood += 5;
                    }
                    break;
            }
            choices += 1;
            if (energy <= 0 || hygiene <= 0 || mood <= 0 || weight <= 0 || energy > 100 || hygiene > 100 || mood > 100 || weight > 100) {
                state = 'd';
                System.out.println(name + " is dead. It took you " + choices + " choices to kill it.");
            }
        }
    }
}


