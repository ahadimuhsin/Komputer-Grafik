/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidangdantransform;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import javax.swing.JPanel;


public class SistemUtama extends JPanel {

   //----- variables -----
    int h = 480, w = 480, id, grid = 20;
    int x1Axis, y1Axis, x2Axis, y2Axis, x3Axis, y3Axis, x4Axis, y4Axis;
    int triX[], triY[], triXR[], triYR[], reX[], reY[], reXR[], reYR[], dX, dY, deg, shear;
    int cs, xmin, ymin, xmax, ymax;
    int poX[], poY[], liX[], liY[], elX[], elY[];
    int xT, yT;
    float scale;
    
    int x1rAxis, y1rAxis, x2rAxis, y2rAxis, x3rAxis, y3rAxis, x4rAxis, y4rAxis;
    
    //persegi
    int sb_x, xTrans, yTrans, xRotasi, yRotasi, sb_y, ukuranPersegi, pilih;
    int x1Tr, x2Tr, x3Tr, x4Tr, y1Tr, y2Tr, y3Tr, y4Tr;
    float sudut, kali, sh_x, sh_y;
    
    //segitiga
    int x1T, x2T, x3T, x4T,y1T, y2T, y3T, y4T, x1S, x2S, x3S, y1S, y2S, y3S;
    int [] xTs, yTs;
    
    int sd_x1, sd_y1, shearX, shearY;
    int[] sbx,sby,sbxR, sbyR, X_ganti, Y_ganti;
    
    //----- constructors -----
    public SistemUtama() {
    }
     //titik garis
    public SistemUtama(int x1, int y1, int x2, int y2, int id) {
        x1Axis = x1;
        y1Axis = y1;
        x2Axis = dX = x2;
        y2Axis = dY = y2;
        this.id = id;
    }

    //segitiga
    public SistemUtama(int x[], int y[], int id, int x1, int x2, int x3, int y1, int y2, int y3) {
        x1Axis = x1;
        y1Axis = y1;
        x2Axis = x2;
        y2Axis = y2;
        x3Axis = x3;
        y3Axis = y3;
        liX = triX = reX = elX = x;
        liY = triY = reY = elY = y;
        this.id = id;

    }
    
    //Clipping
    public SistemUtama(int x[], int y[], int id) {
        liX = triX = reX = elX = x;
        liY = triY = reY = elY = y;
        this.id = id;
    }
    
    public SistemUtama(int x[], int y[], int c[], int id, int cs) {
        poX = liX = triX = reX = elX = x;
        poY = liY = triY = reY = elY = y;
        xmin = c[0];
        xmax = c[1];
        ymin = c[2];
        ymax = c[3];
        this.id = id;
        this.cs = cs;
    }
    
    //translasi segitiga
     public SistemUtama(int x[], int y[], int xTrs[], int yTrs[],int id, int x1, int x2, int x3, int y1, int y2, int y3, int xT, int yT, int a) {
         x1Axis = x1;
         x2Axis = x2;
         x3Axis = x3;
         y1Axis = y1;
         y2Axis = y2;
         y3Axis = y3;
         
         x1T= x1+xT;
         x2T= x2+xT;
         x3T= x3+xT;
         y1T= y1+yT;
         y2T= y2+yT;
         y3T= y3+yT;
         
         
        liX = triX = reX = elX = x;
        liY = triY = reY = elY = y;
        xTs = xTrs;
        yTs = yTrs;
        this.id = id;
        
    }
    
     //segitiga refleksi, dilatasi, shear, dan rotasi
    public SistemUtama(int x1, int x2, int x3, int y1, int y2, int y3, float a, int d) {
         
        int x[] = {x1 * grid + (480/2), x2 * grid + (480/2), x3 * grid + (480/2)};
        int y[] = {(480/2) - grid * y1, (480/2) - grid * y2, (480/2) - grid * y3};
        int[] xR = {(w/2) + (int) (x1 * a * grid), (w/2)+ (int) (x2 * a * grid), (w/2) + (int) (x3 * a * grid)};
        int[] yR = {(w/2) - (int) (y1 * a * grid), (w/2) - (int) (y2 * a * grid), (w/2)- (int) (y3 * a * grid)};
       
        sbx = x;
        sby = y;
        sbxR = xR;
        sbyR = yR;
        sd_x1 = (w/2) + (x1 * grid); //digunakan sbg titik acuan
        sd_y1 = (w/2) - (y1 * grid); //digunakan sbg titik acuan
        sudut = (int) a;
        shearX = (int) a; //digunakan sbg shear X
        shearY = (int) a; //digunakan sbg shear Y
        Y_ganti = new int[3];   //digunakan sbg dilatasi
        X_ganti = new int[3];
        X_ganti[0] = x[0];
        Y_ganti[0] = y[0];
        X_ganti[1] = (x[0] - (int) ((x[0] - x[1]) * a));
        Y_ganti[1] = (y[0] - (int) ((y[0] - y[1]) * a));
        X_ganti[2] = (x[0] - (int) ((x[0] - x[2]) * a));
        Y_ganti[2] = (y[0] - (int) ((y[0] - y[2]) * a));
        
        //untuk segitiga
        x1Axis = x1;
         x2Axis = x2;
         x3Axis = x3;
         y1Axis = y1;
         y2Axis = y2;
         y3Axis = y3;
        
        //untuk segitiga refleksi 
        x1T= (int)(x1*a);
         x2T= (int)(x2*a);
         x3T= (int)(x3*a);
         y1T= (int)(y1*a);
         y2T= (int)(y2*a);
         y3T= (int)(y3*a);
         
         x1S = x1;
         y1S = y1;
         x2S = (int) (x1 - ((x1-x2)*a));
         y2S = (int) (y1 - ((y1-y2)*a));
         x3S = (int) (x1 - ((x1-x3)*a));
         y3S = (int) (y1 - ((y1-y3)*a));
         
        System.out.println(X_ganti[0]+" "+X_ganti[1]+" "+X_ganti[2]+" "+Y_ganti[0]+" "+Y_ganti[1]+" "+Y_ganti[2]);
        this.id = d;
    }
    
    
     //konstruktor untuk membuat persegi
    public SistemUtama(int x, int y, int sisi, int ind) {
        sb_x = x;
        sb_y = y;
        ukuranPersegi = sisi;
        x1Axis = x;
         x2Axis = x+sisi;
         x3Axis = x+sisi;
         x4Axis = x;
         y1Axis = y;
         y2Axis = y;
         y3Axis = y-sisi;
         y4Axis = y-sisi;
        this.id = ind;
    }

    //konstruktor untuk dilatasi, rotasi, dan shear persegi
    public SistemUtama(int x, int y, float b, int sisi, int ind) {
        sb_x = x;
        sb_y = y;
        kali = b;   //utk pengali
        sudut = b;  //utk sudut rotasi
        sh_x = b;   //utk shear X
        sh_y = b;   //utk shear Y
        ukuranPersegi = sisi;
        
        x1Axis = x;
         x2Axis = x+sisi;
         x3Axis = x+sisi;
         x4Axis = x;
         y1Axis = y;
         y2Axis = y;
         y3Axis = y-sisi;
         y4Axis = y-sisi;
         
         
        x1T = x;
        x2T = x+sisi;
        x3T = x+sisi;
        x4T = x;
        y1T = y;
        y2T = y;
        y3T = y-sisi;
        y4T = y-sisi;
        this.id = ind;
    }

    //konstruktor untuk translasi persegi
    public SistemUtama(int x, int y, int xT, int yT, int sisi, int ind) {
        sb_x = x;
        sb_y = y;
        xTrans = xT;
        yTrans = yT;
        ukuranPersegi = sisi;
        
        x1Axis = x;
         x2Axis = x+sisi;
         x3Axis = x+sisi;
         x4Axis = x;
         y1Axis = y;
         y2Axis = y;
         y3Axis = y-sisi;
         y4Axis = y-sisi;
         
        x1Tr = x+xT;
        x2Tr = x+sisi+xT;
        x3Tr = x+sisi+xT;
        x4Tr = x+xT;
        y1Tr = y+yT;
        y2Tr = y+yT;
        y3Tr = y-sisi+yT;
        y4Tr = y-sisi+yT;
        this.id = ind;
    }
     

     
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.white);
        //----- cartesian canvas -----
        Graphics2D gr = (Graphics2D) g;
        for (int x = 0; x <= w; x += grid) {
            g.setColor(Color.gray);
            g.drawLine(x, 0, x, h);//x1 y1 x2 y2
            g.drawLine(0, x, w, x);
            
            if (x <= w / 2 && x / grid != 0) {
                gr.setColor(Color.BLACK);
                gr.drawString("" + x / grid, w / 2 + x, h / 2 + 15);
                gr.drawString("" + x / grid, w / 2 - 15, h / 2 - x);
                gr.drawString("" + (-x / grid), w / 2 - 19, h / 2 + 10 + x);
                gr.drawString("" + (-x / grid), w / 2 - x, h / 2 + 15);
                
            }
        }
        
        gr.setColor(Color.BLACK);
        gr.setStroke(new BasicStroke(2));
        gr.drawLine(0, h / 2, w, h / 2);
        gr.drawLine(w / 2, 0, w / 2, h);
        
        AffineTransform af = new AffineTransform();
        switch (id) {
            //----- point and line -----
            case 1:
                Point(gr, x1Axis, y1Axis, x2Axis, y2Axis);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                break;
            case 2:
                Line(gr, x1Axis, y1Axis, x2Axis, y2Axis);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                break;
             case 5:
                 //bikin rectangle
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
                break;
                
            //persegi translasi
            case 6:
                //persegi sebelumnya
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
                
                //persegi translasi
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + ((sb_x + xTrans) * grid) - (ukuranPersegi / 2)+2, (w/2) - ((sb_y + yTrans) * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + ((sb_x + xTrans) * grid), (w/2) - ((sb_y + yTrans) * grid), 5, 5);
                gr.drawString("A'("+x1Tr+","+y1Tr+")",(w/2)+x1Tr*grid,(h/2)-(y1Tr*grid)-5);
                gr.drawString("B'("+x2Tr+","+y2Tr+")",(w/2)+x2Tr*grid,(h/2)-(y2Tr*grid)-5);
                gr.drawString("C'("+x3Tr+","+y3Tr+")",(w/2)+x3Tr*grid,(h/2)-(y3Tr*grid)-5);
                gr.drawString("D'("+x4Tr+","+y4Tr+")",(w/2)+x4Tr*grid,(h/2)-(y4Tr*grid)-5);

            //persegi rotasi
            case 7:
                //persegi sebelumnya
                System.out.println(sb_x+" "+sb_y+" "+ukuranPersegi);
                gr.setColor(Color.BLUE);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
                
                //membuat rotasi
                //membuat bangun persegi
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                gr.rotate(Math.toRadians(sudut), (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2)- (sb_y * grid) - (ukuranPersegi / 2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                //membuat titik
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                
                gr.drawString("A'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("B'", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("D'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                gr.drawString("C'", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                break;
            //segitiga
            case 8:
                gr.drawPolygon(triX, triY, triX.length);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                break;
            //segitiga translasi
            case 9:
                gr.drawPolygon(triX, triY, triX.length);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                
                gr.setColor(Color.BLACK);
                gr.drawPolygon(xTs, yTs, xTs.length);
                gr.drawString("A("+x1T+","+y1T+")",(w/2)+x1T*grid,(h/2)-(y1T*grid)-5);
                gr.drawString("B("+x2T+","+y2T+")",(w/2)+x2T*grid,(h/2)-(y2T*grid)-5);
                gr.drawString("C("+x3T+","+y3T+")",(w/2)+x3T*grid,(h/2)-(y3T*grid)-5);
                break;    

            //persegi scala
            case 12:
                gr.setColor(Color.BLUE);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
                
                //membuat dilatasi
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, (int)(ukuranPersegi * kali * grid), (int) (ukuranPersegi * kali * grid));
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("B'", ((w/2) + ((sb_x + ukuranPersegi * kali) * grid) - (ukuranPersegi * kali / 2)), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("D'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), ((w/2) - ((sb_y - ukuranPersegi * kali) * grid) - (ukuranPersegi * kali / 2)));
                gr.drawString("C'", ((w/2) + ((sb_x + ukuranPersegi * kali) * grid) - (ukuranPersegi * kali / 2)), ((w/2) - ((sb_y - ukuranPersegi * kali) * grid) - (ukuranPersegi * kali / 2)));
                break;
                
            //persegi shear x dan y    
            // X
            case 16:
                //muncul persegi sebelumnya
                gr.setColor(Color.BLUE);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                //membentuk titik
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid) +2, (w/2) - (sb_y * grid) +2, 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
                
                //membuat shear X
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                AffineTransform identitasX = new AffineTransform();
                identitasX.shear(sh_x, 0);
                gr.setTransform(identitasX);
                gr.translate(-((w/2) - (sb_y * grid) - (ukuranPersegi / 2)) * sh_x, 0);
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                //membentuk titik
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid) +2, (w/2) - (sb_y * grid) +2, 5, 5);
                
                gr.drawString("A'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("B'", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("D'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                gr.drawString("C'", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                break;
             
            // Y    
            case 17:
                //muncul persegi sebelumnya
                 gr.setColor(Color.BLUE);
                 gr.setStroke(new BasicStroke(2));
                 g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2), ukuranPersegi * grid, ukuranPersegi * grid);
                 //membentuk titik
                 gr.setColor(Color.BLACK);
                 gr.setStroke(new BasicStroke(3));
                 g.drawOval((w/2) + (sb_x * grid) - 5, (w/2) - (sb_y * grid) - 5, 5, 5);
                 gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
                 
                 //membuat shear Y
                 gr.setColor(Color.BLACK);
                 gr.setStroke(new BasicStroke(2));
                 AffineTransform identitasY = new AffineTransform();
                 identitasY.shear(0, sh_y);
                 gr.setTransform(identitasY);
                 gr.translate(0, -((w/2) + (sb_x * grid) - (ukuranPersegi / 2)) * sh_y);
                 g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2), ukuranPersegi * grid, ukuranPersegi * grid);
                 //membentuk titik
                 gr.setColor(Color.BLACK);
                 gr.setStroke(new BasicStroke(3));
                 g.drawOval((w/2) + (sb_x * grid) - 5, (w/2) - (sb_y * grid) - 5, 5, 5);
                 
                gr.drawString("A'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("B'", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("D'", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                gr.drawString("C'", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                break;
                
            //persegi refleksi sumbu x
            case 18:
                //muncul persegi sebelumnya
                gr.setColor(Color.BLUE);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2) +2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
            
                //membuat refleksi sb x
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                gr.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2)+2, (w/2) - ((-sb_y + ukuranPersegi) * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (-sb_y * grid), 5, 5);
                gr.drawString("A'("+x1Axis+","+(-y1Axis)+")", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (-sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("B'("+x2Axis+","+(-y2Axis)+")", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (-sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("D'("+x4Axis+","+(-y4Axis)+")", (w/2) + (sb_x * grid) - (ukuranPersegi / 2), (w/2) - (-(sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                gr.drawString("C'("+x3Axis+","+(-y3Axis)+")", (w/2) + ((sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (-(sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                
                break;
                
             //persegi refleksi sumbu y
            case 19:
                    //muncul persegi sebelumnya
                gr.setColor(Color.BLUE);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2) +2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
            
                //membuat refleksi sb y
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + ((-sb_x - ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid+2, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (-sb_x * grid) - 5, (w/2) - (sb_y * grid) - 5, 5, 5);
                gr.drawString("A'("+(-x1Axis)+","+y1Axis+")", (w/2) + (-sb_x * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("B'("+(-x2Axis)+","+y2Axis+")", (w/2) + (-(sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("D'("+(-x4Axis)+","+y4Axis+")", (w/2) + (-sb_x * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                gr.drawString("C'("+(-x3Axis)+","+y3Axis+")", (w/2) + (-(sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - ((sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));                
                break;
                
             //persegi refleksi titik pusat
            case 20:
                //muncul persegi sebelumnya
                gr.setColor(Color.BLUE);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + (sb_x * grid) - (ukuranPersegi / 2) +2, (w/2) - (sb_y * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2) + (sb_x * grid), (w/2) - (sb_y * grid), 5, 5);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                gr.drawString("D("+x4Axis+","+y4Axis+")",(w/2)+x4Axis*grid,(h/2)-(y4Axis*grid)-5);
            
                //membuat refleksi titik pusat
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(2));
                g.drawRect((w/2) + ((-sb_x - ukuranPersegi) * grid) - (ukuranPersegi / 2)+2, (w/2) - ((-sb_y + ukuranPersegi) * grid) - (ukuranPersegi / 2)+2, ukuranPersegi * grid, ukuranPersegi * grid);
                gr.setColor(Color.BLACK);
                gr.setStroke(new BasicStroke(3));
                g.drawOval((w/2)+ (-sb_x * grid) - 5, (w/2) - (-sb_y * grid) - 5, 5, 5);
                gr.drawString("A'("+(-x1Axis)+","+(-y1Axis)+")", (w/2) + (-sb_x * grid) - (ukuranPersegi / 2), (w/2) - (-sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("B'("+(-x2Axis)+","+(-y2Axis)+")", (w/2) + (-(sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (-sb_y * grid) - (ukuranPersegi / 2));
                gr.drawString("D'("+(-x4Axis)+","+(-y4Axis)+")", (w/2) + (-sb_x * grid) - (ukuranPersegi / 2), (w/2) - (-(sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));
                gr.drawString("C'("+(-x3Axis)+","+(-y3Axis)+")", (w/2) + (-(sb_x + ukuranPersegi) * grid) - (ukuranPersegi / 2), (w/2) - (-(sb_y - ukuranPersegi) * grid) - (ukuranPersegi / 2));                  
                break;
            
            //segitiga scala    
            case 21:
                //membuat segitiga awal
                gr.setStroke(new BasicStroke(2));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                //membuat segitiga dilatasi
                gr.setColor(Color.BLACK);
                g.drawPolygon(X_ganti, Y_ganti, 3);
                gr.drawString("A'("+x1S+","+y1S+")",(w/2)+x1S*grid,(h/2)-(y1S*grid)-5);
                gr.drawString("B'("+x2S+","+y2S+")",(w/2)+x2S*grid,(h/2)-(y2S*grid)-5);
                gr.drawString("C'("+x3S+","+y3S+")",(w/2)+x3S*grid,(h/2)-(y3S*grid)-5);
                break;
                
            //segitiga rotate    
            case 22:
                //membuat segitiga awal
                gr.setStroke(new BasicStroke(2));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                //membuat segitiga rotasi
                gr.setStroke(new BasicStroke(2));
                gr.setColor(Color.BLACK);
                gr.rotate(Math.toRadians(sudut), sd_x1, sd_y1);
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A'",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B'",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C'",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                break;
                
            //segitiga shear x
            case 23:
                //membuat segitiga awal
                gr.setStroke(new BasicStroke(2));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                //membuat segitiga shearX
                g.setColor(Color.BLUE);
                AffineTransform identitas = new AffineTransform();
                identitas.shear(-shearX, 0);
                gr.setTransform(identitas);
                gr.translate((-sd_y1) * (-shearX), 0);
                g.drawPolygon(sbx, sby, 3);  
                gr.drawString("A'",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B'",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C'",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                break;
                
            //segitiga shear y    
            case 24:
                //membuat segitiga awal
                gr.setStroke(new BasicStroke(2));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                //membuat segitiga shearY
                gr.setColor(Color.BLUE);
                AffineTransform identitas1 = new AffineTransform();
                identitas1.shear(0, -shearY);
                gr.setTransform(identitas1);
                gr.translate(0, (-sd_x1) * (-shearY));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A'",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B'",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C'",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                break;
                
            //segitiga refleksi x
            case 25:
                //membuat segitiga awal
                gr.setStroke(new BasicStroke(2));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                //membuat segitiga refleksi sb x
                gr.setStroke(new BasicStroke(2));
                gr.setColor(Color.BLUE);
                g.drawPolygon(sbx, sbyR, 3);
                gr.drawString("A'("+x1Axis+","+y1T+")",(w/2)+x1Axis*grid,(h/2)-(y1T*grid)-5);
                gr.drawString("B'("+x2Axis+","+y2T+")",(w/2)+x2Axis*grid,(h/2)-(y2T*grid)-5);
                gr.drawString("C'("+x3Axis+","+y3T+")",(w/2)+x3Axis*grid,(h/2)-(y3T*grid)-5);
                break;
            
             //segitiga refleksi y
            case 26:
                //membuat segitiga awal
                gr.setStroke(new BasicStroke(2));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                //membuat segitiga refleksi sb y
                gr.setStroke(new BasicStroke(2));
                gr.setColor(Color.BLUE);
                g.drawPolygon(sbxR, sby, 3);
                gr.drawString("A'("+x1T+","+y1Axis+")",(w/2)+x1T*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B'("+x2T+","+y2Axis+")",(w/2)+x2T*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C'("+x3T+","+y3Axis+")",(w/2)+x3T*grid,(h/2)-(y3Axis*grid)-5);
                break; 
                
            //segitiga refleksi titik pusat
            case 27:
                //membuat segitiga awal
                gr.setStroke(new BasicStroke(2));
                g.drawPolygon(sbx, sby, 3);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("B("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                gr.drawString("C("+x3Axis+","+y3Axis+")",(w/2)+x3Axis*grid,(h/2)-(y3Axis*grid)-5);
                //membuat segitiga refleksi titik pusat
                gr.setStroke(new BasicStroke(2));
                gr.setColor(Color.BLACK);
                g.drawPolygon(sbxR, sbyR, 3);
                gr.drawString("A'("+x1T+","+y1T+")",(w/2)+x1T*grid,(h/2)-(y1T*grid)-5);
                gr.drawString("B'("+x2T+","+y2T+")",(w/2)+x2T*grid,(h/2)-(y2T*grid)-5);
                gr.drawString("C'("+x3T+","+y3T+")",(w/2)+x3T*grid,(h/2)-(y3T*grid)-5);
                break;
            case 28:
                gr.setColor(Color.BLUE);
                gr.setColor(Color.BLACK);
                Line(gr, x1Axis, y1Axis, x2Axis, y2Axis);
                gr.drawString("A("+x1Axis+","+y1Axis+")",(w/2)+x1Axis*grid,(h/2)-(y1Axis*grid)-5);
                gr.drawString("A'("+x2Axis+","+y2Axis+")",(w/2)+x2Axis*grid,(h/2)-(y2Axis*grid)-5);
                break;
            
            //----- clipping -----
            case 29:
                if (cs == 1) {
                    Point(gr, poX[0], poY[0], poX[1], poY[1]);
                    drawClip(gr);
                } else if (cs == 2) {
                    drawClip(gr);
                    Shape clipArea = new java.awt.geom.Rectangle2D.Float(grid * xmin + w / 2, h / 2 - ymax * grid, (xmax - xmin) * grid, (ymax - ymin) * grid);
                    gr.clip(clipArea);
                    Point(gr, poX[0], poY[0], poX[1], poY[1]);
                } else {
                    Point(gr, poX[0], poY[0], poX[1], poY[1]);
                }
                break;
            case 30:
                if (cs == 1) {
                    Line(gr, liX[0], liY[0], liX[1], liY[1]);
                    Line(gr, liX[2], liY[2], liX[3], liY[3]);
                    Line(gr, liX[4], liY[4], liX[5], liY[5]);
                    drawClip(gr);
                } else if (cs == 2) {
                    drawClip(gr);
                    Shape clipArea = new java.awt.geom.Rectangle2D.Float(grid * xmin + w / 2, h / 2 - ymax * grid, (xmax - xmin) * grid, (ymax - ymin) * grid);
                    gr.clip(clipArea);
                    Line(gr, liX[0], liY[0], liX[1], liY[1]);
                    Line(gr, liX[2], liY[2], liX[3], liY[3]);
                    Line(gr, liX[4], liY[4], liX[5], liY[5]);
                } else {
                    Line(gr, liX[0], liY[0], liX[1], liY[1]);
                    Line(gr, liX[2], liY[2], liX[3], liY[3]);
                    Line(gr, liX[4], liY[4], liX[5], liY[5]);
                }
                break;
            case 31:
                if (cs == 1) {
                    rectFill(gr, reX[0], reY[0], reX[1], reY[1]);
                    rectFill(gr, reX[2], reY[2], reX[3], reY[3]);
                    rectFill(gr, reX[4], reY[4], reX[5], reY[5]);
                    drawClip(gr);
                } else if (cs == 2) {
                    drawClip(gr);
                    Shape clipArea = new java.awt.geom.Rectangle2D.Float(grid * xmin + w / 2, h / 2 - ymax * grid, (xmax - xmin) * grid, (ymax - ymin) * grid);
                    gr.clip(clipArea);
                    rectFill(gr, reX[0], reY[0], reX[1], reY[1]);
                    rectFill(gr, reX[2], reY[2], reX[3], reY[3]);
                    rectFill(gr, reX[4], reY[4], reX[5], reY[5]);
                } else {
                    rectFill(gr, reX[0], reY[0], reX[1], reY[1]);
                    rectFill(gr, reX[2], reY[2], reX[3], reY[3]);
                    rectFill(gr, reX[4], reY[4], reX[5], reY[5]);
                }
                break;
            case 32:
                int x1[] = Arrays.copyOfRange(triX, 0, 3);
                int y1[] = Arrays.copyOfRange(triY, 0, 3);
                int x2[] = Arrays.copyOfRange(triX, 3, 6);
                int y2[] = Arrays.copyOfRange(triY, 3, 6);
                int x3[] = Arrays.copyOfRange(triX, 6, 9);
                int y3[] = Arrays.copyOfRange(triY, 6, 9);
                if (cs == 1) {
                    triFill(gr, x1, y1, x1.length);
                    triFill(gr, x2, y2, x2.length);
                    triFill(gr, x3, y3, x3.length);
                    drawClip(gr);
                } else if (cs == 2) {
                    drawClip(gr);
                    Shape clipArea = new java.awt.geom.Rectangle2D.Float(grid * xmin + w / 2, h / 2 - ymax * grid, (xmax - xmin) * grid, (ymax - ymin) * grid);
                    gr.clip(clipArea);
                    triFill(gr, x1, y1, x1.length);
                    triFill(gr, x2, y2, x2.length);
                    triFill(gr, x3, y3, x3.length);
                } else {
                    triFill(gr, x1, y1, x1.length);
                    triFill(gr, x2, y2, x2.length);
                    triFill(gr, x3, y3, x3.length);
                }
                break;
            case 33:
                if (cs == 1) {
                    elliFill(gr, elX[0], elY[0], elX[3]);
                    elliFill(gr, elX[1], elY[1], elX[4]);
                    elliFill(gr, elX[2], elY[2], elX[5]);
                    drawClip(gr);
                } else if (cs == 2) {
                    drawClip(gr);
                    Shape clipArea = new java.awt.geom.Rectangle2D.Float(grid * xmin + w / 2, h / 2 - ymax * grid, (xmax - xmin) * grid, (ymax - ymin) * grid);
                    gr.clip(clipArea);
                    elliFill(gr, elX[0], elY[0], elX[3]);
                    elliFill(gr, elX[1], elY[1], elX[4]);
                    elliFill(gr, elX[2], elY[2], elX[5]);
                } else {
                    elliFill(gr, elX[0], elY[0], elX[3]);
                    elliFill(gr, elX[1], elY[1], elX[4]);
                    elliFill(gr, elX[2], elY[2], elX[5]);
                }
                break;
                case 34:
                Trans(gr, x1Axis, y1Axis, x2Axis, y2Axis);
                gr.setColor(Color.BLUE);
                gr.setColor(Color.BLACK);
                gr.drawString("A ("+ x1Axis + ", "+ y1Axis +")", x1Axis*grid + w / 2+5,  h/2-y1Axis*grid-5);
                gr.drawString("A'("+ (x2Axis+x1Axis) + ", "+ (y2Axis+y1Axis) +")", (x2Axis+x1Axis)*grid + w / 2+5,  h/2-(y2Axis+y1Axis)*grid-5);
                break;
            case 35:
                Line(gr, x1Axis, y1Axis, (x2Axis+x1Axis), (y2Axis+y1Axis),2);
                Trans(gr, x1Axis, y1Axis, x2Axis, y2Axis);
                gr.setColor(Color.BLACK);
             gr.drawString("A ("+ x1Axis + ", "+ y1Axis +")", x1Axis*grid + w / 2+5,  h/2-y1Axis*grid-5);
                gr.drawString("A'("+ (x2Axis+x1Axis) + ", "+ (y2Axis+y1Axis) +")", (x2Axis+x1Axis)*grid + w / 2+5,  h/2-(y2Axis+y1Axis)*grid-5);
                break;
         
            default: ;
        }
    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2, int ar) {//gambar garis
        Graphics2D g = (Graphics2D) g1.create();
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy), size = 10;
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        g.drawLine(0, 0, len, 0);
        if (ar == 28) {
            g.fillPolygon(new int[]{len, len - size, len - size, len}, new int[]{0, -size, size, 0}, 3);
        }
    }
    public void arrow(Graphics gg, int x1, int y1, int x2, int y2){
        Graphics2D g = (Graphics2D) gg.create();
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy), size = 10;
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        g.drawLine(0, 0, len, 0);
//        if (pilih == 5) {
//            g.fillPolygon(new int[]{len, len - size, len - size, len}, new int[]{0, -size, size, 0}, 3);
    }
    public void Point(Graphics2D p, int x1, int y1, int x2, int y2) {
        p.setStroke(new BasicStroke(4));
        p.drawLine(x1 * grid + w / 2, h / 2 - y1 * grid,
                x1 * grid + w / 2, h / 2 - y1 * grid);//titik 1
        p.drawLine(x2 * grid + w / 2, h / 2 - y2 * grid,
                x2 * grid + w / 2, h / 2 - y2 * grid);//titik 2
    }
    
    public void Point3(Graphics2D p, int x1, int y1, int x2, int y2, int x3, int y3) {
        p.setStroke(new BasicStroke(4));
        p.drawLine(x1 * grid + w / 2, h / 2 - y1 * grid,
                x1 * grid + w / 2, h / 2 - y1 * grid);//titik 1
        p.drawLine(x2 * grid + w / 2, h / 2 - y2 * grid,
                x2 * grid + w / 2, h / 2 - y2 * grid);//titik 2
        p.drawLine(x3 * grid + w / 2, h / 2 - y3 * grid,
                x3 * grid + w / 2, h / 2 - y3 * grid);//titik 2
    }

    public void Line(Graphics l, int x1, int y1, int x2, int y2) {
        Graphics2D gr = (Graphics2D) l;
        gr.setStroke(new BasicStroke(2));
        arrow(l, x1 * grid + w / 2, h / 2 - y1 * grid,
                x2 * grid + w / 2, h / 2 - y2 * grid);
        Point(gr, x1, y1, x2, y2);
    }
    
    public void Rect(Graphics2D rec) {
        rec.drawPolygon(reX, reY, reX.length);
    }
    
    private void rectFill(Graphics2D recf, int x, int y, int wi, int he) {
        recf.fillRect(grid * x + w / 2, h / 2 - y * grid, wi * grid, he * grid);
    }

    private void triFill(Graphics2D tr, int x[], int y[], int p) {
        tr.fillPolygon(x, y, p);
    }

    private void elliFill(Graphics2D elf, int x, int y, int d) {
        elf.fillOval(grid * x + w / 2 - d * grid / 2, h / 2 - y * grid - d * grid / 2, d * grid, d * grid);
    }
    
    private void drawClip(Graphics2D cl) {
        cl.setColor(Color.BLUE);
        cl.setStroke(new BasicStroke(2));
        cl.drawRect(grid * xmin + w / 2, h / 2 - ymax * grid, (xmax - xmin) * grid, (ymax - ymin) * grid);
        cl.setColor(Color.BLUE);
        cl.drawString("A("+xmin+","+ymin+")",(w/2)+xmin*grid,(h/2)-(ymin*grid)-5);
        cl.drawString("B("+xmax+","+ymin+")",(w/2)+xmax*grid,(h/2)-(ymin*grid)-5);
        cl.drawString("C("+xmax+","+ymax+")",(w/2)+xmax*grid,(h/2)-(ymax*grid)-5);
        cl.drawString("D("+xmin+","+ymax+")",(w/2)+xmin*grid,(h/2)-(ymax*grid)-5);
    }
    
     public void Trans (Graphics2D p, int x1, int y1, int x2, int y2){
        int x_trans = x1 + x2;
        int y_trans = y1 + y2;
        Graphics2D gr = (Graphics2D) p;
        gr.setColor(Color.BLACK);
        Point(gr,x1,y1,x_trans,y_trans);
        gr.setStroke(new BasicStroke(2));
        gr.setColor(Color.red);
         drawArrow(p, x1 * grid + w /2, h/2-y1*grid, x_trans*grid+w/2, h/2-y1*grid,1);
         drawArrow(p, x_trans * grid + w /2, h/2-y1*grid, x_trans*grid+w/2, h/2-y_trans*grid,1);
        
    }
    public void Line(Graphics l, int x1, int y1, int x2, int y2, int ar) {
        Graphics2D gr = (Graphics2D) l;
        gr.setStroke(new BasicStroke(2));
        drawArrow(l, x1 * grid + w / 2, h / 2 - y1 * grid,
                x2 * grid + w / 2, h / 2 - y2 * grid,ar);
        Point(gr, x1, y1, x2, y2);
    }
}
