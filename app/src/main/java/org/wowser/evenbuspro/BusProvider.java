package org.wowser.evenbuspro;

import com.squareup.otto.Bus;

/**
 * Created by Wowser on 2016/3/16.
 */
public class BusProvider {

    private static Bus  BUS = new Bus();

    public  static Bus  getInstance(){
        return   BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
