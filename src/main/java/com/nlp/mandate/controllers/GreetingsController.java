package com.nlp.mandate.controllers;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
    	

   	 String paragraph = "This is a statement. This is another statement. Now is an abstract word for time, "
                + "that is always flying. And my email address is google@gmail.com.";
   	 InputStream is = getClass().getResourceAsStream("/models/en-sent.bin");
   	 String str = "";
        SentenceModel model;
		try {
			model = new SentenceModel(is);
		

        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String sentences[] = sdetector.sentDetect(paragraph);
        
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < sentences.length; i++) {
           sb.append(sentences[i]);
        }
        sb.append("<br>");
        str = sb.toString();
        System.out.println(str);
		
		
		
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("Hotel: Good morning, EDotel Hotel reservation Vanny􁢾s speaking, how may I assistyou?Guest: Hello, good morning. I’d like to make a reservation for the second weekend inOctober in your hotel.Hotel: Excuse me Mam, may I know who is speaking?Guest:"
        		+ " I’m Taylor Swift .Hotel: Could you spell your name please?Guest: T-A-Y-L-OR S-W-I-F-T Hotel: Alright Mam, now I want to spell your name. Your name is Taylor Swift , T for Tango, A for Alpha, Y for Yellow, L for London , O for Orange , R for Roma. S for Sidney, W for Work, I for India, F for Fox, T for Tango. Am I right mam?Guest: Yes, you are right.Hotel: May I know who is the reservation for Miss. Swift ?"
        		+ "Guest: The reservation is for me.Hotel: Alright Miss. Swift , may I know what type of room do you need?Guest: I need a suite room. What is the rate of the room?"
        		+ "Hotel: Certainly Mam. The room price is Rp.450.000/night. What is the exact date of your arrival Mam?Guest: The 12nd of October 2014Hotel: How long will you be staying Miss. Swift ?Guest: I’ll be staying for three nights.Hotel: Your departure could be 15th of October 2014 ,"
        		+ " couldn􁢾t it Mam?Guest: Yes that’s right.Hotel: How many people is the reservation for Miss. Swift ?Guest: There will be two people.Hotel: Well Miss. Swift , wait a moment please,I would like to check the availability ofthe room on that period.Guest: Yes please. "
        		+ "Hotel: hank you very much for your waiting Miss. Swift , we have several suite roomavailable on that particular weekend.Guest: "
        		+ "Thanks.Hotel: And, is there a phone number where you can be contacted Miss. Swift ?Guest: "
        		+ "My phone number is +33 161 626 77777Hotel: And your e-mail address please?Guest: swift .taylor@lawyer.co.auHotel: "
        		+ "May I know your nationality Miss. Swift ?Guest: I􁢾m Australian. Hotel: And could you tell me where your current address is?Guest: "
        		+ "Sure. It􁢾s 35 Sand Apparel ST, Sydney .Hotel: Thank you. Do you have any special request that could be prepared for yourarrival here?"
        		+ " May Iknow, what type of credit card have you got?Guest: I have Visa.Hotel: Well Miss. Swift , for completing your reservation, please sends your consentwhich states that you give us authorization to take your deposit as much as theroom price for three nights. And also, please attach your copy of ID and creditcard. And the last, please put your signature bellow your consent. And couldyou please send it to our e-mail address to edotel.hotel@yahoo.com?Guest: May I know what is it for?Hotel: It is for guarantee of your reservation Miss. Swift , because, that period is on ahigh season, so we need a guarantee for your reservation.Guest: OK. I will send it soon.Hotel: Alright Miss. Swift , your reservation has been made for the 12nd until 15th ofOctober 2014 for a suite room. Your phone number is +33 161 626 77777. Andyour e-mail address is swift .taylor@lawyer.co.au. "
        		+ "John is 26 years old. His best friend's name is Leonard. He has a sister named Penny lives in Singapore. "
        		+ "she has $1000.00 USD which is much less than 1 million dollars. That belongs to IBM the company which has 12%. THe time now is noon 12:45PM");
        
        InputStream inputStreamLocationFinder = getClass().getResourceAsStream("/models/en-ner-location.bin");
        TokenNameFinderModel tokenNameFinderModel = new TokenNameFinderModel(inputStreamLocationFinder);
        NameFinderME nameFinderME = new NameFinderME(tokenNameFinderModel);
        List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
    //    assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
        List<String> names = new ArrayList<String>();
        int k = 0;
        for (Span s : spans) {
            names.add("");
            for (int index = s.getStart(); index < s.getEnd(); index++) {
                names.set(k, names.get(k) + tokens[index]);
            }
            k++;
        }
        for(int i = 0; i < names.size(); i++) {
            sb.append(names.get(i));
         }
        sb.append("<br>");
         str = sb.toString();
         System.out.println(str);
         
         
         
         
         
         
         
         
         
         
         
         InputStream inputStreamNameFinder = getClass().getResourceAsStream("/models/en-ner-person.bin");
          tokenNameFinderModel = new TokenNameFinderModel(inputStreamNameFinder);
          nameFinderME = new NameFinderME(tokenNameFinderModel);
          spans = Arrays.asList(nameFinderME.find(tokens));
     //    assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
        names = new ArrayList<String>();
          k = 0;
         for (Span s : spans) {
             names.add("");
             for (int index = s.getStart(); index < s.getEnd(); index++) {
                 names.set(k, names.get(k) + tokens[index]);
             }
             k++;
         }
         for(int i = 0; i < names.size(); i++) {
             sb.append(names.get(i));
          }
         sb.append("<br>");
          str = sb.toString();
          System.out.println(str);
          
          
          
          
          InputStream inputStreamMoneyFinder = getClass().getResourceAsStream("/models/en-ner-money.bin");
           tokenNameFinderModel = new TokenNameFinderModel(inputStreamMoneyFinder);
           nameFinderME = new NameFinderME(tokenNameFinderModel);
           spans = Arrays.asList(nameFinderME.find(tokens));
      //    assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
         names = new ArrayList<String>();
           k = 0;
          for (Span s : spans) {
              names.add("");
              for (int index = s.getStart(); index < s.getEnd(); index++) {
                  names.set(k, names.get(k) + tokens[index]);
              }
              k++;
          }
          for(int i = 0; i < names.size(); i++) {
              sb.append(names.get(i));
           }
          sb.append("<br>");
           str = sb.toString();
           System.out.println(str);
           
           
           
           InputStream inputStreamTimeFinder = getClass().getResourceAsStream("/models/en-ner-time.bin");
           tokenNameFinderModel = new TokenNameFinderModel(inputStreamTimeFinder);
           nameFinderME = new NameFinderME(tokenNameFinderModel);
           spans = Arrays.asList(nameFinderME.find(tokens));
      //    assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
         names = new ArrayList<String>();
           k = 0;
          for (Span s : spans) {
              names.add("");
              for (int index = s.getStart(); index < s.getEnd(); index++) {
                  names.set(k, names.get(k) + tokens[index]);
              }
              k++;
          }
          for(int i = 0; i < names.size(); i++) {
              sb.append(names.get(i));
           }
          sb.append("<br>");
           str = sb.toString();
           System.out.println(str);
           
           
           
           InputStream inputStreamDateFinder = getClass().getResourceAsStream("/models/en-ner-date.bin");
           tokenNameFinderModel = new TokenNameFinderModel(inputStreamDateFinder);
           nameFinderME = new NameFinderME(tokenNameFinderModel);
           spans = Arrays.asList(nameFinderME.find(tokens));
      //    assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
         names = new ArrayList<String>();
           k = 0;
          for (Span s : spans) {
              names.add("");
              for (int index = s.getStart(); index < s.getEnd(); index++) {
                  names.set(k, names.get(k) + tokens[index]);
              }
              k++;
          }
          for(int i = 0; i < names.size(); i++) {
              sb.append(names.get(i));
           }
          sb.append("<br>");
           str = sb.toString();
           System.out.println(str);
           
           
           

           InputStream inputStreamorganizationFinder = getClass().getResourceAsStream("/models/en-ner-organization.bin");
           tokenNameFinderModel = new TokenNameFinderModel(inputStreamorganizationFinder);
           nameFinderME = new NameFinderME(tokenNameFinderModel);
           spans = Arrays.asList(nameFinderME.find(tokens));
      //    assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
         names = new ArrayList<String>();
           k = 0;
          for (Span s : spans) {
              names.add("");
              for (int index = s.getStart(); index < s.getEnd(); index++) {
                  names.set(k, names.get(k) + tokens[index]);
              }
              k++;
          }
          for(int i = 0; i < names.size(); i++) {
              sb.append(names.get(i));
           }
          sb.append("<br>");
           str = sb.toString();
           System.out.println(str);
           
           

           InputStream inputStreampercentageFinder = getClass().getResourceAsStream("/models/en-ner-percentage.bin");
           tokenNameFinderModel = new TokenNameFinderModel(inputStreampercentageFinder);
           nameFinderME = new NameFinderME(tokenNameFinderModel);
           spans = Arrays.asList(nameFinderME.find(tokens));
      //    assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person]");
         names = new ArrayList<String>();
           k = 0;
          for (Span s : spans) {
              names.add("");
              for (int index = s.getStart(); index < s.getEnd(); index++) {
                  names.set(k, names.get(k) + tokens[index]);
              }
              k++;
          }
          for(int i = 0; i < names.size(); i++) {
              sb.append(names.get(i));
           }
          sb.append("<br>");
           str = sb.toString();
           System.out.println(str);
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return "Hello " + str + "!";
    }
}
