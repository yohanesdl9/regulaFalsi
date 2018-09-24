/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodenumerik;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class TabelRegulaFalsi {
    double a, c, b, FA, FC, FB, lebar;
    String selang_baru;

    public TabelRegulaFalsi(double a, double c, double b, double FA, double FC, double FB, double lebar, String selang_baru) {
        this.a = a;
        this.c = c;
        this.b = b;
        this.FA = FA;
        this.FC = FC;
        this.FB = FB;
        this.selang_baru = selang_baru;
        this.lebar = lebar;
    }
}
