package org.example;


import java.net.URL;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Main");
//        final String vidName = "enderman_dance.mp4";
      final String vidName = "badApple.flv";
//      final String vidName = "shirou pfp.jpg";
      final String vidPath = Paths.get(
          Main.class.getClassLoader().getResource(vidName).toURI()
      ).toString();

      System.out.println( vidPath );

      AsciiVidRunner.run(vidPath);



    }
}
