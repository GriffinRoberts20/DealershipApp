package com.pluralsight;

import java.util.Scanner;

public class MyUtils {
    static Scanner input=new Scanner(System.in);
    //prints n character long "-" text divider
    public static void printDivider(int n){
        System.out.println("-".repeat(Math.max(0, n)));
    }
    public static void printCustomDivider(String divider,int n){
        System.out.println(divider.repeat(Math.max(0, n)));
    }
    //input the question you want answered, returns answer, expects string
    public static String askQuestionGetString(String q){
        System.out.print(q);
        return input.nextLine();
    }
    //input the question you want answered, returns answer, cannot be empty, expects String
    public static String askQuestionGetStringFull(String q){
        String answer;
        while(true) {
            System.out.print(q);
            answer=input.nextLine().trim();
            if(!answer.isEmpty()){
                break;
            }
            System.out.println("Input cannot be blank.");
        }
        return answer;
    }
    //input the question you want answered, returns answer, expects integer
    public static int askQuestionGetInt(String q){
        boolean asking=true;
        int answer=-1;
        while(asking){
            try{
                System.out.print(q);
                answer = Integer.parseInt(input.nextLine());
                asking=false;
            } catch (Exception e) {
                printDivider(30);
                System.out.println("Error: must enter a whole number.");
                printDivider(30);
            }
        }
        return answer;
    }
    public static float askQuestionGetFloat(String q){
        boolean asking=true;
        float answer=-1;
        while(asking){
            try{
                System.out.print(q);
                answer = Float.parseFloat(input.nextLine());
                asking=false;
            } catch (Exception e) {
                printDivider(30);
                System.out.println("Error: must enter a number.");
                printDivider(30);
            }
        }
        return answer;
    }
    public static double askQuestionGetDouble(String q){
        boolean asking=true;
        double answer=-1;
        while(asking){
            try{
                System.out.print(q);
                answer = Double.parseDouble(input.nextLine());
                asking=false;
            } catch (Exception e) {
                printDivider(30);
                System.out.println("Error: must enter a number.");
                printDivider(30);
            }
        }
        return answer;
    }

    public static void clearTerminal(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void pressEnterToContinue(){
        System.out.print("Press enter to continue.");
        input.nextLine();
    }
}
