package in.reqres.support;

import java.util.Random;

/**
 * @author jussaragranja
 * Class with generic methods
 */

public class Util {

    public static int valorRandomico(int max) {
        int valor = new Random().nextInt(max);

        return valor;
    }

}
