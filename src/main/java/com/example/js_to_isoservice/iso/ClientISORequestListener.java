package com.example.js_to_isoservice.iso;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

import java.io.IOException;

public class ClientISORequestListener implements ISORequestListener {
    @Override
    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {
        try {
            ISOMsg respMsg = (ISOMsg) isoMsg.clone();
            respMsg.set(124, "HI server, THIS IS DATA FROM ME!");
            respMsg.set(39, "00");
            respMsg.setResponseMTI();
            isoSource.send(respMsg);
        } catch (ISOException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
