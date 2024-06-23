package com.example.js_to_isoservice.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.MUX;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class IsomsgRestController {


    //autowired MUX
    @Autowired
    private MUX mux;



    //create an echo API
    @GetMapping("/echo")
    public String echo() throws ISOException, NameRegistrar.NotFoundException {
        mux = QMUX.getMUX("clientsimulator-mux");
        //create an ISOMsg
        byte b[] = {00, ((byte) 0xfb)};


        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setMTI("0100");
        isoMsg.set(2, "0000000");
        isoMsg.set(3, "000000");
        isoMsg.set(4, "100000");
        isoMsg.set(7, new SimpleDateFormat("MMddHHmmss").format(new Date()));
        isoMsg.set(11, "123");
        isoMsg.set(12, new SimpleDateFormat("HHmmss").format(new Date()));
        ISOMsg respMsg = mux.request(isoMsg, 100);
        if (respMsg == null) {
            return "No response received from server.";
        } else {
            System.out.println(Arrays.toString(respMsg.pack()));
            return respMsg.toString();
        }


    }


    @GetMapping(value="/echo-v2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String echoV2(@RequestParam String cardNumber,
                         @RequestParam String processingCode,
                         @RequestParam String AmountTransaction,
                         @RequestParam String SystemTraceAuditNumber,
                         @RequestParam String DateExpiration,
                         @RequestParam String AcquiringInstitutionCountryCode,
                         @RequestParam String CardAcceptorIDCode



    ) throws ISOException, NameRegistrar.NotFoundException {
        mux = QMUX.getMUX("clientsimulator-mux");
        //create an ISOMsg
        byte b[] = {00, ((byte) 0xfb)};

        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setMTI("0100");
        isoMsg.set(2, cardNumber);
        isoMsg.set(3, processingCode);
        isoMsg.set(4, AmountTransaction);
        isoMsg.set(7, new SimpleDateFormat("MMddHHmmss").format(new Date()));
        isoMsg.set(11, SystemTraceAuditNumber);
        isoMsg.set(14, DateExpiration);
        isoMsg.set(19, AcquiringInstitutionCountryCode);
        isoMsg.set(42, CardAcceptorIDCode);
        ISOMsg respMsg = mux.request(isoMsg, 100);
        if (respMsg == null) {
            return "No response received from server.";
        } else {
            System.out.println(Arrays.toString(respMsg.pack()));
            return respMsg.toString();
        }


    }

    @GetMapping(value = "/spam")
    private void getStudentString() throws InterruptedException {
        int count = 0;
        while (true){
            // calculate the time

            String uri = "http://localhost:8087/echo";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.getForObject(uri, String.class));
        System.out.println(count++);
        sleep(5);

            }
    }

    //create an echo API
//
//    @GetMapping("/echo1")
//    public void echo(@ModelAttribute("card") String card,
//                     @ModelAttribute("currency") String currency,
//                     @ModelAttribute("amount") String amount,
//                     @ModelAttribute("ExpireDate") String ExpireDate,
//                     @ModelAttribute("Point_service") String Point_service,
//                     @ModelAttribute("Date_Transaction") String Date_Transaction
//
//
//    ) throws ISOException {
//
//        //Isomsg isomsg = isomsgService.getIsomsg("987654");
//
//        //System.out.println("Montant: "+isomsg.getAmount());
//
//        try {
//            mux = QMUX.getMUX("clientsimulator-mux");
//            ISOMsg msg = new ISOMsg();
//            msg.setMTI("0200");
//            msg.set(2,card);  //card number
//            msg.set(3,"0000");  //Transaction Type
//            msg.set(4,amount);  //Montant de transaction
//            msg.set(11,"000001");
//
//            String t1[] = Date_Transaction.split("/");
//
//            String date12=t1[0]+""+t1[1]+""+t1[2];
//            String date13=t1[3]+""+t1[4];
//            msg.set(12,date12);  //Date et heure locale de la transaction (Format : AAMMJJhhmmss)
//            msg.set(13,date13);
//
//            String t[] = ExpireDate.split("");
//
//            String Expdate =  t[2]+""+t[3]+""+t[5]+""+t[6];
//            msg.set(14,Expdate);  //Date  d’expiration (Format AAMM)
//            msg.set(49,currency);
//            msg.set(120,"8001");//Code monnaie de la transaction
//
//            msg.set(70,"301");
//            System.out.println("Amount***:"+amount);
//            System.out.println("Date Transaction***:"+Date_Transaction);
//            System.out.println("currency***:"+currency);
//            System.out.println("expirydate****:"+ExpireDate);
//
//
//            ISOMsg respMsg = mux.request(msg,30000);
//            Transaction_Hist transactionHist = new Transaction_Hist();
//            transactionHist.setCardNumber(respMsg.getString(2));
//            transactionHist.setDate_transaction(respMsg.getString(12)+""+respMsg.getString(13));
//            transactionHist.setAmount(Double.parseDouble(respMsg.getString(4)));
//            transactionHist.setExpireDate(respMsg.getString(14));
//            transactionHist.setPoint_de_service(respMsg.getString(3));
//            transactionHist.setState(respMsg.getString(125));
//            transactionHistService.saveIsomsg(transactionHist);
//            System.out.println(respMsg);
//
//        } catch (NameRegistrar.NotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//    }


}
