package io.github.hydos.doshysound;

import io.netty.util.internal.logging.AbstractInternalLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * used to improve minecraft's paul sound system
 *
 * @author hydos
 */
public class DosHySoundSystem {

    public static final Logger LOGGER = LogManager.getLogger("DosHy SoundSystem");

    /**
     * modified solution from stack overflow
     * https://stackoverflow.com/questions/20794204/how-to-determine-length-of-ogg-file
     *
     * @param stream the ogg input stream to read
     * @return the length of the ogg file
     * @throws IOException if the file could not be read
     */
    public double calculateDuration(InputStream stream) throws IOException {
        int rate = -1;
        int length = -1;
        int size = stream.available();
        byte[] t = new byte[size];
        stream.read(t);

        for (int i = size - 1 - 8 - 2 - 4; i >= 0 && length < 0; i--) { //4 bytes for "OggS", 2 unused bytes, 8 bytes for length
            if (t[i] == (byte) 'O' && t[i + 1] == (byte) 'g' && t[i + 2] == (byte) 'g' && t[i + 3] == (byte) 'S') { //Oggs
                byte[] byteArray = new byte[]{t[i + 6], t[i + 7], t[i + 8], t[i + 9], t[i + 10], t[i + 11], t[i + 12], t[i + 13]};
                ByteBuffer bb = ByteBuffer.wrap(byteArray);
                bb.order(ByteOrder.LITTLE_ENDIAN);
                length = bb.getInt(0);
            }
        }
        for (int i = 0; i < size - 8 - 2 - 4 && rate < 0; i++) {
            if (t[i] == (byte) 'v' && t[i + 1] == (byte) 'o' && t[i + 2] == (byte) 'r' && t[i + 3] == (byte) 'b' && t[i + 4] == (byte) 'i' && t[i + 5] == (byte) 's') { //vorbis
                byte[] byteArray = new byte[]{t[i + 11], t[i + 12], t[i + 13], t[i + 14]};
                ByteBuffer bb = ByteBuffer.wrap(byteArray);
                bb.order(ByteOrder.LITTLE_ENDIAN);
                rate = bb.getInt(0);
            }
        }
        return (double) (length * 1000) / (double) rate;
    }
}
