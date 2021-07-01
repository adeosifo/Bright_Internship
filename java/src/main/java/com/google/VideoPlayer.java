package com.google;


import javax.swing.*;
import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  Video videoPlaying;
  Boolean paused;
  ArrayList<VideoPlaylist> videoPlaylists = new ArrayList<VideoPlaylist>();
  VideoPlaylist currentPlaylist;
  HashMap<String, String> flaggedVideos = new HashMap<>();
  ArrayList<Video> videoOptions = new ArrayList<>();

  public List<VideoPlaylist> getVideoPlaylists(){
    return videoPlaylists;
  }

  public List<String> getFlaggedVideos(){
    return new ArrayList<>(this.flaggedVideos.values());
  }



  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    Collections.sort(videoLibrary.getVideos());
    for (int i=0; i<videoLibrary.getVideos().size(); i++)
    {
      if (flaggedVideos.containsKey(videoLibrary.getVideos().get(i).getVideoId()))
      {
        System.out.println(videoLibrary.getVideos().get(i).getTitle() + " (" + videoLibrary.getVideos().get(i).getVideoId()+ ") "
                + videoLibrary.getVideos().get(i).getTags().toString().replace(",", "") + " - FLAGGED (reason: " + flaggedVideos.get(videoLibrary.getVideos().get(i).getVideoId()) + ")");
      }
     // String str = videoLibrary.getVideos().get(i).getTags()
      else {
        System.out.println(videoLibrary.getVideos().get(i).getTitle() + " (" + videoLibrary.getVideos().get(i).getVideoId() + ") "
                + videoLibrary.getVideos().get(i).getTags().toString().replace(",", ""));
      }
    }
  }

  public void playVideo(String videoId) {
    if (videoPlaying == null)
    {
      if (videoLibrary.getVideo(videoId) != null)
      {
        if (flaggedVideos.containsKey(videoId))
        {
          System.out.println("Cannot play video: Video is currently flagged (reason: " + flaggedVideos.get(videoId) + ")");
        }
        else
        {
          videoPlaying = videoLibrary.getVideo(videoId);
          System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
          paused = false;
        }

      }
      else
        System.out.println("Cannot play video: Video does not exist");
    }
    else
    {
      if (videoLibrary.getVideo(videoId) != null) {
        if (flaggedVideos.containsKey(videoId))
        {
          System.out.println("Cannot play video: Video is currently flagged (reason: " + flaggedVideos.get(videoId) + ")");
        }
        else
        {
          stopVideo();
          playVideo(videoId);
          paused = false;
          return;
        }
      }
      else
        System.out.println("Cannot play video: Video does not exist");
    }

  }

  public void stopVideo() {
    if (videoPlaying != null)
    {
      System.out.println("Stopping video: " + videoPlaying.getTitle());
      videoPlaying = null;
    }

    else
      System.out.println("Cannot stop video: No video is currently playing");

  }

  public void playRandomVideo() {
    if (flaggedVideos.size() == 4)
    {
      Random rand = new Random();
      Video randomVid = videoLibrary.getVideos().get(rand.nextInt(videoLibrary.getVideos().size()));
      if (videoPlaying == null)
      {
        videoPlaying = randomVid;
        System.out.println("Playing video: " + randomVid.getTitle());
      }
      else {
        stopVideo();
        playVideo(randomVid.getVideoId());
      }
      paused = false;
    }
    else
      System.out.println("No videos available");

  }

  public void pauseVideo() {
    if (videoPlaying != null) {
      if (paused == true)
        System.out.println("Video already paused: " + videoPlaying.getTitle());
      else {
        System.out.println("Pausing video: " + videoPlaying.getTitle());
        paused = true;
      }
    }
    else
    {
      System.out.println("Cannot pause video: No video is currently playing");
    }

  }

  public void continueVideo() {
    if (videoPlaying!=null)
    {
      if (paused)
        System.out.println("Continuing video: "+ videoPlaying.getTitle());
      else
        System.out.println("Cannot continue video: Video is not paused");
    }
    else
      System.out.println("Cannot continue video: No video is currently playing");

  }

  public void showPlaying() {
    if (videoPlaying != null &&  !(flaggedVideos.containsKey(videoPlaying.getVideoId())))
    {
      if (!paused)
        System.out.println("Currently playing: " + videoPlaying.getTitle() + " (" + videoPlaying.getVideoId() + ") " + videoPlaying.getTags().toString().replace(",", ""));
      else
        System.out.println("Currently playing: " + videoPlaying.getTitle() + " (" + videoPlaying.getVideoId() + ") " + videoPlaying.getTags().toString().replace(",", "") + " - PAUSED");

    }
    else
      System.out.println("No video is currently playing");

  }

  public void createPlaylist(String playlistName) {

    for (VideoPlaylist vidplaylist: videoPlaylists) {
      if (vidplaylist.getName().equalsIgnoreCase(playlistName)) {
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
        return;
      }
    }
      videoPlaylists.add(new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: " + playlistName);
  }


  public void addVideoToPlaylist(String playlistName, String videoId) {
    for (VideoPlaylist vidplaylist : videoPlaylists) {
      if (vidplaylist.getName().toLowerCase().equalsIgnoreCase(playlistName)) {
        if (videoLibrary.getVideo(videoId) != null) { //checks that video exists
          //System.out.println("Successfully added");
          if (flaggedVideos.containsKey(videoId))
          {
            System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: " + flaggedVideos.get(videoId) + ")");
          }
          else
          {
            if (vidplaylist.getPlaylistVideos().isEmpty())
            {
              vidplaylist.getPlaylistVideos().add(videoLibrary.getVideo(videoId));
              System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
            }
            else
            {
              for (Video vids : vidplaylist.p_videos)
              {
                if (vids.getTitle().equals(videoLibrary.getVideo(videoId).getTitle())) {
                  System.out.println("Cannot add video to " + playlistName + ": Video already added");
                  return;
                }
              }
              System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
              vidplaylist.getPlaylistVideos().add(videoLibrary.getVideo(videoId));
            }
          }
        }
          else //video does not exist
          {
            System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
          }
          return;
        }
      }
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }

    public void showAllPlaylists() {
    if (videoPlaylists.isEmpty())
    {
      System.out.println("No playlists exist yet");
    }
    else {
      Collections.sort(videoPlaylists);
      System.out.println("Showing all playlists:");
      for (int i = 0; i < videoPlaylists.size(); i++) {
        System.out.println(getVideoPlaylists().get(i).getName());
      }
    }
  }

  public void showPlaylist(String playlistName) {

    for (VideoPlaylist vidplaylist: videoPlaylists)
    {
      if (vidplaylist.getName().toLowerCase().equalsIgnoreCase(playlistName)) {
        System.out.println("Showing playlist: " + playlistName);
        if (vidplaylist.getPlaylistVideos().isEmpty())
        {
          System.out.println("No videos here yet");
          return;
        }
        else {
          for (int i = 0; i < vidplaylist.getPlaylistVideos().size(); i++) {
            if (flaggedVideos.containsKey(vidplaylist.getPlaylistVideos().get(i).getVideoId()))
            {
              System.out.println(vidplaylist.getPlaylistVideos().get(i).getTitle() + " (" + vidplaylist.getPlaylistVideos().get(i).getVideoId() + ") "
                      + vidplaylist.getPlaylistVideos().get(i).getTags().toString().replace(",", "") + " - FLAGGED (reason: " + flaggedVideos.get(vidplaylist.getPlaylistVideos().get(i).getVideoId()) + ")");
            }
            else {
              System.out.println(vidplaylist.getPlaylistVideos().get(i).getTitle() + " (" + vidplaylist.getPlaylistVideos().get(i).getVideoId() + ") "
                      + vidplaylist.getPlaylistVideos().get(i).getTags().toString().replace(",", ""));
            }
          }
          return;
        }
      }
    }
    System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");

    }

  public void removeFromPlaylist(String playlistName, String videoId) {
    for (VideoPlaylist vidplaylist: videoPlaylists)
    {
      if (vidplaylist.getName().toLowerCase().equalsIgnoreCase(playlistName)) {
        if (videoLibrary.getVideo(videoId) != null) {
          for (Video vids : vidplaylist.getPlaylistVideos()) {
            if (vids.getTitle().equals(videoLibrary.getVideo(videoId).getTitle())) {
              vidplaylist.getPlaylistVideos().remove(vids);
              System.out.println("Removed video from " + playlistName + ": " + vids.getTitle());
              return;
            }
          }
              System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
              return;
          }
        else {
          System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
          return;
        }
      }

    }
    System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");

  }

  public void clearPlaylist(String playlistName) {
    for (VideoPlaylist vidplaylist: videoPlaylists) {
      if (vidplaylist.getName().toLowerCase().equalsIgnoreCase(playlistName)) {
        for (int i=0; i<vidplaylist.getPlaylistVideos().size(); i++) {//(Video vid : vidplaylist.getPlaylistVideos()) {
          vidplaylist.getPlaylistVideos().remove(i);
        }
        System.out.println("Successfully removed all videos from " + playlistName);
        return;
      }
    }
    System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");

  }

  public void deletePlaylist(String playlistName) {
    for (VideoPlaylist vidplaylist: videoPlaylists) {
      if (vidplaylist.getName().toLowerCase().equalsIgnoreCase(playlistName)) {
        videoPlaylists.remove(vidplaylist);
        System.out.println("Deleted playlist: " + playlistName);
        return;
      }
    }
    System.out.println("Cannot delete playlist my_playlist: Playlist does not exist");

  }

  public void searchVideos(String searchTerm) {
    int i = 0;
    boolean found = false;

    for (int a = 0; a<videoLibrary.getVideos().size(); a++)
    {
      if (videoLibrary.getVideos().get(a).getTitle().toLowerCase().contains(searchTerm))
      {
        found = true;
        break;
      }
      else
        found = false;
    }

    if (found)
    {
      System.out.println("Here are the results for " + searchTerm + ":");
      for (int a = 0; a<videoLibrary.getVideos().size(); a++) {
        if (videoLibrary.getVideos().get(a).getTitle().toLowerCase().contains(searchTerm) && !(flaggedVideos.containsKey(videoLibrary.getVideos().get(a).getVideoId()))) {
          System.out.println(++i + ") " + videoLibrary.getVideos().get(a).getTitle() + " (" + videoLibrary.getVideos().get(a).getVideoId() + ") "
                  + videoLibrary.getVideos().get(a).getTags().toString().replace(",", ""));
        videoOptions.add(videoLibrary.getVideos().get(a));
        }
      }

      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner sc=new Scanner(System.in);
      int option;
      try {
        option = Integer.parseInt( sc.nextLine() );
        //option = Integer.parseInt( sc.nextLine() );
      }
      catch( Exception e ) {
        return;
      }
      //int option;
      //if (Integer.parseInt(sc.nextLine()) != null)
      //System.out.println(option);
      if (option <= i)
      {
        System.out.println("Playing video: " + videoOptions.get(i-1).getTitle());
      }
    }
    else
      System.out.println("No search results for " + searchTerm);
  }

  public void searchVideosWithTag(String videoTag) {
    int i = 0;
    boolean found = false;

    for (int a = 0; a<videoLibrary.getVideos().size(); a++)
    {
      if (videoLibrary.getVideos().get(a).getTags().contains(videoTag))
      {
        found = true;
        break;
      }
      else
        found = false;
    }

    if (found)
    {
      System.out.println("Here are the results for " + videoTag + ":");
      for (int a = 0; a<videoLibrary.getVideos().size(); a++) {
        if (videoLibrary.getVideos().get(a).getTags().contains(videoTag) && !(flaggedVideos.containsKey(videoLibrary.getVideos().get(a).getVideoId()))) {
          System.out.println(++i + ") " + videoLibrary.getVideos().get(a).getTitle() + " (" + videoLibrary.getVideos().get(a).getVideoId() + ") "
                  + videoLibrary.getVideos().get(a).getTags().toString().replace(",", ""));
          videoOptions.add(videoLibrary.getVideos().get(a));
        }
      }

      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner sc=new Scanner(System.in);
      int option;
      try {
        option = Integer.parseInt( sc.nextLine() );
      }
      catch( Exception e ) {
        return;
      }
      if (option <= i)
      {
        System.out.println("Playing video: " + videoOptions.get(i-2).getTitle());
      }
    }
    else
      System.out.println("No search results for " + videoTag);
  }

  public void flagVideo(String videoId) {
    for (String key : flaggedVideos.keySet())
    {
      if (key == videoId)
      {
        System.out.println("Cannot flag video: Video is already flagged");
        return;
      }
    }
    flaggedVideos.put(videoId, "Not supplied");
    if (flaggedVideos.containsKey(videoPlaying.getVideoId()))
    {
      System.out.println("Stopping video: " + videoLibrary.getVideo(videoId).getTitle());
    }
    System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle() + " (reason: " + flaggedVideos.get(videoId) + ")");
  }

  public void flagVideo(String videoId, String reason) {
    if (videoLibrary.getVideo(videoId) != null){
      for (String key : flaggedVideos.keySet()) {
        if (key.equals(videoId)) {
          System.out.println("Cannot flag video: Video is already flagged");
          return;
        }
      }
      this.flaggedVideos.put(videoId, reason);
      if (videoPlaying != null && flaggedVideos.containsKey(videoPlaying.getVideoId()))
      {
        System.out.println("Stopping video: " + videoLibrary.getVideo(videoId).getTitle());
      }
      System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle() + " (reason: " +  reason + ")");
    }
    else
      System.out.println("Cannot flag video: Video does not exist");
  }


  public void allowVideo(String videoId) {
    if (videoLibrary.getVideo(videoId) != null){
      for (String key : flaggedVideos.keySet()) {
        if (key.equals(videoId)) {
          flaggedVideos.remove(key);
          System.out.println("Successfully removed flag from video: " + videoLibrary.getVideo(videoId).getTitle());
          return;
        }
      }
      System.out.println("Cannot remove flag from video: Video is not flagged");
    }
    else
      System.out.println("Cannot remove flag from video: Video does not exist");
  }
}