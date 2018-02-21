/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdes;

/**
 *
 * @author maria
 */
class Print1 {

    /**
     * Prints array to console *
     */
    static void array(int[] arr, int len) {
        System.out.print(" - ");

        for (int i = 0; i < len; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    static void msg(String msg) {
        System.out.print(msg);
    }
}