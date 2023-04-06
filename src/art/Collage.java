package art;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {



        collageDimension = 4;
        tileDimension = 150; 

        originalPicture =new Picture(filename);

        collagePicture =new Picture((tileDimension*collageDimension),(tileDimension*collageDimension));


        scale(originalPicture, collagePicture);

        

        
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {

        this.collageDimension=cd;
        this.tileDimension=td;
        int widthtotal = cd*td;


        originalPicture=new Picture(filename);
        collagePicture =new Picture(widthtotal, widthtotal);


        scale(originalPicture, collagePicture);
    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {

        

        int w=target.width(); 
        int h=target.height();
        

        for(int col = 0; col<w;col++)
        {
            for(int rol=0;rol<h;rol++)
            {
                int colcheck = col*source.width()/w;
                int rolcheck = rol * source.height()/h;
                Color color = source.get(colcheck, rolcheck);
                target.set(col,rol,color);
            }
            target.show();

        }

        
    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {

        Picture scaledOriginal = new Picture(tileDimension, tileDimension);
        scale(originalPicture, scaledOriginal);

     
        for (int i = 0; i < collagePicture.height()/tileDimension; i++) {
            for (int j = 0; j < collagePicture.height()/tileDimension; j++) {
                  
                for(int k=0;k<scaledOriginal.height();k++)
                    {
                        for(int m=0;m<scaledOriginal.height();m++)
                        {
                            Color color =scaledOriginal.get(k, m);
                            collagePicture.set(k+(i*tileDimension), m+(j*tileDimension) ,color);
                        }
                    }

                    
                
            }

        }
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {

        for( int i = 0; i < collagePicture.height()/tileDimension; i++) 
        {
            for (int j = 0; j < collagePicture.height()/tileDimension; j++) 
            {
                if ( j == collageCol && i == collageRow ) 
                {    
                    for (int pixelRow = 0; pixelRow < tileDimension ; pixelRow++) 
                    {
                        for (int pixelCol = 0; pixelCol < tileDimension; pixelCol++) 
                        {
                            Color color = collagePicture.get(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i));
                            int red = color.getRed();
                            int green = color.getGreen();
                            int blue = color.getBlue();
                                if(component.equals("red")) 
                                {
                                    collagePicture.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(red, 0, 0));
                                }
                                else if(component.equals("green")) 
                                {
                                    collagePicture.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, green, 0));
                                }
                                else if(component.equals("blue"))
                                {
                                    collagePicture.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, 0, blue));
                                }
                        }
                    }
                }
            }
        }
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {



        Picture replace = new Picture(filename);
        Picture replacescale= new Picture(tileDimension, tileDimension);

        scale(replace, replacescale);

        for(int i = 0; i< collagePicture.height()/tileDimension; i++) {
            for(int j = 0; j< collagePicture.height()/tileDimension; j++) {
                if ( j == collageRow && i == collageCol ) {



                    for(int k=0;k<replacescale.height();k++)
                    {
                        for(int m=0;m<replacescale.height();m++)
                        {
                            Color color =replacescale.get(k, m);
                            collagePicture.set(k+(i*tileDimension), m+(j*tileDimension), color);
                        }
                    }



                    
                }
            }
        }
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {

        for( int i = 0; i < collagePicture.height()/tileDimension; i++) {
            for (int j = 0; j < collagePicture.height()/tileDimension; j++) {
                if ( j == collageCol && i == collageRow ) {
                    //Picture pictureTile = new Picture(i,j);
                    for (int prow = 0; prow < tileDimension ; prow++) {

                        for (int pcol = 0; pcol < tileDimension; pcol++) {
                            Color color = collagePicture.get(pcol + (tileDimension * j), prow + (tileDimension * i));
                            Color gray = toGray(color); 
                            collagePicture.set(pcol + (tileDimension * j), prow + (tileDimension * i), gray);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
