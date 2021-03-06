import java.awt.*;
import java.io.File;
import javax.swing.*;

/** Defines all of the possible character types in the game. Each
 *  character type is accompanied by a set of images which
 *  represent the different possible states or appearances. Up to
 *  five possible states exist, with one representing the still
 *  image, another two representing movement, and the final two
 *  representing the two possible action states.
 *
 *  When loaded, the path to the image files are searched for
 *  the necessary image filenames. For each character, the image
 *  filenames searched for are of the form "[name][index].png",
 *  where name is the character-type, index is a positive integer
 *  starting at 0, and the image is of the PNG file format.
 *  The images are then resized so that the height matches that
 *  of the game window by default.
 *
 *  When retrieving the set of images, you can either retrieve the
 *  default-sized image array or a resized type. In order to retrieve
 *  a resized image array, the absolute size is passed into the
 *  getAnimationImagesResized(double) function (absolute meaning
 *  with respect to the height of the window). Either way, the
 *  returned array is immutable.
 *
 *  @author Paul Shin
 *  @since 0.1.0
 *  @version 0.1.0
 */
public enum FigureType
{
  /* ENUMERATIONS */
  
  ENEMY     ("char_evil\\enemy"  ),
  MOB       ("char_evil\\mob"    ),
  JASON     ("char_main\\jason"  ),
  PAUL      ("char_main\\paul"   ),
  RICK      ("char_main\\rick"   ),
  SAM       ("char_main\\sam"    ),
  SHIN      ("char_main\\shin"   ),
  HEATHER   ("char_side\\heather");
  
  
  
  /* PRIVATE DATA */
  
  /** The animation images for each character. */
  private final ImageIcon[] animationImages;
  
  
  
  /* CONSTRUCTORS */
  
  /** Constructor for the FigureType enum. This method takes in
   *  a string representing the path to the image files from the
   *  base images directory, plus the figure's name. It then
   *  loads all of the relevant images for the specified character,
   *  filling in any gaps at the end of the array with the default
   *  still image.
   *  @param image    The path to a character's image files and its name. */
  FigureType(String image)
  {
    String path = "..\\images\\"; // relative location to image directory
    
    /* Determine the number of relevant image files. */
    int first_non_image = -1; // first occurrance of a non-existant image
    while (new File(path + image + ++first_non_image + ".png").exists()) {}
    
    /* Initialize the animationImages array to a max size of 5. */
    animationImages = new ImageIcon[5];
    
    /* Load the animationImages array with all provided images.
     * If less than 4 images exist, use the default first image. */
    for (int i = 0; i < animationImages.length; i++)
    {
      String fn = ""; // store the relative location to image here
      
      /* Retrieve the location of the file. */
      if (i < first_non_image) {
        fn = path + image + i + ".png";
        System.out.println("Loaded character image: " + fn);
      } else {
        fn = path + image + 0 + ".png";
      }
      
      /* Load the image first. */
      ImageIcon loaded_image = new ImageIcon(fn);
      
      /* Determine the width and height of loaded image. */
      int load_height = Game.HEIGHT;
      int load_width
      = (int) ( ( (double) Game.HEIGHT / loaded_image.getIconHeight() )
      * loaded_image.getIconWidth() );
      
      /* Add the image to the array as an icon. */
      animationImages[i]
      = new ImageIcon
      ( loaded_image.getImage().getScaledInstance
      ( load_width, load_height, Image.SCALE_SMOOTH ) );
    }
  }
  
  
  
  /* PUBLIC FUNCTIONS */
  
  /** Returns the array of animation image files. Note that this array
   *  cannot be modified.
   *  @return The array of animation image files. */
  public ImageIcon[] getAnimationImages()
  {
    return animationImages;
  }
  
  /** Returns the array of animation image files with resized images
   *  specified by the parameter. A scale of 1 represents the images
   *  without any scaling, whereas a scale of 2 represents the images
   *  doubled in size.
   *  @param scale    The factor by which to scale the images.
   *  @return The array of animation image files scaled by some factor. */
  public ImageIcon[] getAnimationImagesResized(double scale)
  {
    final ImageIcon[] animationImagesResized // holds the scaled instances
    = new ImageIcon[animationImages.length]; // of the animation images
    int[] newWidths = new int[animationImages.length]; // width of scaled img
    int[] newHeights = new int[animationImages.length]; // height of scaled img
    
    /* Figure out the new dimensions of the images. */
    for (int i = 0; i < animationImages.length; i++)
    {
      ImageIcon currentImage = animationImages[i];
      newWidths[i]  = (int) Math.ceil(currentImage.getIconWidth()  * scale);
      newHeights[i] = (int) Math.ceil(currentImage.getIconHeight() * scale);
    }
    
    /* Load the return array with the appropriately scaled images. */
    for (int i = 0; i < animationImagesResized.length; i++)
    {
      ImageIcon currentImage = animationImages[i];
      ImageIcon scaledImage /* scale the image */
      = new ImageIcon
      ( currentImage.getImage().getScaledInstance
      ( newWidths[i], newHeights[i], Image.SCALE_SMOOTH) );
      animationImagesResized[i] = scaledImage; /* add the scaled image */
    }
    
    return animationImagesResized;
  }
}