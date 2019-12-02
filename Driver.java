/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamercollector;

import java.io.IOException;
import java.util.ArrayList;
import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;

/**
 * @author cesar
 */
public class Driver {
    final String PATH = "<your directory path>";
    final int addLimit = 50;
    
    public void run() throws IOException{
        MapIO streamersMap = new MapIO(PATH);

        
        ArrayList<String> streamersNotInMap = getStreamersNotInMap(streamersMap, addLimit);
        
        for (String streamer : streamersNotInMap) {
            openWebpage("https://www.twitch.tv/" + streamer);
            String input = inputOriginName(streamer);
            
            if(input.equals("!q")) {
                System.out.println("Attempting to quit...");
                streamersMap.serializeMap();
                System.out.println("Total streamers in map: "+streamersMap.size());
                return;
            }
            else if(input.equals("!n")){
                System.out.println("Skipping "+ streamer);
            }
            else if(input.equals("!s")){
                System.out.println("Same twitch name and origin name");
                streamersMap.put(streamer, streamer);
            }
            else{
                streamersMap.put(input, streamer);
            }
            
        }
        streamersMap.serializeMap();
        System.out.println("Total streamers in map: "+streamersMap.size());
    }
    
    
    private ArrayList<String> getStreamersNotInMap(MapIO streamerMap, int limit) throws IOException{
        ArrayList<String> result = new ArrayList<>();
        TwitchAPI twitch = new TwitchAPI();
        String page = "";
        while(limit > 0){
            String json = twitch.getStreamersPlaying(twitch.APEX_GAME_ID,twitch.MAX_STREAMERS_PER_CALL, page);
            page = twitch.getPagination(json);
            ArrayList<String> filteredStreamers = filterStreamersOnMap(streamerMap, twitch.getNames(json));
            addToList(result, filteredStreamers, limit);
            limit -= filteredStreamers.size();
        }
        
        return result;
    }
    
    private ArrayList<String> filterStreamersOnMap(MapIO streamerMap, ArrayList<String> streamers){
        ArrayList<String> result = new ArrayList<>();
        
        for (String streamer : streamers) {
            if(!streamerMap.containsValue(streamer.toLowerCase())){
                result.add(streamer.toLowerCase());
            }
        }
        
        return result;
    
    }
    
    private void addToList(ArrayList<String> addTo, ArrayList<String> addFrom, int limit){
        for(int i = 0; i < addFrom.size(); i++){
            if(i >= limit){
                return;
            }
            addTo.add(addFrom.get(i));
        }
    }
    
    
    private void openWebpage(String uriString){
        try {
   
            URI uri = new URI(uriString);
            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("Web page opened in browser");

       } catch (Exception e) {
            e.printStackTrace();
       }
    
    }
    
    private String inputOriginName(String streamer){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter "+streamer+"'s origin username: ");
        return in.nextLine().toLowerCase();
    }
    
    
}
