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
class Encryption1 {

    private int[] K1 = new int[8];
    private int[] K2 = new int[8];
    private int[] pt = new int[8];

    void SaveParameters(String plaintext, int[] k1, int[] k2) {
        int[] pt = new int[8];

        char c1;
        String ts;

        try {
            for (int i = 0; i < 8; i++) {
                c1 = plaintext.charAt(i);
                ts = Character.toString(c1);
                pt[i] = Integer.parseInt(ts);

                if (pt[i] != 0 && pt[i] != 1) {
                    Print1.msg("\n .. Invalid Plaintext ..");
                    System.exit(0);
                    return;
                }
            }
        } catch (Exception e) {
            Print1.msg("\n .. Invalid Plaintext .. ");
            System.exit(0);
            return;

        }

        this.pt = pt;

        Print1.msg("Plaintext array : ");
        Print1.array(this.pt, 8);
        Print1.msg("\n");

        this.K1 = k1;
        this.K2 = k2;

    ;
    }

    void InitialPermutation() {
        int[] temp = new int[8];

        temp[0] = pt[1];
        temp[1] = pt[5];
        temp[2] = pt[2];
        temp[3] = pt[0];
        temp[4] = pt[3];
        temp[5] = pt[7];
        temp[6] = pt[4];
        temp[7] = pt[6];

        pt = temp;

        Print1.msg("Initial Permutaion(IP) : ");
        Print1.array(this.pt, 8);
        Print1.msg("\n");

    }

    void InverseInitialPermutation() {
        int[] temp = new int[8];

        temp[0] = pt[3];
        temp[1] = pt[0];
        temp[2] = pt[2];
        temp[3] = pt[4];
        temp[4] = pt[6];
        temp[5] = pt[1];
        temp[6] = pt[7];
        temp[7] = pt[5];

        pt = temp;

    }


    int[] mappingF(int[] R, int[] SK) {
        int[] temp = new int[8];

 
        temp[0] = R[3];
        temp[1] = R[0];
        temp[2] = R[1];
        temp[3] = R[2];
        temp[4] = R[1];
        temp[5] = R[2];
        temp[6] = R[3];
        temp[7] = R[0];

        Print1.msg("EXPANSION/PERMUTATION on RH : ");
        Print1.array(temp, 8);
        Print1.msg("\n");

    
        temp[0] = temp[0] ^ SK[0];
        temp[1] = temp[1] ^ SK[1];
        temp[2] = temp[2] ^ SK[2];
        temp[3] = temp[3] ^ SK[3];
        temp[4] = temp[4] ^ SK[4];
        temp[5] = temp[5] ^ SK[5];
        temp[6] = temp[6] ^ SK[6];
        temp[7] = temp[7] ^ SK[7];

        Print1.msg("XOR With Key : ");
        Print1.array(temp, 8);
        Print1.msg("\n");

      
        final int[][] S0 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}};
        final int[][] S1 = {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};

        int d11 = temp[0];  
        int d14 = temp[3]; 

        int row1 = BinaryOp1.BinToDec(d11, d14); 

        int d12 = temp[1]; 
        int d13 = temp[2];   
        int col1 = BinaryOp1.BinToDec(d12, d13); 

        int o1 = S0[row1][col1];

        int[] out1 = BinaryOp1.DecToBinArr(o1);

        Print1.msg("S-Box S0: ");
        Print1.array(out1, 2);
        Print1.msg("\n");

        int d21 = temp[4];
        int d24 = temp[7]; 
        int row2 = BinaryOp1.BinToDec(d21, d24);

        int d22 = temp[5]; 
        int d23 = temp[6]; 
        int col2 = BinaryOp1.BinToDec(d22, d23);

        int o2 = S1[row2][col2];

        int[] out2 = BinaryOp1.DecToBinArr(o2);

        Print1.msg("S-Box S1: ");
        Print1.array(out2, 2);
        Print1.msg("\n");

        int[] out = new int[4];
        out[0] = out1[0];
        out[1] = out1[1];
        out[2] = out2[0];
        out[3] = out2[1];

	
        int[] O_Per = new int[4];
        O_Per[0] = out[1];
        O_Per[1] = out[3];
        O_Per[2] = out[2];
        O_Per[3] = out[0];

        Print1.msg("Output of mappingF : ");
        Print1.array(O_Per, 4);
        Print1.msg("\n");

        return O_Per;
    }

    /**
     * fK(L, R, SK) = (L (XOR) mappingF(R, SK), R) .. regresa 8-bit de salida*
     */
    int[] functionFk(int[] L, int[] R, int[] SK) {
        int[] temp = new int[4];
        int[] out = new int[8];

        temp = mappingF(R, SK);

        //XOR left half with output of mappingF 
        out[0] = L[0] ^ temp[0];
        out[1] = L[1] ^ temp[1];
        out[2] = L[2] ^ temp[2];
        out[3] = L[3] ^ temp[3];

        out[4] = R[0];
        out[5] = R[1];
        out[6] = R[2];
        out[7] = R[3];

        return out;

    }

    /**
     * Funcion switch (SW) cambia los los 4 bits izquierdos y derechos
     */
    int[] switchSW(int[] in) {

        int[] temp = new int[8];

        temp[0] = in[4];
        temp[1] = in[5];
        temp[2] = in[6];
        temp[3] = in[7];

        temp[4] = in[0];
        temp[5] = in[1];
        temp[6] = in[2];
        temp[7] = in[3];

        return temp;
    }

    int[] encrypt(String plaintext, int[] LK, int[] RK) {

        SaveParameters(plaintext, LK, RK);

        Print1.msg("\n---------------------------------------\n");
        InitialPermutation();
        Print1.msg("\n---------------------------------------\n");
       
        int[] LH = new int[4];
        int[] RH = new int[4];
        LH[0] = pt[0];
        LH[1] = pt[1];
        LH[2] = pt[2];
        LH[3] = pt[3];

        RH[0] = pt[4];
        RH[1] = pt[5];
        RH[2] = pt[6];
        RH[3] = pt[7];

        Print1.msg("First Round LH : ");
        Print1.array(LH, 4);
        Print1.msg("\n");

        Print1.msg("First Round RH: ");
        Print1.array(RH, 4);
        Print1.msg("\n");

        //Primer ronda con la primer subllave
        int[] r1 = new int[8];
        r1 = functionFk(LH, RH, K1);

        Print1.msg("After First Round : ");
        Print1.array(r1, 8);
        Print1.msg("\n");
        Print1.msg("\n---------------------------------------\n");
        
        //Cambia las salidas izquierda y derecha
        int[] temp = new int[8];
        temp = switchSW(r1);

        Print1.msg("After Switch Function : ");
        Print1.array(temp, 8);
        Print1.msg("\n");
        Print1.msg("\n---------------------------------------\n");
       
        //separa otra vez la mitad izquiera y la mitad derecha para la segunda ronda
        LH[0] = temp[0];
        LH[1] = temp[1];
        LH[2] = temp[2];
        LH[3] = temp[3];

        RH[0] = temp[4];
        RH[1] = temp[5];
        RH[2] = temp[6];
        RH[3] = temp[7];

        Print1.msg("Second Round LH : ");
        Print1.array(LH, 4);
        Print1.msg("\n");

        Print1.msg("Second Round RH: ");
        Print1.array(RH, 4);
        Print1.msg("\n");

        //segunda ronda con la subllave 2
        int[] r2 = new int[8];
        r2 = functionFk(LH, RH, K2);

        pt = r2;

        Print1.msg("After Second Round : ");
        Print1.array(this.pt, 8);
        Print1.msg("\n");
        Print1.msg("\n---------------------------------------\n");

        InverseInitialPermutation();

        Print1.msg("After Inverse IP (Result) : ");
        Print1.array(this.pt, 8);
        Print1.msg("\n");

        //Termina la encripción y regresa los 8 bit de salida
        return pt;

    }

}