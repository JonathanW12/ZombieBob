/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.core.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author phili
 */
public class UpdatesEditor {
    
    //private static final String xmlFilePath = "";
            //"C:\\Users\\phili\\Desktop\\AsteroidsNetbeansModules\\AsteroidsNetbeansModules\\netbeans_site\\updates.xml";
    private final HashMap<String, Boolean> enabledModules = new HashMap<>();
    private final ArrayList<String> nonLoadableModules = new ArrayList<>();
    private static UpdatesEditor instance = new UpdatesEditor();
    private ArrayList<String> xmlElements = new ArrayList<>();
    private File updatesXMLFile = getUpdatesXMLFile();
    
    public static UpdatesEditor getInstance(){
        return instance;
    }
    
    private File getUpdatesXMLFile() {
        File filehandle;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            filehandle = new File("../../netbeans_site/updates.xml");
        } else {
            filehandle = new File("./netbeans_site/updates.xml");
        }

        return filehandle;
    }
    
    private File getNonLoadableModulesFile(){
        File filehandle;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            filehandle = new File("../../non_dynamic_modules.txt");
        } else {
            filehandle = new File("./non_dynamic_modules.txt");
        }

        return filehandle;   
    }
    
    private UpdatesEditor(){
        System.out.println("reading updates.xml files");
        this.xmlElements = new ArrayList<>();
        File file = getUpdatesXMLFile();
        try {
            
            
            Scanner scanner = new Scanner(getNonLoadableModulesFile()).useDelimiter(",");
            
            while(scanner.hasNext()){
                nonLoadableModules.add(scanner.next());
            }
            scanner.close();
            
            
            
            Scanner sc = new Scanner(file).useDelimiter("<|\\>");
            while(sc.hasNext()){
                String newline = System.getProperty("line.separator");
                String next = sc.next();
                if(!next.contains(newline) && !next.equals("")){
                    if(next.contains("!--")){
                        
                        this.xmlElements.add("<"+next);
                    } else if(next.contains("--")){
                        this.xmlElements.add(next+">");
                    } else {
                    this.xmlElements.add("<"+next+">");
                    }
                }
            }
            
            for(int i = 0; i < this.xmlElements.size();i++){
                String element = this.xmlElements.get(i);
                
                if(element.contains("<module ")){
                    if(element.contains("codenamebase=\"dk.sdu.mmmi.cbse.")){
                        String[] strings = element.split("codenamebase=\"dk.sdu.mmmi.cbse.");
                        String newString = strings[1].replaceAll("\"", "");
                        String moduleName = newString.split(" ")[0];
                        if(!nonLoadableModules.contains(moduleName)){
                            if(!this.xmlElements.get(i-1).equals("<!--")){
                                this.enabledModules.put(moduleName, true);
                            } else {
                                this.enabledModules.put(moduleName, false);
                            }
                        }
                    }
                    }
                }
            sc.close();
            
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);  
        } 
    }
    
    public void deactivate(String moduleName){
        System.out.println("de-activating: "+ moduleName);
        this.xmlElements = commentOut(moduleName, this.xmlElements);
        updateFile();
    }
    
    public void activate(String moduleName){
        System.out.println("activating: "+ moduleName);
        this.xmlElements = uncomment(moduleName, this.xmlElements);
        updateFile();
    }
    
    public boolean isActive(String moduleName){
        return this.enabledModules.get(moduleName);
    }
    
    public HashMap<String, Boolean> getModulesStatus(){
        return enabledModules;
    }
    
    private void updateFile(){
        System.out.println("updating file");
        String newContent = "";
        String newline = System.getProperty("line.separator");
            for(String string: this.xmlElements){
                newContent += string;
                newContent += newline;
            }
            //System.out.println("New Content: ");
            //System.out.print(newContent);
           
        
        
        try {
            File file = this.updatesXMLFile;
            PrintWriter printer = new PrintWriter(file);
            printer.print(newContent);
            printer.flush();
            printer.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    
    private ArrayList<String> commentOut(String moduleName, ArrayList<String> fileContent){
        ArrayList<String> newContent = new ArrayList<>();
        if(isActive(moduleName)){
                
        for(int i = 0; i < fileContent.size(); i++){
            String tag = fileContent.get(i);
            if(tag.contains("<module ")){
                if(tag.contains("codenamebase=\"dk.sdu.mmmi.cbse.")){
                        String[] strings = tag.split("codenamebase=\"dk.sdu.mmmi.cbse.");
                        String newString = strings[1].replaceAll("\"", "");
                        String name = newString.split(" ")[0];
                        if(name.equals(moduleName)){
                            if(!fileContent.get(i-1).equals("<!--")){
                                String startCommentTag = "<!--";
                                newContent.add(startCommentTag);
                                newContent.add(tag);
                                i++;
                                newContent.add(fileContent.get(i));
                                i++;
                                newContent.add(fileContent.get(i));
                                String endCommentTag = "-->";
                                newContent.add(endCommentTag);
                                enabledModules.replace(name, false);
                            } else {
                                System.out.println("trying to comment a tag, that's already commented out");
                                newContent.add(tag);
                            }
                        }
                        else{
                            newContent.add(tag);
                        }
                    }else {
                    //System.out.println("cannot comment tag by this name, since the system has a hard dependecy on this module");
                    newContent.add(tag);
                }
            } else {
                newContent.add(tag);
            }
        }
        return newContent;
        //alternativt skal new content bare skrives direkte til updates.xml
        } else {
            return fileContent;
        }
    }
    private ArrayList<String> uncomment(String moduleName, ArrayList<String> fileContent){
        if(!isActive(moduleName)){
        ArrayList<String> newContent = new ArrayList<>();
        for(int i = 0; i < fileContent.size(); i++){
            String tag = fileContent.get(i);
            if(tag.contains("<module ")){
                if(tag.contains("codenamebase=\"dk.sdu.mmmi.cbse.")){
                        String[] strings = tag.split("codenamebase=\"dk.sdu.mmmi.cbse.");
                        String newString = strings[1].replaceAll("\"", "");
                        String name = newString.split(" ")[0];
                        if(name.equals(moduleName)){
                            if(fileContent.get(i-1).equals("<!--")){
                                newContent.remove(i-1);
                                newContent.add(tag);
                                i++;
                                //add manifest tag
                                newContent.add(fileContent.get(i));
                                i++;
                                //add </module> tag
                                newContent.add(fileContent.get(i));
                                fileContent.remove(i+1);
                                fileContent.trimToSize();
                                enabledModules.replace(name, true);
                                } else {
                                //System.out.println("trying to uncomment, a tag that is not commented");
                                newContent.add(tag);
                                }
                            
                        }
                        else{
                            newContent.add(tag);
                        }
                    }else {
                    newContent.add(tag);
                }
            } else {
                newContent.add(tag);
            }
        }
        return newContent; 
    } else {
    return fileContent;
    }
    }
}
