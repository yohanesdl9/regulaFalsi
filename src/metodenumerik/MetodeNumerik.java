/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodenumerik;

import java.util.Scanner;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class MetodeNumerik {
    
    public static void main(String[] args) {
        RegulaFalsi rf = new RegulaFalsi();
        Scanner sc = new Scanner(System.in);
        System.out.println("- SOLUSI PERSAMAAN NONLINIER (REGULA FALSI) -");
        System.out.println("-- Kelompok C1 Metode Numerik A STIKI 2017 --");
        char jawab;
        do {
            rf.MasukkanFungsi();
            rf.MasukkanNilai();
            System.out.print("Lagi? (Y/T) : ");
            jawab = sc.next().toUpperCase().charAt(0);
        } while (jawab == 'Y');
    }
    
}
