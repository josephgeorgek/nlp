package com.nlp.mandate.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;

import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

public class DrugsClassifierTrainer {
                static String onlpModelPath = "en-ner-drugs.bin";
                // training data set
                static String trainingDataFilePath = "/Users/josephgeorge/dev/eclipseWS/nlp/springboot-rest-api-sample/src/main/resources/new-model/AnnotatedSentences.txt";
                
                public static void main(String[] args) {
                	 
                    // reading training data
                    InputStreamFactory in = null;
                    
                    
                    try {
                        in = new MarkableFileInputStreamFactory(new File(trainingDataFilePath));
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    }
                     
                    ObjectStream sampleStream = null;
                    try {
                        sampleStream = new NameSampleDataStream(
                            new PlainTextByLineStream(in, StandardCharsets.UTF_8));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
             
                    // setting the parameters for training
                    TrainingParameters params = new TrainingParameters();
                    params.put(TrainingParameters.ITERATIONS_PARAM, 70);
                    params.put(TrainingParameters.CUTOFF_PARAM, 1);
             
                    // training the model using TokenNameFinderModel class 
                    TokenNameFinderModel nameFinderModel = null;
                    try {
                        nameFinderModel = NameFinderME.train("en", null, sampleStream,
                            params, TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                     
                    // saving the model to "ner-custom-model.bin" file
                    try {
                        File output = new File("ner-custom-model.bin");
                        FileOutputStream outputStream = new FileOutputStream(output);
                        nameFinderModel.serialize(outputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                     
                    // testing the model and printing the types it found in the input sentence
                    TokenNameFinder nameFinder = new NameFinderME(nameFinderModel);
                     
                    String[] testSentence ={"Alisa","Fernandes","is","a","tourist","from","Spain","Joseph","George","is","from","Moon"};
             
                    System.out.println("Finding types in the test sentence..");
                    Span[] names = nameFinder.find(testSentence);
                    System.out.println("Found types in the test sentence..:"+names.length);
                    for(Span name:names){
                        String personName="";
                        for(int i=name.getStart();i<name.getEnd();i++){
                            personName+=testSentence[i]+" ";
                        }
                        System.out.println(name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
                    }
                }
             
                
              
				/*
				 * public static void main2(String[] args) throws IOException { Charset charset
				 * = Charset.forName("UTF-8"); TrainingParameters params = new
				 * TrainingParameters(); params.put(TrainingParameters.ITERATIONS_PARAM, 70);
				 * params.put(TrainingParameters.CUTOFF_PARAM, 1);
				 * 
				 * InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new
				 * File("src/main/resources/models/DoccatSample.txt")); // ObjectStream
				 * lineStream = new PlainTextByLineStream(dataIn, "UTF-8"); ObjectStream<String>
				 * lineStream = new PlainTextByLineStream( dataIn, charset);
				 * ObjectStream<NameSample> sampleStream = new NameSampleDataStream(
				 * lineStream); TokenNameFinderModel model = null; HashMap<String, Object> mp =
				 * new HashMap<String, Object>();
				 * 
				 * 
				 * TokenNameFinderModel nameFinderModel = null; try { nameFinderModel =
				 * NameFinderME.train("en", "drugs", sampleStream, params,
				 * TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new
				 * BioCodec())); } catch (IOException e) { e.printStackTrace(); }
				 * 
				 * 
				 * try {
				 * 
				 * 
				 * 
				 * model = NameFinderME.train("en", "drugs", sampleStream,
				 * Collections.<String,Object> emptyMap(),100,4); } finally {
				 * sampleStream.close(); } BufferedOutputStream modelOut = null; try { modelOut
				 * = new BufferedOutputStream(new FileOutputStream(onlpModelPath));
				 * model.serialize(modelOut); } finally { if (modelOut != null)
				 * modelOut.close(); } }
				 */
}