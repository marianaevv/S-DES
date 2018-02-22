package sdes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SDES {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        char x;
        KeyGeneration1 KG = new KeyGeneration1();
        Encryption1 enc = new Encryption1();
        String pt;
        String key;
        int[] ct = new int[8];

        do {
            System.out.print("(A) Cipher - (B) Decipher - (C) Find key given pairs");

            x = (char) System.in.read();
        } while (Character.toUpperCase(x) != 'A' && Character.toUpperCase(x) != 'B' && Character.toUpperCase(x) != 'C');

        if (Character.toUpperCase(x) == 'A') {

            System.out.print("Enter 8-bit Plaintext : ");
            pt = sc.next();

            System.out.println(" \n ");

            System.out.print("Enter 10-bit Key : ");
            key = sc.next();

            System.out.println(" \n ");

            Print1.msg("\n Key Generation ...\n");
            Print1.msg("\n---------------------------------------\n");
            KG.GenerateKeys(key);
            Print1.msg("\n---------------------------------------\n");
            ct = enc.encrypt(pt, KG.getK1(), KG.getK2());

            Print1.msg("\n---------------------------------------\n");

        } else if (Character.toUpperCase(x) == 'B') {
            System.out.println(" \n Decryption  ");

            System.out.print("Enter 8-bit Ciphertext : ");
            pt = sc.next();

            System.out.println(" \n ");

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
        } else if (Character.toUpperCase(x) == 'C') {
          
            Files a = new Files();
            String s1 = a.leerTxt("C:\\Users\\maria\\Documents\\pares.txt");
        
        System.out.println(s1);
            
        }

    }

}
