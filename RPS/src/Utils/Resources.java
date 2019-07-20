package Utils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Resources {

    static Class source = Resources.class;


    private Resources() {
    }


    public static void setSourceClass(Class source) {
        Resources.source = source;
    }

    public static BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getResource(path));
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
        return image;
    }


    public static String getText(String path) {

        StringBuilder builder = new StringBuilder();

        try (InputStream byteStream = getResourceAsStream(path);
             InputStreamReader txtStream = new InputStreamReader(byteStream, "ISO-8859-1");
             BufferedReader reader = new BufferedReader(txtStream)) {

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }

        return builder.toString();
    }


    public static String extract(String path) throws IOException {
        return extract(path, null);
    }


    public static String extract(String path, String destinationPath) throws IOException {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String runningPath = getRunningPath();
        if (isRunningFromJar()) {
            if (destinationPath == null || destinationPath.isEmpty()) {
                destinationPath = runningPath.substring(0, runningPath.lastIndexOf("/"));
            }
            JarFile jar = new JarFile(runningPath);
            JarEntry file = jar.getJarEntry(path);
            String fileName = file.getName();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
            File destination = new File(destinationPath + File.separator + fileName);
            if (!destination.exists()) {
                if (file.isDirectory()) {
                    destination.mkdir();
                }
                try (
                        InputStream input = jar.getInputStream(file);
                        FileOutputStream output = new FileOutputStream(destination)) {
                    while (input.available() > 0) {
                        output.write(input.read());
                    }
                }
            }
            return destination.getAbsolutePath();
        } else {
            return runningPath + path;
        }
    }


    public static String getRunningPath() {
        String runningPath = Resources.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            return new URI(runningPath).getPath();
        } catch (URISyntaxException ex) {
            return runningPath;
        }
    }


    public static boolean isRunningFromJar() {
        String className = Resources.class.getName().replace('.', '/');
        String classJar = Resources.class.getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }


    public static URL getResource(String resource) {
        return Resources.source.getResource(resource);
    }


    public static InputStream getResourceAsStream(String resource) {
        return Resources.source.getResourceAsStream(resource);
    }
}

