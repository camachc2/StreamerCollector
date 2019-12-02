# StreamerCollector
Open livestreams by twitch gameID to store twitch name and ingameUsername in a serialized hashmap. 

## Reason
1. There is no database that associates livestreamers with their ingame usernames so this program creates a map that stores that information. 
2. I needed this map for a different project. 

## Commands
1. !q - saves and quits
2. !n - next streamer, does not save
3. !s - same twitch username and ingame username

## Comments
  1. I used an AutoHotKey file to keep the console in the foreground when recording usernames as it opens the livestreams in your browser.
