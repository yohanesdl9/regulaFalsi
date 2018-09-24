/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodenumerik;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class RegulaFalsi {
    double[] fungsi; /* menampung koefisien tiap suku (suku ditentukan oleh indeks array) */
    ArrayList<TabelRegulaFalsi> tabRegula;
    Scanner sc = new Scanner(System.in);
    char jawab;
    int i;
    
    public void MasukkanFungsi() {
        System.out.print("Masukkan derajat fungsi polinom : ");
        int derajat = sc.nextInt();
        tabRegula = new ArrayList<>();
        fungsi = new double[derajat + 1];
        for (int j = fungsi.length - 1; j >= 0; j--){
            do{
                System.out.print("Masukkan koefisien polinom suku ke-" + j + " : ");
                fungsi[j] = sc.nextDouble();
                if (fungsi[j] == 0 && j == fungsi.length - 1){
                    System.out.println("Koefisien polinom suku tertinggi tidak boleh nol");
                }
            } while (fungsi[j] == 0 && j == fungsi.length - 1);
        }
        PrintFungsi();
    }

    private void PrintFungsi() {
        System.out.print("F(x) = ");
        for (i = fungsi.length - 1; i >= 0; i--) {
            double koefisien = fungsi[i];
            int pangkat = i;
            if (i < fungsi.length - 1) {
                System.out.print((koefisien > 1 || koefisien < -1) ? Math.abs(koefisien) : "");
            } else {
                System.out.print((koefisien > 1 || koefisien < -1) ? koefisien : (koefisien == -1) ? "-" : "");
            }
            System.out.print(tentukanPangkat(pangkat, koefisien));
            System.out.print(tentukanTanda());
        }
        System.out.println();
    }

    public void MasukkanNilai() {
        System.out.print("Masukkan selang bawah : ");
        double selang_bawah = sc.nextDouble();
        System.out.print("Masukkan selang atas : ");
        double selang_atas = sc.nextDouble();
        System.out.print("Masukkan batas lebar selang akhir iterasi : ");
        double eps1 = sc.nextDouble();
        System.out.print("Masukkan batas galat nilai fungsi hampiran : ");
        double eps2 = sc.nextDouble();
        RegulaFalsi(selang_bawah, selang_atas, eps1, eps2);
    }

    private void RegulaFalsi(double a, double b, double epsilon1, double epsilon2) {
        double FA = F(a), FB = F(b);
        String[] selang = {"[a, c]", "[c, b]"};
        double c = 0, lebar = 0;
        /* mandek_kiri dan mandek_kanan : jumlah perulangan titik ujung selang (a : kiri, b : kanan) */
        int mandek_kiri = 1, mandek_kanan = 1, r = 0;
        do {
            c = b - (FB * (b - a)) / (FB - FA);
            String slg = F(a) * F(c) < 0 ? selang[0] : selang[1];
            if (slg.equals(selang[1])){
                lebar = Math.abs(c - b);
            } else if (slg.equals(selang[0])){
                lebar = Math.abs(a - c);
            }
            tabRegula.add(new TabelRegulaFalsi(a, c, b, FA, F(c), FB, lebar, slg));
            if (Math.abs(F(c)) < epsilon2) {
                a = c;
                b = c;
            } else {
                if (F(a) * F(c) < 0) {
                    b = c; /* a,c jadi selang baru */
                    FB = F(c);
                    mandek_kiri++;
                    mandek_kanan = 0;
                    if (mandek_kiri > 1) {
                        FA /= 2; /* a menjadi titik mandek */
                    }
                } else {
                    a = c; /* c,b jadi selang baru */
                    FA = F(c);
                    mandek_kanan++;
                    mandek_kiri = 0;
                    if (mandek_kanan > 1) {
                        FB /= 2; /* b jadi titik mandek */
                    }
                }
            }
            r++;
        } while (lebar > epsilon1 || Math.abs(F(c)) > epsilon2);
        printRegulaFalsi();
        System.out.println("Hampiran akar : " + String.format("%.7f", c));
    }

    private String tentukanTanda() {
        String tanda = "";
        if (i > 0) {
            if (fungsi[i - 1] != 0){
                if (fungsi[i - 1] < 0) {
                    tanda += " - ";
                } else {
                    tanda += " + ";
                }
            }
        }
        return tanda;
    }

    private String tentukanPangkat(int pangkat, double koefisien) {
        String pemangkat = "";
        if (koefisien != 0) {
            if ((koefisien != 1 && koefisien != -1) && (pangkat >= 1)) {
                pemangkat = "*";
            }
            switch (pangkat) {
                case 0:
                    break;
                case 1:
                    pemangkat += "x";
                    break;
                default:
                    pemangkat += ("(x^" + pangkat + ")");
            }
        }
        return pemangkat;
    }

    private double F(double x) {
        double jumlah = 0;
        for (int j = fungsi.length - 1; j >= 0; j--) {
            jumlah += fungsi[j] * Math.pow(x, j);
        }
        return jumlah;
    }

    public void printRegulaFalsi() {
        System.out.println("----------------------------------------------------------------------------------------\n"
                + " r       a          c          b        F(a)       F(c)       F(b)    Slg Baru  Lebarnya \n"
                + "----------------------------------------------------------------------------------------");
        for (int j = 0; j < tabRegula.size(); j++) {
            TabelRegulaFalsi tr = tabRegula.get(j);
            System.out.printf("%2d % 10.7f % 10.7f % 10.7f% 11.7f% 11.7f% 11.7f   %s  %9.7f\n",
                    j, tr.a, tr.c, tr.b, tr.FA, tr.FC, tr.FB, tr.selang_baru, tr.lebar);
        }
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
