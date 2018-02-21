package sdes;

import java.io.IOException;
import java.util.*;

public class SDES {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        char x;

        //Enter text or word
        //you choose whether to cipher or decipher
        do {
            System.out.print("(A) Cipher - (B) Decipher");

            x = (char) System.in.read();
        } while (Character.toUpperCase(x) != 'A' && Character.toUpperCase(x) != 'B');

        if (Character.toUpperCase(x) == 'A') {
            SDES s = new SDES();
        } else if (Character.toUpperCase(x) == 'B') {

        }
        KeyGeneration1 KG = new KeyGeneration1();
        Encryption1 enc = new Encryption1();
       // Scanner sc = new Scanner(System.in);

        String pt;
        String key;
        int[] ct = new int[8];

        try {

            //Ex Input : 10101010
            System.out.print("Enter 8-bit Plaintext : ");
            pt = sc.next();

            System.out.println(" \n ");

            //Ex Input : 1010000010  			
            System.out.print("Enter 10-bit Key : ");
            key = sc.next();

            System.out.println(" \n ");

            Print1.msg("\n Key Generation ...\n");
            Print1.msg("\n---------------------------------------\n");
            KG.GenerateKeys(key);
            Print1.msg("\n---------------------------------------\n");
            ct = enc.encrypt(pt, KG.getK1(), KG.getK2());

            Print1.msg("\n---------------------------------------\n");
            System.out.println(" \n Decryption  ");

            //Ex Input : 10001101
            System.out.print("Enter 8-bit Ciphertext : ");
            pt = sc.next();

            System.out.println(" \n ");

            //Ex Input : 1010000010  			
            System.out.print("Enter 10-bit Key : ");
            key = sc.next();

            System.out.println(" \n ");

            Print1.msg("\n Key Generation ...\n");
            Print1.msg("\n---------------------------------------\n");
            Print1.msg("\n For decryption Two Sub-keys will be used in reverse order \n");
            Print1.msg("\n---------------------------------------\n\n");
            KG.GenerateKeys(key);
            Print1.msg("\n---------------------------------------\n");

            ct = enc.encrypt(pt, KG.getK2(), KG.getK1());

            Print1.msg("\n---------------------------------------\n");

        } catch (InputMismatchException e) {
            System.out.println("-- Error Occured : Invalid Input ");
        } catch (Exception e) {
            System.out.println("-- Error Occured : " + e);
        }

    }

}

/**
 * Class to print Strings & arrays shortly *
 */

