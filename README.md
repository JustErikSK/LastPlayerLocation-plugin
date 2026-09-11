**Last Player Location** is a server plugin that records the last known location of players when they leave the server.

Whenever a player disconnects are being online for a configurable amount of time, the plugin saves their UUID, player name, dimension, coordinates and the exact date and time of their disconnect. This information is stored is a simple txt file located in **plugins/LastPlayerLocation/last_location.txt**, making it easy for server administrators to check where a player was last located. So if you're hosting a server and your friend falls into a lava, you can find them easily and help them!

The minimum amount of time a player must be online before their location is recorded can be configured, preventing very short connection and reconnects from spamming the location log.

### Here's a list of features if you don't wanna read all the text above:
- Records the player's last location when they disconnect
- Saves the player's UUID, username, dimension, exact coordinates and disconnect time
- Configurable minimum online time before a location is saved
- Optionally saves locations when the server shuts down or restarts
