package com.example.PruebaCRUD.Frames;

import java.io.File;
import java.io.IOException;

/**
 * Clase para poder dividir el video del alumon en varios frames
 */
public class DivisionFrames {
    /**
     *
     * @param videoPath Ruta de donde está guardado el video.
     * @param outputDir Ruta en donde se guardarán los frames del video.
     */
    public static void extractFrames(String videoPath, String outputDir) {
        try {
            // Crear el directorio si no existe
            new File(outputDir).mkdirs();

            System.out.println("EL VIDEO ES " + videoPath);
            System.out.println("DONDE SE GUARDARÁ ES " + outputDir);

            /**
             * Extraer 15 fotogramas distribuidos uniformemente mediante procesos del sistema operativo, es decir, cmd.
             *  ffmpeg: Línea de comandos para convertir, grabar y modificar archivos multimedia
             *  -i videoPath: Indica la entrada del archivo en este caso.
             *  -vf fps: Aplica un filtro al video, es decir, va a extraer 3 fotogramas por segundo.
             *  -vsync vfr: Asegura que los fotogramas extraídos se sincronicen con un modo de frecuencia variable
             */
            ProcessBuilder builder = new ProcessBuilder(
                    "ffmpeg", "-i", videoPath, "-vf", "fps=3", "-vsync", "vfr", outputDir + "/frame_%02d.png"
            );

            // Ejecuta el proceso anterior
            Process process = builder.start();
            // Espera a que el proceso termine su ejecución
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
