package org.example;


import java.io.IOException;
import java.io.InputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
//        final String imageName = "shirou pfp.jpg";
//        final String imageName = "proof.jpg";
        final String imageName = "gabi.jpg";
        final InputStream imgStream = Main.class.getClassLoader().getResourceAsStream(imageName);
        final Ascifier ascifier = new Ascifier();
        ascifier.ascify(imgStream);

    }
}