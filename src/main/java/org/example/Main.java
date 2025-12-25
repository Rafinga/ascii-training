package org.example;


import java.net.URL;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Main");
        
        if (args.length == 0) {
            System.err.println("Usage: java Main <filename> [speed]");
            System.exit(1);
        }
        
        final String vidName = args[0];
        final int speed = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        
        final String vidPath = Paths.get(
            Main.class.getClassLoader().getResource(vidName).toURI()
        ).toString();

        System.out.println(vidPath);
        AsciiVidRunner.run(vidPath, speed);



    }
}
